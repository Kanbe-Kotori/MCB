package cn.nulladev.mcb.sample;

import java.util.ArrayList;

import cn.nulladev.mcb.core.Block;
import cn.nulladev.mcb.core.transaction.Transaction;

public class Sample {
	
	public static void main(String[] Args) {
		ArrayList<Transaction> list = new ArrayList<Transaction>();
		list.add(Transaction.createCoinBase(StaticValues.PUB_KEY_1, 100));
		
		Block b = Block.create(0, Block.ZERO_HASH, "0000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
		b.initTransactionList(list);
		
		boolean flag = false;
		while (!flag) {
			flag = b.mine();
			System.out.println("attempts: " + b.getNonce() + ", " + (flag? "success" : "failed"));
			System.out.println("hash=" + b.getHash());
		}
	}

}
