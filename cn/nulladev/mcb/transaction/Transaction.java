package cn.nulladev.mcb.transaction;

import java.util.ArrayList;

public class Transaction {
	
	protected String _hash;
	protected ArrayList<TxInput> _input = new ArrayList<TxInput>();
	protected ArrayList<TxOutput> _output = new ArrayList<TxOutput>();
	protected long _timeStamp;
	
	protected Transaction() {}
	
	public static Transaction create(ArrayList<TxInput> input, ArrayList<TxOutput> output, String sign) {
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
		//TODO
		return "";
	}
	
	public double getFeeValue() {
		double input = 0; 	//TODO 总和
		double output = 0;	//TODO 总和
		return input - output;
	}

}
