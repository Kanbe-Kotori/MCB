package cn.nulladev.mcb.transaction;

import java.util.ArrayList;

import cn.nulladev.mcb.utils.CommonHelper;
import cn.nulladev.mcb.utils.SHA256;
import cn.nulladev.mcb.utils.TypeTrans;

public class Transaction {
	
	protected String _hash;
	protected ArrayList<TxInput> _input = new ArrayList<TxInput>();
	protected ArrayList<TxOutput> _output = new ArrayList<TxOutput>();
	protected long _timeStamp;
	
	protected Transaction() {}
	
	public static Transaction create(ArrayList<TxInput> input, ArrayList<TxOutput> output) {
		Transaction t = new Transaction();
		t._input = input;
		t._output = output;
		t._timeStamp = System.currentTimeMillis();
		t._hash = t.calcHash();
		return t;
	}
	
	public String getHash() {
		if (_hash != null) return _hash;
		else return calcHash();
	}
	
	protected String calcHash() {
		byte[][] b1a = _input.stream().map(t->t.getRaw()).toArray(byte[][]::new);
		byte[] b1 = CommonHelper.mergeByteArrays(b1a);
		byte[][] b2a = _output.stream().map(t->t.getRaw()).toArray(byte[][]::new);
		byte[] b2 = CommonHelper.mergeByteArrays(b2a);
		byte[] b = CommonHelper.mergeByteArray(b1, b2);
		return TypeTrans.byte2Hex(SHA256.getSHA256(b));
	}
	
	public double getFeeValue() {
		double input = _input.stream().mapToDouble(t->t.getOutput().getValue()).sum();
		double output = _output.stream().mapToDouble(t->t.getValue()).sum();
		return input - output;
	}

}
