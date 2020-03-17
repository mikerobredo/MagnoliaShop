/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.beans.ingenico;

import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_DECLINE_URL;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_LANGUAGE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_ACCEPT_URL;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_AMOUNT;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_CURRENCY;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_MERCHANT_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_OPERATION;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_ORDER_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_PAY_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_PSWD;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_SHA_HASH;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_USER_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PM;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_TX_TOKEN;

/**
 * 
 *
 */
public class DataToBuildHtmlToSendPayment {

	private PaymentPaypalSendDataEcommerce paymentPaypalSendData;
	private String endpoint;
	private String shaHash;
	private String inputFieldPaypalMerchantId = INPUT_FIELD_PAYPAL_MERCHANT_ID;
	private String inputFieldPaypalUserId = INPUT_FIELD_PAYPAL_USER_ID;
	private String inputFieldPaypalPswd = INPUT_FIELD_PAYPAL_PSWD;
	private String inputFieldPaypalOrderId = INPUT_FIELD_PAYPAL_ORDER_ID;
	private String inputFieldPaypalAmount = INPUT_FIELD_PAYPAL_AMOUNT;
	private String inputFieldPaypalCurrency = INPUT_FIELD_PAYPAL_CURRENCY;
	private String inputFieldPaypalLanguage = INPUT_FIELD_LANGUAGE;
	private String inputFieldPaypalPM = INPUT_FIELD_PM;
	private String inputFieldPaypalTxToken = INPUT_FIELD_TX_TOKEN;
	private String inputFieldPaypalPayId = INPUT_FIELD_PAYPAL_PAY_ID;
	private String inputFieldPaypalAcceptUrl = INPUT_FIELD_PAYPAL_ACCEPT_URL;
	private String inputFieldPaypalDeclineUrl = INPUT_FIELD_DECLINE_URL;
	private String inputFieldPaypalOperation = INPUT_FIELD_PAYPAL_OPERATION;
	private String inputFieldPaypalShaHash = INPUT_FIELD_PAYPAL_SHA_HASH;

	public DataToBuildHtmlToSendPayment(PaymentPaypalSendDataEcommerce paymentPaypalSendDataEcommerce, String endpoint, String shaHash) {
		super();

		this.paymentPaypalSendData = paymentPaypalSendDataEcommerce;
		this.endpoint = endpoint;
		this.shaHash = shaHash;
	}

	public PaymentPaypalSendDataEcommerce getPaymentPaypalSendData() {
		return paymentPaypalSendData;
	}

	public String getInputFieldPaypalMerchantId() {
		return inputFieldPaypalMerchantId;
	}

	public String getInputFieldPaypalUserId() {
		return inputFieldPaypalUserId;
	}

	public String getInputFieldPaypalPswd() {
		return inputFieldPaypalPswd;
	}

	public String getInputFieldPaypalOrderId() {
		return inputFieldPaypalOrderId;
	}

	public String getInputFieldPaypalAmount() {
		return inputFieldPaypalAmount;
	}

	public String getInputFieldPaypalCurrency() {
		return inputFieldPaypalCurrency;
	}

	public String getInputFieldPaypalLanguage() {
		return inputFieldPaypalLanguage;
	}

	public String getInputFieldPaypalPM() {
		return inputFieldPaypalPM;
	}

	public String getInputFieldPaypalTxToken() {
		return inputFieldPaypalTxToken;
	}

	public String getInputFieldPaypalPayId() {
		return inputFieldPaypalPayId;
	}

	public String getInputFieldPaypalAcceptUrl() {
		return inputFieldPaypalAcceptUrl;
	}

	public String getInputFieldPaypalDeclineUrl() {
		return inputFieldPaypalDeclineUrl;
	}

	public String getInputFieldPaypalOperation() {
		return inputFieldPaypalOperation;
	}

	public String getInputFieldPaypalShaHash() {
		return inputFieldPaypalShaHash;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public String getShaHash() {
		return shaHash;
	}
	
}
