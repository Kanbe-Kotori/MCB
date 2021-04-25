package cn.nulladev.mcb;

import java.security.KeyPair;

import cn.nulladev.mcb.utils.*;

public class Main {
	
	public static void main(String[] Args) {
		try {
			//System.out.println(SHA256.getSHA256("woshisb"));	
			KeyPair kp = RSA.createKeyPair();
			String pubKey = RSA.getPubKey(kp);
			System.out.println("pubKey is " + pubKey);
			String priKey = RSA.getPriKey(kp);
			System.out.println("priKey is " + priKey);
		} catch(Exception e) {
			
		}
		
	}

}
