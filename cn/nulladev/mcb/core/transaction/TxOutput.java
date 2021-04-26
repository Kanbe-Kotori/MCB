package cn.nulladev.mcb.core.transaction;

import cn.nulladev.mcb.utils.CommonHelper;
import cn.nulladev.mcb.utils.TypeTrans;

public class TxOutput {
	
	private Transaction _tx;
	
	private double _value;
	private String _owner_pub_key;
	
	private TxOutput() {}
	
	//_tx not initialized
	public static TxOutput create(double value, String owner) {
		TxOutput output = new TxOutput();
		output._value = value;
		output._owner_pub_key = owner;
		return output;
	}
	
	public TxOutput setTransaction(Transaction tx) {
		this._tx = tx;
		return this;
	}
	
	public Transaction getTransaction() {
		return _tx;
	}
	
	public double getValue() {
		return _value;
	}
	
	public String getOwner() {
		return _owner_pub_key;
	}
	
	public byte[] getRaw() {
		byte[] b1 = TypeTrans.double2Byte(_value);
		byte[] b2 = _owner_pub_key.getBytes();
		return CommonHelper.mergeByteArray(b1, b2);
	}
	
	//unsigned
	public TxInput genTxInput() {
		return TxInput.create(this._tx._hash, this._tx.getOutputIndex(this));
	}
	
	public boolean matchInput(TxInput input) {
		return this._tx._hash.equals(input.getHash()) && this._tx.getOutput(input.getIndex()) == this;
	}

}
