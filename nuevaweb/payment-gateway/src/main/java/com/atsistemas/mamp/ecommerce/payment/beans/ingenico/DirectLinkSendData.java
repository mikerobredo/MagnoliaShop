/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.beans.ingenico;

/**
 * 
 *
 */
public class DirectLinkSendData {

	private String pspId;
	private String orderId;
	private String userId;
	private String pswd;
	private String alias;
	private String amount;
	private String currency;
	private String operation;
	

	public String getPspId() {
		return pspId;
	}

	public void setPspId(String pspId) {
		this.pspId = pspId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAmount() {
		return amount;
	}

	/**
	 * Ingenico divides the amount per 100, so setter multiplies per 100
	 * 
	 * @param amount
	 */
	public void setAmount(String amount) {
		Double amountDouble = Double.valueOf(amount)*100;
		this.amount = String.valueOf(amountDouble.intValue());
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
}
