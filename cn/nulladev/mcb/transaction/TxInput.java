package cn.nulladev.mcb.transaction;

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
	
	public void generateSign(String priKey) {
		//TODO ”√priKeyº”√‹_tx_hash
		this._sign = "";
	}

}
