package com.atsistemas.mamp.ecommerce.payment.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.atsistemas.mamp.ecommerce.payment.common.IConstantsCommon;

public class GenerateEncript {

	public static void main(String[] args) {
		
		try {
			
			GenerateEncript generateEncript = new GenerateEncript();
			
			String key = "zkHU2Yh5RWbh3P4S0W9jTEhKDmklhU4r2Gj44cQwGL7pR7C1KXC9P5q5leHBxWW";
			String strToEncrypt = "N6MPFPY9ANVAD72G";
			
			String strToDecrypt = generateEncript.encrypt(strToEncrypt, key);
			System.out.println("Encrypted phrase: " + strToDecrypt);
			System.out.println("Decrypted phrase: " + new String(generateEncript.decrypt(strToDecrypt, key)));
			
			generateOrderId();

		} catch (Exception e) {

			//log.error("Error while encrypting: " + e.toString());
		}

	}
	
	private byte[] decrypt(String strToDecrypt, String key) {
		
		byte[] ret = null;
		
		try {
            
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
           
            cipher.init(Cipher.DECRYPT_MODE, this.generateSecretKey(key));
            ret = cipher.doFinal(Base64.decodeBase64(strToDecrypt));
            
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
        }
		
        return ret;
	}

	private String encrypt(String strToEncrypt, String key) {
		
		String ret = null;
        
		try {
			
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			cipher.init(Cipher.ENCRYPT_MODE, this.generateSecretKey(key));

			ret = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes(IConstantsCommon.UTF8)));

		} catch (Exception e) {

//			log.error("Error while encrypting: " + e.toString());
		}
        return ret;
	}
	
	private static SecretKeySpec generateSecretKey(String publicKey) {
		
		SecretKeySpec ret = null;
		
		MessageDigest sha = null;
		try {

			byte[] key = publicKey.getBytes(IConstantsCommon.UTF8);
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
	    	key = Arrays.copyOf(key, 16); // use only first 128 bit
	    	ret = new SecretKeySpec(key, "AES");
		    
		} catch (NoSuchAlgorithmException e) {
			//log.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			//log.error(e.getMessage(), e);
		}
		
		return ret;
	}
	
	private static String generateOrderId(){
		
		UUID uuid = UUID.randomUUID();
		String id = Long.toString(uuid.getMostSignificantBits(), 36).replace("-", "") + "-" + Long.toString(uuid.getLeastSignificantBits(), 36).replace("-", "");
		return id;
	}

}
