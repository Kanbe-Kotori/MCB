package cn.nulladev.mcb.utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSA {
	
	public static KeyPair createKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
		keyPairGen.initialize(1024, new SecureRandom());  
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}
	
	public static String getPubKey(KeyPair keypair) {
		RSAPublicKey publicKey = (RSAPublicKey) keypair.getPublic();
		String publicKeyString = new String(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
		return publicKeyString;
	}
	
	public static String getPriKey(KeyPair keypair) {
		RSAPrivateKey privateKey = (RSAPrivateKey) keypair.getPrivate();
		String priavteKeyString = new String(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
		return priavteKeyString;
	}
	
	public static String encrypt(String str, String publicKey) throws Exception{
		byte[] decoded = Base64.getDecoder().decode(publicKey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
		return outStr;
	}
	
	public static String decrypt(String str, String privateKey) throws Exception{
		byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"));
		byte[] decoded = Base64.getDecoder().decode(privateKey);  
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));  
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr = new String(cipher.doFinal(inputByte));
		return outStr;
	}

}
