package cn.nulladev.mcb.transaction;

import cn.nulladev.mcb.utils.CommonHelper;
import cn.nulladev.mcb.utils.RSA;
import cn.nulladev.mcb.utils.TypeTrans;

public class TxInput {
	
	private String _tx_hash;
	private int _num;
	private String _sign;
	
	private TxInput() {}
	
	public static TxInput create(String hash, int num) {
		TxInput input = new TxInput();
		input._tx_hash = hash;
		input._num = num;
		return input;
	}
	
	public void genSign(String priKey) throws Exception {
		this._sign = RSA.encrypt(_tx_hash, priKey);
	}
	
	public TxOutput getOutput() {
		//TODO 从Blockchain查询，获取对应的output
		return null;
	}
	
	public byte[] getRaw() {
		byte[] b1 = _tx_hash.getBytes();
		byte[] b2 = TypeTrans.int2Byte(_num);
		byte[] b3 = _sign.getBytes();
		return CommonHelper.mergeByteArrays(b1, b2, b3);
	}

}
