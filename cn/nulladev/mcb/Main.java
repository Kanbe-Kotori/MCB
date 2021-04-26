package cn.nulladev.mcb;

import java.security.KeyPair;

import cn.nulladev.mcb.sample.SampleUsers;
import cn.nulladev.mcb.utils.*;

public class Main {
	
	public static void main(String[] Args) {
		try {
			String sb1 = TypeTrans.byte2Hex(SHA256.getSHA256("woshisb1"));
			String sb2 = TypeTrans.byte2Hex(SHA256.getSHA256("woshisb2"));
			System.out.println(sb1);
			System.out.println(sb2);
			String sb11 = SampleUsers.sampleUser1.sign(sb1);
			String sb12 = SampleUsers.sampleUser1.sign(sb2);
			System.out.println(sb11);
			System.out.println(sb12);
			System.out.println(RSA.verify(sb11, SampleUsers.sampleUser1.getAddress()));
			System.out.println(RSA.verify(sb12, SampleUsers.sampleUser1.getAddress()));
			
			/*
			KeyPair kp = RSA.createKeyPair();
			String pubKey = RSA.getPubKey(kp);
			System.out.println("pubKey is " + pubKey);
			String priKey = RSA.getPriKey(kp);
			System.out.println("priKey is " + priKey);
			*/
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
