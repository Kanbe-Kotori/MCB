package cn.nulladev.mcb;

import java.util.ArrayList;
import java.util.stream.Collectors;

import cn.nulladev.mcb.transaction.Transaction;
import cn.nulladev.mcb.utils.CommonHelper;
import cn.nulladev.mcb.utils.SHA256;
import cn.nulladev.mcb.utils.TypeTrans;

public class Block {
	
	public static final int VERSION = 114514;
	
	protected int _index;
	protected String _hash;
	
	//Header info
	protected int _version = VERSION;	//4Bytes
	protected String _prevHash;		//32Bytes
	protected String _merkleRoot;		//32Bytes
	protected long _timeStamp;		//4Bytes in BTC, but 8 Bytes there
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
			this.getCoinbaseTransaction();	//TODO 更改sign以改变merkle tree的root hash
			this._merkleRoot = calcMerkleRoot();
		}
		this._hash = calcHash();
		return CommonHelper.hexCompare(this._hash, this._target);
	}
	
	public String calcMerkleRoot() {
		return TypeTrans.byte2Hex(CommonHelper.calcMerkle(_transaction_list.stream().map(t->TypeTrans.hex2Byte(t.getHash())).collect(Collectors.toList())));
	}
	
	public String calcHash() {
		byte[] b1 = TypeTrans.int2Byte(_version);
		byte[] b2 = TypeTrans.hex2Byte(_prevHash);
		byte[] b3 = TypeTrans.hex2Byte(_merkleRoot);
		byte[] b4 = TypeTrans.long2Byte(_timeStamp);
		byte[] b5 = TypeTrans.hex2Byte(_target);
		byte[] b6 = TypeTrans.int2Byte(_nonce);
		byte[] b = CommonHelper.mergeByteArrays(b1, b2, b3, b4, b5, b6);
		return TypeTrans.byte2Hex(SHA256.getSHA256(b));
	}
	
	public Transaction getCoinbaseTransaction() {
		if (this._transaction_list != null && this._transaction_list.size() > 0)
			return this._transaction_list.get(0);
		return null;
	}
	

}
