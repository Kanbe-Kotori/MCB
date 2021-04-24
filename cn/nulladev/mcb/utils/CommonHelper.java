package cn.nulladev.mcb.utils;

public class CommonHelper {
	
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

}
