package cn.nulladev.mcb.transaction;

import cn.nulladev.mcb.utils.CommonHelper;
import cn.nulladev.mcb.utils.TypeTrans;

public class TxOutput {
	
	private double _value;
	private String _owner_pub_key;
	
	private TxOutput() {}
	
	public static TxOutput create(double value, String owner) {
		TxOutput output = new TxOutput();
		output._value = value;
		output._owner_pub_key = owner;
		return output;
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

}
