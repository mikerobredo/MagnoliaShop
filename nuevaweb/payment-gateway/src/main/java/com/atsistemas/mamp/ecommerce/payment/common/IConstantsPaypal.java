/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.common;

/**
 * 
 *
 */
public interface IConstantsPaypal {

	String productNullError = "product.null.error";
	String quantityAmountNotNullError = "quantity.amount.not.null.error";
	String quantityAmountValidNumberError = "quantity.amount.valid.number.error";
	String listPaymentItemDetailsNullError = "list.payment.item.details.null.error";
	
	
	String ACK_SUCCESS = "success";
	
	String SDK_CONFIG_PROP_ACCT1_PWD = "acct1.Password";
	String SDK_CONFIG_PROP_SELLER_EMAIL = "seller.EmailAddress";
	String SDK_CONFIG_PROP_ENDPOINT_PAYPAL = "service.endpoint.paypal";
	String SDK_CONFIG_NODE_COMMON_NAME = "common";
	
	String PATH_TO_TEMPLATE_PAYMENT_STEP_SEND_DATA = "payment-gateway/templates/paypal_payment_step_send_data.xhtml";
}
