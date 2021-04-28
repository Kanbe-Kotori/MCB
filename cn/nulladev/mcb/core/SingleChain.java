package cn.nulladev.mcb.core;

import java.util.ArrayList;

public class SingleChain extends BlockChain {
	
	protected final ArrayList<Block> _chain;
	
	public SingleChain() {
		super();
		this._chain = new ArrayList<Block>();
	}
	
	@Override
	public String getLastBlockHash() {
		if (_chain.size() == 0)
			return null;
		return _chain.get(_chain.size()-1).getHash();
	}
	
	@Override
	protected void addBlock(Block b) {
		_chain.add(b);
	}

	@Override
	public boolean verifyNewBlock(Block b) {
		if (b.getPrevHash().equals(Block.ZERO_HASH)) {
			if (this._chain.size() > 0)
				return false;
		} else if (!b.getPrevHash().equals(getLastBlockHash())) {
			return false;
		}
		return super.verifyNewBlock(b);
	}
	
}
