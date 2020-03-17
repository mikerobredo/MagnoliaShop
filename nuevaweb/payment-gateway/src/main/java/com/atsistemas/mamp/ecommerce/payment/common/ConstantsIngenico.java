/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.common;

import java.util.Map;

import com.atsistemas.mamp.ecommerce.payment.beans.ingenico.SdkConfigIngenico;

/**
 * 
 *
 */
public class ConstantsIngenico {

	private static Map<String, SdkConfigIngenico> mapSdkConfigSites;
	
	private static String ENDPOINT_ALIAS_GATEWAY;
	
	private static String ENDPOINT_DIRECT_LINK;
	
	private static String SERVICE_ENDPOINT_PAYPAL;

	public static Map<String, SdkConfigIngenico> getMapSdkConfigSites() {
		return mapSdkConfigSites;
	}

	public static void setMapSdkConfigSites(Map<String, SdkConfigIngenico> mapSdkConfigSites) {
		ConstantsIngenico.mapSdkConfigSites = mapSdkConfigSites;
	}

	public static String getENDPOINT_ALIAS_GATEWAY() {
		return ENDPOINT_ALIAS_GATEWAY;
	}

	public static void setENDPOINT_ALIAS_GATEWAY(String eNDPOINT_ALIAS_GATEWAY) {
		ENDPOINT_ALIAS_GATEWAY = eNDPOINT_ALIAS_GATEWAY;
	}

	public static String getENDPOINT_DIRECT_LINK() {
		return ENDPOINT_DIRECT_LINK;
	}

	public static void setENDPOINT_DIRECT_LINK(String eNDPOINT_DIRECT_LINK) {
		ENDPOINT_DIRECT_LINK = eNDPOINT_DIRECT_LINK;
	}

	public static String getSERVICE_ENDPOINT_PAYPAL() {
		return SERVICE_ENDPOINT_PAYPAL;
	}

	public static void setSERVICE_ENDPOINT_PAYPAL(String sERVICE_ENDPOINT_PAYPAL) {
		SERVICE_ENDPOINT_PAYPAL = sERVICE_ENDPOINT_PAYPAL;
	}
	
}
