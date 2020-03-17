/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.beans.ingenico;

import org.codehaus.jackson.annotate.JsonProperty;

import com.atsistemas.mamp.ecommerce.payment.common.ConstantsIngenico;

/**
 * 
 *
 */
public class AliasGatewaySendData {

	private String acceptUrl;
	
	private String exceptionUrl;
	
	private String orderId;
	
	private String pspId;
	
	private String shaSign;
	
	private String serviceEndpointAliasGateway;

	
	

	public AliasGatewaySendData(@JsonProperty("orderId") String orderId) {
		super();
		
		this.orderId = orderId;
		this.serviceEndpointAliasGateway = ConstantsIngenico.getENDPOINT_ALIAS_GATEWAY();
	}
	
	
	

	public String getAcceptUrl() {
		return acceptUrl;
	}

	public void setAcceptUrl(String acceptUrl) {
		this.acceptUrl = acceptUrl;
	}

	public String getExceptionUrl() {
		return exceptionUrl;
	}

	public void setExceptionUrl(String exceptionUrl) {
		this.exceptionUrl = exceptionUrl;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPspId() {
		return pspId;
	}
	
	public void setPspId(String pspId) {
		this.pspId = pspId;
	}

	public String getShaSign() {
		return shaSign;
	}

	public void setShaSign(String shaSign) {
		this.shaSign = shaSign;
	}




	public String getServiceEndpointAliasGateway() {
		return serviceEndpointAliasGateway;
	}

	
}
