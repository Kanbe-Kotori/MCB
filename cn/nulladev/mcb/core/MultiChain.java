package cn.nulladev.mcb.core;

import java.util.ArrayList;
import java.util.Random;

public class MultiChain extends BlockChain {
	
	protected final ArrayList<Block>[] _chains;
	protected final Random random = new Random(12345678);
	
	public MultiChain(int num) {
		super();
		this._chains = new ArrayList[num];
		for (int i = 0; i< num; i++) {
			this._chains[i] = new ArrayList<Block>();
		}
	}
	
	public int getChainNum() {
		return this._chains.length;
	}
	
	protected boolean hasEmptyChain() {
		for (int i = 0; i<getChainNum(); i++) {
			if (this._chains[i].size() == 0)
				return true;
		}
		return false;
	}

	@Override
	public String getLastBlockHash() {
		if (this.hasEmptyChain())
			return null;
		int n = random.nextInt(_chains.length);
		return _chains[n].get(_chains[n].size()-1).getHash();
	}

	@Override
	public boolean verifyNewBlock(Block b) {
		if (b.getPrevHash().equals(Block.ZERO_HASH)) {
			if (!this.hasEmptyChain())
				return false;
		} else {
			boolean flag = true;
			for (ArrayList<Block> chain : _chains) {
				if(chain.size() != 0 && chain.get(chain.size()-1).getHash().equals(b.getPrevHash())) {
					flag = false;
					break;
				}
			}
			if (flag)
				return false;
		}
		return super.verifyNewBlock(b);
	}

	@Override
	protected void addBlock(Block b) {
		for (ArrayList<Block> chain : _chains) {
			if(chain.size() != 0 && chain.get(chain.size()-1).getHash().equals(b.getPrevHash())) {
				chain.add(b);
			}
		}
	}

}
