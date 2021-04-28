package cn.nulladev.mcb.core.transaction;

import java.util.ArrayList;

import cn.nulladev.mcb.core.BlockChain;
import cn.nulladev.mcb.utils.CommonHelper;
import cn.nulladev.mcb.utils.SHA256;
import cn.nulladev.mcb.utils.TypeTrans;

public class Transaction {
	
	public static String COINBASE_HASH = "0000000000000000000000000000000000000000000000000000000000000000";
	public static int COINBASE_INDEX = Integer.MAX_VALUE;
	
	protected String _hash;
	protected ArrayList<TxInput> _input_list;
	protected ArrayList<TxOutput> _output_list;
	protected long _timeStamp;
	
	protected Transaction() {}
	
	public static Transaction create(ArrayList<TxInput> input, ArrayList<TxOutput> output) {
		Transaction t = new Transaction();
		t._input_list = input;
		t._output_list = output;
		t._timeStamp = System.currentTimeMillis();
		t._hash = t.calcHash();
		return t;
	}
	
	public static Transaction createCoinBase(String miner, double value) {
		Transaction t = new Transaction();
		t._input_list = new ArrayList<TxInput>();
		t._input_list.add(TxInput.create(COINBASE_HASH, COINBASE_INDEX).genRandomSign());
		t._output_list = new ArrayList<TxOutput>();
		t._output_list.add(TxOutput.create(value, miner).setTransaction(t));
		t._timeStamp = System.currentTimeMillis();
		t._hash = t.calcHash();
		return t;
	}
	
	public boolean isCoinBase() {
		if (this._input_list.size() != 1)
			return false;
		TxInput coinbase = this._input_list.get(0);
		if (!coinbase.getHash().equals(COINBASE_HASH))
			return false;
		if (coinbase.getIndex() != COINBASE_INDEX)
			return false;
		return true;
	}
	
	public void changeCoinbaseHash() {
		if (this.isCoinBase())
			this._input_list.get(0).genRandomSign();
		this._hash = this.calcHash();
	}
	
	public String getHash() {
		if (this._hash == null)
			this._hash = this.calcHash();
		return this._hash;
	}
	
	public ArrayList<TxInput> getInputs() {
		return _input_list;
	}
	
	public ArrayList<TxOutput> getOutputs() {
		return _output_list;
	}
	
	public TxOutput getOutputFromIndex(int index) {
		return _output_list.get(index);
	}
	
	public int getOutputIndex(TxOutput output) {
		return this._output_list.indexOf(output);
	}
	
	public double getTotalOutput() {
		return _output_list.stream().mapToDouble(t->t.getValue()).sum();
	}
	
	public String calcHash() {
		byte[][] b1a = _input_list.stream().map(t->t.getRaw()).toArray(byte[][]::new);
		byte[] b1 = CommonHelper.mergeByteArrays(b1a);
		byte[][] b2a = _output_list.stream().map(t->t.getRaw()).toArray(byte[][]::new);
		byte[] b2 = CommonHelper.mergeByteArrays(b2a);
		byte[] b3 = TypeTrans.long2Byte(_timeStamp);
		byte[] b = CommonHelper.mergeByteArrays(b1, b2, b3);
		return TypeTrans.byte2Hex(SHA256.getSHA256(b));
	}
	
	//Must be called before validation, or UTXOs won't be found.
	public double getFeeValue(BlockChain chain) {
		double input = _input_list.stream().mapToDouble(t->t.getUTXO(chain).getValue()).sum();
		double output = _output_list.stream().mapToDouble(t->t.getValue()).sum();
		return input - output;
	}
	
	public byte[] getRaw() {
		byte[] hash = TypeTrans.hex2Byte(getHash());
		byte[] b1 = TypeTrans.int2Byte(_input_list.size());
		byte[][] b2a = _input_list.stream().map(t->t.getRaw()).toArray(byte[][]::new);
		byte[] b2 = CommonHelper.mergeByteArrays(b2a);
		byte[] b3 = TypeTrans.int2Byte(_output_list.size());
		byte[][] b4a = _output_list.stream().map(t->t.getRaw()).toArray(byte[][]::new);
		byte[] b4 = CommonHelper.mergeByteArrays(b4a);
		return CommonHelper.mergeByteArrays(hash, b1, b2, b3, b4);
	}

}
