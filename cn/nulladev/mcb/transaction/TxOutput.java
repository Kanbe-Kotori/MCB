package cn.nulladev.mcb.transaction;

public class TxOutput {
	
	private double _value;
	private String _owner_pub_key;
	private String _script;
	
	private TxOutput() {}
	
	public static TxOutput create(double value, String owner) {
		TxOutput output = new TxOutput();
		output._value = value;
		output._owner_pub_key = owner;
		return output;
	}

}
