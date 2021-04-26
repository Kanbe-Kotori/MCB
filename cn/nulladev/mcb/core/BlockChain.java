package cn.nulladev.mcb.core;

public abstract class BlockChain {
	
	public final UTXOPool pool;
	
	public BlockChain() {
		this.pool = new UTXOPool();
	}

}
