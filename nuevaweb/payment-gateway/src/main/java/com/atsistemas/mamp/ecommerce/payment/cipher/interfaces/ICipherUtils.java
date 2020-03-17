/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.cipher.interfaces;

/**
 * 
 *
 */
public interface ICipherUtils {

	/**
	 * @param strToDecrypt
	 * @param key
	 * @return string decrypted into byte[] basing on AES/ECB/PKCS5PADDING
	 */
	byte[] decrypt(String strToDecrypt, String key);
	
	/**
	 * @param strToEncrypt
	 * @param key
	 * @return string encrypted basing on AES/ECB/PKCS5PADDING
	 */
	String encrypt(String strToEncrypt, String key);
	
}
