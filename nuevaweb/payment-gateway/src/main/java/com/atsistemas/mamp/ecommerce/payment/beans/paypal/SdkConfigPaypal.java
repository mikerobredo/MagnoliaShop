/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.beans.paypal;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 *
 */
public class SdkConfigPaypal {

	private Map<String, String> SDK_CONFIG_CLEAR;
	private Map<String, byte[]> SDK_CONFIG_ENCRYPTED;

	public void setSDK_CONFIG_CLEAR(Map<String, String> sDK_CONFIG_CLEAR) {
		SDK_CONFIG_CLEAR = sDK_CONFIG_CLEAR;
	}

	public void setSDK_CONFIG_ENCRYPTED(Map<String, byte[]> sDK_CONFIG_ENCRYPTED) {
		SDK_CONFIG_ENCRYPTED = sDK_CONFIG_ENCRYPTED;
	}
	
	public Map<String, String> getSdkConfig() {
		
		Map<String, String> ret = new HashMap<String, String>(SDK_CONFIG_CLEAR);
		
		for (Map.Entry<String, byte[]> entry : SDK_CONFIG_ENCRYPTED.entrySet()) {
			ret.put(entry.getKey(), new String(entry.getValue()));
		}
		
		return ret;
	}
	
}
