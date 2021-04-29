package cn.nulladev.mcb.core;

import java.util.ArrayList;

import cn.nulladev.mcb.utils.CommonHelper;
import cn.nulladev.mcb.utils.SHA256;
import cn.nulladev.mcb.utils.TypeTrans;

public class MultiChain extends BlockChain {
	
	protected final ArrayList<Block>[] _chains;
	
	public MultiChain(int num) {
		super();
		this._chains = new ArrayList[num];
		for (int i = 0; i<num; i++) {
			this._chains[i] = new ArrayList<Block>();
		}
	}
	
	public int getChainNum() {
		return this._chains.length;
	}
	
	protected int getLastIndex() {
		int index = 0;
		for (ArrayList<Block> chain : _chains) {
			if(chain.size() != 0) {
				index = Math.max(index, chain.get(chain.size()-1).end_num);
			}
		}
		return index;
	}
	
	protected boolean hasEmptyChain() {
		for (ArrayList<Block> chain : _chains) {
			if (chain.size() == 0)
				return true;
		}
		return false;
	}

	@Override
	public String getLastBlockHash() {
		if (this.hasEmptyChain())
			return null;
		ArrayList<byte[]> s = new ArrayList<byte[]>();
		for (ArrayList<Block> chain : _chains) {
			s.add(TypeTrans.hex2Byte(chain.get(chain.size()-1).getHash()));
		}
		return TypeTrans.byte2Hex(CommonHelper.calcMerkle(s));
	}

	@Override
	public boolean verifyNewBlock(Block b) {
		if (b.getPrevHash().equals(Block.ZERO_HASH)) {
			if (!this.hasEmptyChain())
				return false;
		} else {
			if (this.hasEmptyChain())
				return false;
			if (!getLastBlockHash().equals(b.getPrevHash()))
				return false;
		}
		return super.verifyNewBlock(b);
	}

	@Override
	protected void addBlock(Block b) {
		if (b.getPrevHash().equals(Block.ZERO_HASH)) {
			for (ArrayList<Block> chain : _chains) {
				if (chain.size() == 0) {
					b.start_num = 0;
					b.end_num = 1;
					chain.add(b);
					return;
				}
			}
		} else {
			byte[] hash = TypeTrans.hex2Byte(b.getHash());
			int n = (256*(hash[hash.length-2]&0xFF) + (hash[hash.length-1]&0xFF))%getChainNum();
			b.start_num = _chains[n].get(_chains[n].size()-1).end_num;
			b.end_num = getLastIndex() + 1;
			_chains[n].add(b);
		}
	}
	
	public void printChainStruct() {
		int i = 0;
		for (ArrayList<Block> chain : _chains) {
			System.out.printf("Chain %d:\n", i++);
			for (Block b: chain) {
				System.out.printf("(%d, %d)->", b.start_num, b.end_num);
			}
			System.out.println();
		}
	}

}
