package cn.nulladev.mcb.core;

import java.util.ArrayList;
import java.util.List;

import cn.nulladev.mcb.core.transaction.Transaction;
import cn.nulladev.mcb.core.transaction.TxInput;
import cn.nulladev.mcb.utils.CommonHelper;
import cn.nulladev.mcb.utils.RSA;

public class SingleChain extends BlockChain {
	
	protected final ArrayList<Block> chain;
	
	public SingleChain() {
		super();
		this.chain = new ArrayList<Block>();
	}
	
	public Block getLastBlock() {
		if (chain.size() == 0)
			return null;
		return chain.get(chain.size()-1);
	}
	
	private void addBlock(Block b) {
		chain.add(b);
	}
	
	private void updateUTXO(Block b) {
		Transaction coinbase = b.getFirstTransaction();
		this.pool.add(coinbase.getOutputs());
		
		int size = b.getTransactionList().size();
		if (size >= 2) {
			List<Transaction> normal = b.getTransactionList().subList(1, size);
			for (Transaction t : normal) {
				for (TxInput i : t.getInputs()) {
					this.pool.remove(i.getUTXO(this));
				}
				this.pool.add(t.getOutputs());
			}
			
		}
	}

	public boolean verifyNewBlock(Block b) {
		String hash = b.calcHash();
		if (!hash.equals(b.getHash()))
			return false;
		
		if (!CommonHelper.hexCompare(hash, b.getTarget()))
			return false;
		
		Transaction coinbase = b.getFirstTransaction();
		if (!coinbase.isCoinBase())
			return false;
		
		int size = b.getTransactionList().size();
		if (size >= 2) {
			List<Transaction> normal = b.getTransactionList().subList(1, size);
			double total_fee = 0;
			for (Transaction t : normal) {
				for (TxInput i : t.getInputs()) {
					if (i.getUTXO(this) == null)
						return false;
					String sender = i.getUTXO(this).getOwner();
					try {
						if (!RSA.verify(i.getSign(), sender).equals(i.getHash()))
							return false;
					} catch(Exception e) {
						e.printStackTrace();
						return false;
					}
				}
				double fee = t.getFeeValue(this);
				if (fee <= 0)
					return false;
				total_fee += fee;
			}
			if (total_fee + 100 < coinbase.getTotalOutput())
				return false;
		}			
		this.addBlock(b);
		this.updateUTXO(b);
		return true;
	}
	
}
