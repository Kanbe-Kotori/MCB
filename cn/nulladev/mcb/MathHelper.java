package cn.nulladev.mcb;

import java.security.MessageDigest;

public class MathHelper {
	
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
	
	public static String getSHA256Str(String str){
		return byte2Hex(getSHA256(str));
	}
	
	public static String byte2Hex(byte[] bytes){
		StringBuffer stringBuffer = new StringBuffer();
		String temp = null;
		for (int i=0;i<bytes.length;i++){
			temp = Integer.toHexString(bytes[i] & 0xFF);
			if (temp.length()==1){
				stringBuffer.append("0");
			}
			stringBuffer.append(temp);
		}
		return stringBuffer.toString();
	}
	
	public static byte[] hex2Byte(String hex){
        int m = 0, n = 0;
        int byteLen = hex.length() / 2;
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte)intVal);
        }
        return ret;
    }
	
	public static boolean hexCompare(byte[] b1, byte[] b2) {
		//TODO
		return true;
	}
	
	public static boolean hexCompare(String s1, String s2) {
		return hexCompare(hex2Byte(s1), hex2Byte(s2));
	}

}
