package cn.nulladev.mcb.utils;

import java.security.MessageDigest;

public class SHA256 {
	
	public static byte[] getSHA256(String str){
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes("UTF-8"));
			return messageDigest.digest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
