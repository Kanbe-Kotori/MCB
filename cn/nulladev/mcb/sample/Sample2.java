package cn.nulladev.mcb.sample;

import java.util.ArrayList;

import cn.nulladev.mcb.core.Block;
import cn.nulladev.mcb.core.MultiChain;
import cn.nulladev.mcb.core.transaction.Transaction;
import cn.nulladev.mcb.utils.CommonHelper;

public class Sample2 {
	
	public static void main(String[] Args) {
		MultiChain testchain = new MultiChain(3);
		
		for(int i = 0; i<20; i++) {
			System.out.println("prepare to mine index: " + i);
			
			//initialize transactions
			ArrayList<Transaction> list = new ArrayList<Transaction>();
			double balance = SampleUsers.sampleUser1.getBalance(testchain);
			System.out.print("user1 current balance:" + balance + ", list: ");
			SampleUsers.sampleUser1.getUTXOList(testchain).forEach(t->System.out.print(t.getValue() + ", "));
			System.out.println();
			System.out.print("user2 current balance:" + SampleUsers.sampleUser2.getBalance(testchain) + ", list: ");
			SampleUsers.sampleUser2.getUTXOList(testchain).forEach(t->System.out.print(t.getValue() + ", "));
			System.out.println();
			
			if (balance >= 400) {
				System.out.println("user1 trys to give user2 399 because he owns more than 400.");
				Transaction t = SampleUsers.sampleUser1.createTransaction(testchain, StaticValues.PUB_KEY_2, 399, 0.9);
				double mineValue = Block.MINER_BONUS + t.getFeeValue(testchain);
				list.add(Transaction.createCoinBase(StaticValues.PUB_KEY_1, mineValue));
				list.add(t);
			} else {
				list.add(Transaction.createCoinBase(StaticValues.PUB_KEY_1, Block.MINER_BONUS));
			}
	
			//Generate the block
			String prevHash = testchain.getLastBlockHash() == null? Block.ZERO_HASH : testchain.getLastBlockHash();
			Block b = Block.create(i, prevHash, CommonHelper.genTarStr(4));
			b.initTransactionList(list);
			long times = 0;
			boolean flag = false;
			while (!flag) {
				times++;
				flag = b.mine();
			}
			System.out.println("Succeed in mining after " + times + " times. Block info:");
			b.printInfo();
			if (testchain.verifyNewBlock(b)) {
				System.out.println("verify success.");
			} else {
				i--;
				System.out.println("verify failed.");
			}
			System.out.println("-------------------------");
		}
		testchain.printChainStruct();
	}

}
