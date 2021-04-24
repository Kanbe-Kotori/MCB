package cn.nulladev.mcb;

import java.util.ArrayList;

import cn.nulladev.mcb.transaction.Transaction;
import cn.nulladev.mcb.utils.TypeTrans;

public class Block {
	
	public static final int VERSION = 114514;
	
	protected int _index;
	protected String _hash;
	
	//Header info
	protected int _version = VERSION;	//4Bytes
	protected String _prevHash;		//32Bytes
	protected String _merkleRoot;		//32Bytes
	protected long _timeStamp;		//4Bytes
	protected String _target;			//4Bytes
	protected int _nonce;				//4Bytes
	
	//Multi-chain field
	public int start_num;
	public int end_num;
	
	protected ArrayList<Transaction> _transaction_list;
	
	protected Block() {}
	
	public Block create(int index, String prevHash, String target) {
		Block b = new Block();
		b._index = index;
		b._prevHash = prevHash;
		b._timeStamp = System.currentTimeMillis();
		b._target = target;
		b._nonce = 0;
		return b;
	}
	
	public void initTransactionList(ArrayList<Transaction> list) {
		this._transaction_list = list;
		this._merkleRoot = calcMerkleRoot();
	}
	
	public boolean mine() {
		if (this._nonce < Integer.MAX_VALUE) {
			this._nonce++;
		} else {
			this._nonce = 0;
			this.getCoinbaseTransaction();	//更改sign以改变merkle tree的root hash
			this._merkleRoot = calcMerkleRoot();
		}
		this._hash = calcHash();
		return TypeTrans.hexCompare(this._hash, this._target);
	}
	
	public String calcMerkleRoot() {
		//TODO 根据list获取root
		return "";
	}
	
	public String calcHash() {
		//TODO 根据_version到_nonce计算hash
		return "";
	}
	
	public Transaction getCoinbaseTransaction() {
		if (this._transaction_list != null && this._transaction_list.size() > 0)
			return this._transaction_list.get(0);
		return null;
	}
	

}
