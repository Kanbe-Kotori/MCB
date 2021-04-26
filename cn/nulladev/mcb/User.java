package cn.nulladev.mcb;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.nulladev.mcb.core.UTXOPool;
import cn.nulladev.mcb.core.transaction.Transaction;
import cn.nulladev.mcb.core.transaction.TxInput;
import cn.nulladev.mcb.core.transaction.TxOutput;
import cn.nulladev.mcb.utils.RSA;

public class User {
	
	protected String _pub_key;
	protected String _pri_key;
	
	private User() {}
	
	public static User fromKey(String pubKey, String priKey) {
		User user = new User();
		user._pub_key = pubKey;
		user._pri_key = priKey;
		return user;
	}
	
	public static User create() {
		User user = new User();
		try {
			KeyPair kp = RSA.createKeyPair();
			String pubKey = RSA.getPubKey(kp);
			user._pub_key = pubKey;
			String priKey = RSA.getPriKey(kp);
			user._pri_key = priKey;
		} catch(Exception e) {
			
		}
		return user;
	}
	
	public String getAddress() {
		return this._pub_key;
	}
	
	public List<TxOutput> getUTXOList() {
		return UTXOPool.UTXOList.stream().filter(t->t.getOwner().equals(this._pub_key)).collect(Collectors.toList());
	}
	
	public double getBalance() {
		return getUTXOList().stream().mapToDouble(t->t.getValue()).sum();
	}
	
	public String sign(String data) throws Exception {
		return RSA.sign(data, this._pri_key);
	}
	
	public Transaction createTransaction(String address, double value, double fee) {
		try {
			List<TxOutput> list = this.getUTXOList();
			if (list.stream().mapToDouble(t->t.getValue()).sum() <= value + fee)
				return null;
			
			ArrayList<TxInput> inputList = new ArrayList<TxInput>();
			double totalInput = 0;
			for (int i=0; i<list.size(); i++) {
				TxOutput output = list.get(i);
				totalInput += output.getValue();
				inputList.add(output.genTxInput().genSign(this));
				if(totalInput >= value + fee)
					break;
			}
			
			if (totalInput - value - fee <= 0)
				return null;
			
			TxOutput output1 = TxOutput.create(value, address);
			TxOutput output2 = TxOutput.create(totalInput - value - fee, this._pub_key);
			ArrayList<TxOutput> outputList = new ArrayList<TxOutput>(Arrays.asList(output1,output2));
			
			Transaction tx = Transaction.create(inputList, outputList);
			output1.setTransaction(tx);
			output2.setTransaction(tx);
			return tx;		
		} catch(Exception e) {
			
		}		
		return null;
	}

}
