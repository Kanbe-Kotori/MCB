package cn.nulladev.mcb;

import java.util.ArrayList;
import java.util.HashMap;

public class Transaction {
	
	private final String _hash;
	private HashMap<String, Double> _input = new HashMap<String, Double>();
	private HashMap<String, Double> _output = new HashMap<String, Double>();
	private ArrayList<String> _sign = new ArrayList<String>();
	
	private Transaction(String hash) {
		this._hash = hash;
	}
	
	public static Transaction create() {
		Transaction t = new Transaction(calcHash());
		return t;
	}
	
	public static String calcHash() {
		//TODO
		return "";
	}

}
