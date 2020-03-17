/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.beans;

/**
 * 
 *
 */
public class PaypalPaymentData {

	private String currency;
	private String productCost;
	private String productUnits;
	private String productName;
	private String productDescription;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getProductCost() {
		return productCost;
	}
	public void setProductCost(String productCost) {
		this.productCost = productCost;
	}
	public String getProductUnits() {
		return productUnits;
	}
	public void setProductUnits(String productUnits) {
		this.productUnits = productUnits;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	
}
