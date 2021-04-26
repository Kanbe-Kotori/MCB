package cn.nulladev.mcb.core.transaction;

import java.util.ArrayList;

import cn.nulladev.mcb.utils.CommonHelper;
import cn.nulladev.mcb.utils.SHA256;
import cn.nulladev.mcb.utils.TypeTrans;

public class Transaction {
	
	public static String COINBASE_HASH = "0000000000000000000000000000000000000000000000000000000000000000";
	public static int COINBASE_INDEX = Integer.MAX_VALUE;
	
	protected String _hash;
	protected ArrayList<TxInput> _input;
	protected ArrayList<TxOutput> _output;
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
	
	public static Transaction createCoinBase(String miner, double value) {
		Transaction t = new Transaction();
		t._input = new ArrayList<TxInput>();
		t._input.add(TxInput.create(COINBASE_HASH, COINBASE_INDEX).genRandomSign());
		t._output = new ArrayList<TxOutput>();
		t._output.add(TxOutput.create(value, miner));
		t._timeStamp = System.currentTimeMillis();
		t._hash = t.calcHash();
		return t;
	}
	
	public boolean isCoinBase() {
		if (this._input.size() != 1)
			return false;
		TxInput coinbase = this._input.get(0);
		if (!coinbase.getHash().equals(COINBASE_HASH))
			return false;
		if (coinbase.getIndex() != COINBASE_INDEX)
			return false;
		return true;
	}
	
	public void changeCoinbaseHash() {
		if (this.isCoinBase())
			this._input.get(0).genRandomSign();
		this._hash = this.calcHash();
	}
	
	public String getHash() {
		if (this._hash == null)
			this._hash = this.calcHash();
		return this._hash;
	}
	
	public TxInput getInput(int index) {
		return _input.get(index);
	}
	
	public TxOutput getOutput(int index) {
		return _output.get(index);
	}
	
	public int getOutputIndex(TxOutput output) {
		return this._output.indexOf(output);
	}
	
	protected String calcHash() {
		byte[][] b1a = _input.stream().map(t->t.getRaw()).toArray(byte[][]::new);
		byte[] b1 = CommonHelper.mergeByteArrays(b1a);
		byte[][] b2a = _output.stream().map(t->t.getRaw()).toArray(byte[][]::new);
		byte[] b2 = CommonHelper.mergeByteArrays(b2a);
		byte[] b = CommonHelper.mergeByteArray(b1, b2);
		return TypeTrans.byte2Hex(SHA256.getSHA256(b));
	}
	
	//Must be called before validation, or UTXOs won't be found.
	public double getFeeValue() {
		double input = _input.stream().mapToDouble(t->t.getUTXO().getValue()).sum();
		double output = _output.stream().mapToDouble(t->t.getValue()).sum();
		return input - output;
	}
	
	public byte[] getRaw() {
		byte[] hash = TypeTrans.hex2Byte(getHash());
		byte[] b1 = TypeTrans.int2Byte(_input.size());
		byte[][] b2a = _input.stream().map(t->t.getRaw()).toArray(byte[][]::new);
		byte[] b2 = CommonHelper.mergeByteArrays(b2a);
		byte[] b3 = TypeTrans.int2Byte(_output.size());
		byte[][] b4a = _output.stream().map(t->t.getRaw()).toArray(byte[][]::new);
		byte[] b4 = CommonHelper.mergeByteArrays(b4a);
		return CommonHelper.mergeByteArrays(hash, b1, b2, b3, b4);
	}

}
