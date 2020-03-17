/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.beans.ingenico;

import com.atsistemas.mamp.ecommerce.payment.common.ConstantsIngenico;

/**
 * 
 *
 */
public class PaymentPaypalSendDataEcommerce {
	
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
	 * Same value received from payment platform in previous step
	 */
	private String txtoken;
	
	/**
	 * Same value received from payment platform in previous step
	 */
	private String payId;
	
	/**
	 * Operation code: sale or authorisation (it overrides default Ingenico configuration)
	 */
	private String operation;
	
	private SdkConfigIngenico sdkConfigIngenico;

	
	
	public PaymentPaypalSendDataEcommerce(String site) {
		super();
		
		this.sdkConfigIngenico = ConstantsIngenico.getMapSdkConfigSites().get(site);
		
		this.pspId = this.sdkConfigIngenico.getPSPID();
		this.pm = "PAYPAL";
	}



	public String getPspId() {
		return pspId;
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



	public String getPm() {
		return pm;
	}



	public String getTxtoken() {
		return txtoken;
	}



	public void setTxtoken(String txtoken) {
		this.txtoken = txtoken;
	}



	public String getPayId() {
		return payId;
	}



	public void setPayId(String payId) {
		this.payId = payId;
	}



	public SdkConfigIngenico getSdkConfigIngenico() {
		return sdkConfigIngenico;
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



	public String getOperation() {
		return operation;
	}



	public void setOperation(String operation) {
		this.operation = operation;
	}
	
}
