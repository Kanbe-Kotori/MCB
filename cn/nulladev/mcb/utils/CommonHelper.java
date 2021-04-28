package cn.nulladev.mcb.utils;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CommonHelper {
	
	public static Random random = new Random();
	
	public static byte[] mergeByteArray(byte[] b1, byte[] b2) {
		byte[] b = new byte[b1.length + b2.length];
		System.arraycopy(b1, 0, b, 0, b1.length);
		System.arraycopy(b2, 0, b, b1.length, b2.length);
		return b;
	}
	
	public static byte[] mergeByteArrays(byte[]...bs) {
		byte[] b = bs[0];
		for (int i=1; i<bs.length; i++) {
			b = mergeByteArray(b, bs[i]);
		}
		return b;
	}
	
	public static byte[] calcMerkle(List<byte[]> s) {
		if (s.size() == 1) return SHA256.getSHA256(s.get(0));
		if (s.size() % 2 == 1) {
			s.add("".getBytes());
		}
		byte[] b1 = calcMerkle(s.subList(0, s.size()/2));
		byte[] b2 = calcMerkle(s.subList(s.size()/2, s.size()));
		return SHA256.getSHA256(mergeByteArray(b1,b2));
	}
	
	//return b1<=b2
	public static boolean hexCompare(byte[] b1, byte[] b2) {
		for (int i=0; i<b1.length; i++) {
			int v1 = b1[i]>=0? b1[i] : b1[i]+256;
			int v2 = b2[i]>=0? b2[i] : b2[i]+256;
			if (v1 < v2)
				return true;
			else if (v1 > v2)
				return false;
			else
				continue;
		}
		return true;
	}
	
	public static boolean hexCompare(String s1, String s2) {
		return hexCompare(TypeTrans.hex2Byte(s1), TypeTrans.hex2Byte(s2));
	}
	
	public static String genTarStr(int zero_num) {
		return String.join("", Collections.nCopies(zero_num, "0")) + String.join("", Collections.nCopies(64 - zero_num, "f"));
	}
	
	public static String getRandomString(int length){
		String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<length;i++){
			int number=random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

}
