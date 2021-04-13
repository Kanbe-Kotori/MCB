package cn.nulladev.mcb;

import java.util.ArrayList;

public class Block {
	
	public static final int VERSION = 114514;
	
	private int _index;
	private String _hash;
	
	//Header info
	private int _version = VERSION;	//4Bytes
	private String _prevHash;		//32Bytes
	private String _merkleRoot;		//32Bytes
	private long _timeStamp;		//4Bytes
	private String _target;			//4Bytes
	private int _nonce;				//4Bytes
	
	private ArrayList<Transaction> _transaction_list;
	
	private Block(int index, String prevHash, long time, String target) {
		this._index = index;
		this._prevHash = prevHash;
		this._timeStamp = time;
		this._target = target;
		this._nonce = 0;
	}
	
	public Block create(int index, String prevHash, String target) {
		return new Block(index, prevHash, System.currentTimeMillis(), target);
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
		return MathHelper.hexCompare(this._hash, this._target);
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
