/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.beans.paypal;

import java.io.Serializable;

import com.atsistemas.mamp.ecommerce.payment.beans.ingenico.SdkConfigIngenico;
import com.atsistemas.mamp.ecommerce.payment.common.ConstantsIngenico;

/**
 * 
 *
 */
public class IdentificationPaypalSendData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String pspId;
	
	private String orderId;
	
	/**
	 * Original amount multiplied per 100:
	 * Ex: Original amount: 118.56 // Amount to send: 11856
	 */
	private String amount;
	
	/**
	 * Currency in alpha ISO code
	 */
	private String currency;
	
	/**
	 * Customer language
	 * The format is "language_Country".
	 * The language value is based on ISO 639-1.
	 * The country value is based on ISO 3166-1.
	 */
	private String language;
	
	private String acceptUrl;
	
	private String declineUrl;
	
	/**
	 * Fixed value "PAYPAL"
	 */
	private String pm;
	
	/**
	 * Fixed value "INIT"
	 */
	private String txtoken;
	
	/**
	 * In order to display a payment page adapted for mobile devices, send the value "mobile".
	 */
	private String device;
	
	private String shaSign;
	
	private String serviceEndpointPaypal;
	
	
	
	
	
	public IdentificationPaypalSendData(String site) {
		super();
		
		SdkConfigIngenico sdkConfigIngenico = ConstantsIngenico.getMapSdkConfigSites().get(site);
		
		this.pspId = sdkConfigIngenico.getPSPID();
		this.pm = "PAYPAL";
		this.txtoken =  "INIT";
		this.serviceEndpointPaypal = ConstantsIngenico.getSERVICE_ENDPOINT_PAYPAL();
	}

	
	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAcceptUrl() {
		return acceptUrl;
	}

	public void setAcceptUrl(String acceptUrl) {
		this.acceptUrl = acceptUrl;
	}

	public String getDeclineUrl() {
		return declineUrl;
	}

	public void setDeclineUrl(String declineUrl) {
		this.declineUrl = declineUrl;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}


	public String getPspId() {
		return pspId;
	}

	public String getPm() {
		return pm;
	}

	public String getTxtoken() {
		return txtoken;
	}




	public String getShaSign() {
		return shaSign;
	}




	public void setShaSign(String shaSign) {
		this.shaSign = shaSign;
	}




	public String getServiceEndpointPaypal() {
		return serviceEndpointPaypal;
	}
	
}
