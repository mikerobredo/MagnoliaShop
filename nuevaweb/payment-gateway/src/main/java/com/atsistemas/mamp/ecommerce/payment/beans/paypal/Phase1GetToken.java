/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.beans.paypal;


/**
 * 
 *
 */
public class Phase1GetToken {

	private String returnURL;
	private String cancelURL;
	private String currency;
	private String orderId;
	private String currentSite;
	private String singlePrice;
	private Integer quantity;
	private String description;
	
	/**
	 * @param returnURL
	 * @param cancelURL
	 * @param listItemsToBuy
	 * @param currency
	 */
	public Phase1GetToken(String returnURL, String cancelURL, String currency, String orderId, String currentSite, String singlePrice, Integer quantity, String description) {
		super();
		this.returnURL = returnURL;
		this.cancelURL = cancelURL;
		this.currency = currency;
		this.orderId = orderId;
		this.currentSite = currentSite;
		this.singlePrice = singlePrice;
		this.quantity = quantity;
		this.description = description;
				
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	public String getCancelURL() {
		return cancelURL;
	}

	public void setCancelURL(String cancelURL) {
		this.cancelURL = cancelURL;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCurrentSite() {
		return currentSite;
	}

	public void setCurrentSite(String currentSite) {
		this.currentSite = currentSite;
	}

	public String getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(String singlePrice) {
		this.singlePrice = singlePrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
