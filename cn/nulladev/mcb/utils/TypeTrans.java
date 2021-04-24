package cn.nulladev.mcb.utils;

public class TypeTrans {
		
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
	
	public static byte[] double2Byte(double d) {  
		long value = Double.doubleToRawLongBits(d);  
		byte[] byteRet = new byte[8];  
		for (int i = 0; i < 8; i++) {  
			byteRet[i] = (byte) ((value >> 8 * i) & 0xff);  
		}  
		return byteRet;  
	}
	
	public static double byte2Double(byte[] arr) {  
		long value = 0;  
		for (int i = 0; i < 8; i++) {  
			value |= ((long) (arr[i] & 0xff)) << (8 * i);  
		}  
		return Double.longBitsToDouble(value);  
	}
	
	public static byte[] int2Byte(int i) {
        byte[] result = new byte[4];
        result[0] = (byte)((i >> 24) & 0xFF);
        result[1] = (byte)((i >> 16) & 0xFF);
        result[2] = (byte)((i >> 8) & 0xFF);
        result[3] = (byte)(i & 0xFF);
        return result;
    }
 
    public static int byte2Int(byte[] bytes) {
        int value=0;
        for(int i = 0; i < 4; i++) {
            int shift= (3-i) * 8;
            value +=(bytes[i] & 0xFF) << shift;
        }
        return value;
    }
	
	public static boolean hexCompare(byte[] b1, byte[] b2) {
		//TODO
		return true;
	}
	
	public static boolean hexCompare(String s1, String s2) {
		return hexCompare(hex2Byte(s1), hex2Byte(s2));
	}

}
