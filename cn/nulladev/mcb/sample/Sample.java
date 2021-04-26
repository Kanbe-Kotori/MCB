package cn.nulladev.mcb.sample;

import java.util.ArrayList;

import cn.nulladev.mcb.core.Block;
import cn.nulladev.mcb.core.SingleChain;
import cn.nulladev.mcb.core.transaction.Transaction;

public class Sample {
	
	public static void main(String[] Args) {
		SingleChain testchain = new SingleChain();
		
		for(int i = 0; i<10; i++) {
		System.out.println("prepare to mine index: " + i);
		String prevHash = testchain.getLastBlock() == null? Block.ZERO_HASH : testchain.getLastBlock().getHash();		
		ArrayList<Transaction> list = new ArrayList<Transaction>();
		list.add(Transaction.createCoinBase(StaticValues.PUB_KEY_1, 100));
		Block b = Block.create(i, prevHash, "0000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
		b.initTransactionList(list);		
		double times = 0;
		boolean flag = false;
		while (!flag) {
			times++;
			flag = b.mine();
		}
		System.out.println("Succeed in mining after " + times + " times. Block info:");
		b.printInfo();
		if (testchain.verifyNewBlock(b)) {
			System.out.println("verify success.");
		}
		System.out.println("-------------------------");
		}
	}

}
