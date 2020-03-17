/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.cipher.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atsistemas.mamp.ecommerce.payment.cipher.interfaces.ICipherUtils;
import com.atsistemas.mamp.ecommerce.payment.common.IConstantsCommon;

/**
 * 
 *
 */
public class CipherUtils implements ICipherUtils {

	Logger log = LoggerFactory.getLogger(this.getClass().toString());
	
	/* (non-Javadoc)
	 * @see com.atsistemas.ecommerce.grupojulia.common.cipher.interfaces.ICipherUtils#decrypt(java.lang.String, java.lang.String)
	 */
	@Override
	public byte[] decrypt(String strToDecrypt, String key) {
		
		byte[] ret = null;
		
		try {
            
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
           
            cipher.init(Cipher.DECRYPT_MODE, this.generateSecretKey(key));
            ret = cipher.doFinal(Base64.decodeBase64(strToDecrypt));
            
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
		
        return ret;
	}

	/* (non-Javadoc)
	 * @see com.atsistemas.ecommerce.grupojulia.common.cipher.interfaces.ICipherUtils#encrypt(java.lang.String, java.lang.String)
	 */
	@Override
	public String encrypt(String strToEncrypt, String key) {
		
		String ret = null;
        
		try {
			
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			cipher.init(Cipher.ENCRYPT_MODE, this.generateSecretKey(key));

			ret = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes(IConstantsCommon.UTF8)));

		} catch (Exception e) {

			log.error("Error while encrypting: " + e.toString());
		}
        return ret;
	}

	/**
	 * @return the secret key using AES/SHA-1
	 */
	private SecretKeySpec generateSecretKey(String publicKey) {
		
		SecretKeySpec ret = null;
		
		MessageDigest sha = null;
		try {

			byte[] key = publicKey.getBytes(IConstantsCommon.UTF8);
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
	    	key = Arrays.copyOf(key, 16); // use only first 128 bit
	    	ret = new SecretKeySpec(key, "AES");
		    
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		
		return ret;
	}

}
