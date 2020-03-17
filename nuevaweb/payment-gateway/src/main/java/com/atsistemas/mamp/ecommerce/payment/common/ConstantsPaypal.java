/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.common;

import java.util.HashMap;
import java.util.Map;

import com.atsistemas.mamp.ecommerce.payment.beans.paypal.SdkConfigPaypal;

/**
 * 
 *
 */
public class ConstantsPaypal {

	private static Map<String, String> SDK_CONFIG_CLEAR;
	
	private static Map<String, SdkConfigPaypal> mapSdkConfigSites;
	
	private static String ENDPOINT;

	public static void setSDK_CONFIG_CLEAR(Map<String, String> sDK_CONFIG_CLEAR) {
		SDK_CONFIG_CLEAR = sDK_CONFIG_CLEAR;
	}
	
	public static Map<String, SdkConfigPaypal> getMapSdkConfigSites() {
		return mapSdkConfigSites;
	}

	public static void setMapSdkConfigSites(Map<String, SdkConfigPaypal> mapSdkConfigSites) {
		ConstantsPaypal.mapSdkConfigSites = mapSdkConfigSites;
	}

	public static Map<String, String> getSdkConfig(String site) {
		
		Map<String, String> ret = new HashMap<String, String>(SDK_CONFIG_CLEAR);
		
		ret.putAll(mapSdkConfigSites.get(site).getSdkConfig());
		
		return ret;
	}

	public static String getENDPOINT() {
		return ENDPOINT;
	}

	public static void setENDPOINT(String eNDPOINT) {
		ENDPOINT = eNDPOINT;
	}
	
}
