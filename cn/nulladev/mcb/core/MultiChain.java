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
		if (b.getPrevHash().equals(Block.ZERO_HASH)) {
			for (int i = 0; i<getChainNum(); i++) {
				if (this._chains[i].size() == 0) {
					b.start_num = 0;
					b.end_num = 1;
					this._chains[i].add(b);
					return;
				}
			}
		} else {
			for (ArrayList<Block> chain : _chains) {
				if(chain.size() != 0 && chain.get(chain.size()-1).getHash().equals(b.getPrevHash())) {
					b.start_num = chain.get(chain.size()-1).end_num;
					b.end_num = getLastIndex() + 1;
					chain.add(b);
				}
			}
		}
	}
	
	public void printChainStruct() {
		int i = 0;
		for (ArrayList<Block> chain : _chains) {
			System.out.printf("Chain %d:\n", i);
			for (Block b: chain) {
				System.out.printf("(%d, %d)->", b.start_num, b.end_num);
			}
			System.out.println();
		}
	}

}
