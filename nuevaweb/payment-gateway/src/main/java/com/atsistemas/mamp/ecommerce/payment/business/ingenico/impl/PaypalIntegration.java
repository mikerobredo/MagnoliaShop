/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.business.ingenico.impl;

import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.ATTR_PAYPAL_STEP_ACCEPT_URL_PARAMETER;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_DECLINE_URL;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_DEVICE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_LANGUAGE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_OPERATION;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_ACCEPT_URL;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_AMOUNT;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_CURRENCY;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_MERCHANT_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_ORDER_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PAYPAL_PAY_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PM;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_TX_TOKEN;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OK_RESULT;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OPERATION_SALE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_AMOUNT;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_AUTHENT_STATUS;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_CONTACT_PHONE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_CURRENCY;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_CUSTOM;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_INVOICE_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_NO_ERROR_VALUE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_ORDER_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_CITYNAME;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_COUNTRY;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_NAME;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_POSTALCODE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_STATEORPROVINCE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_STATUS;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_STREET1;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_STREET2;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_BUSINESS;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_COUNTRY;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_EMAIL;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_FIRST_NAME;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_LAST_NAME;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_MIDDLE_NAME;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_SALUTATION;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_STATUS;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_SUFFIX;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PSPID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_SHASIGN;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_IDENTIFICATION_TXTOKEN;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_AAV_CHECK;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_ACCEPTANCE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_AMOUNT;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_BRAND;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_CARDNO;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_CCCTY;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_CN;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_CURRENCY;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_CVC_CHECK;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_COMMPERCENTAGE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_CONVAMOUNT;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_CONVCCY;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_EXCHRATE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_EXCHRATESOURCE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_EXCHRATETS;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_INDICATOR;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_MARGINPERCENTAGE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_VALIDHOURS;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_ECI;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_ED;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_IP;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_IPCTY;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_NCERROR;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_NO_ERROR_VALUE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_ORDER_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_PAYID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_PAYID_SUB;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_PM;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_SCORING;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_SCO_CATEGORY;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_SHASIGN;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_STATUS;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_TRXDATE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_PAYPAL_PAYMENT_VC;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.PAYPAL_IDENTIFICATION_KEY_MESSAGE_AUTHENTICATION_ERROR;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.PAYPAL_IDENTIFICATION_KEY_MESSAGE_SHA_OUT_ERROR;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.PAYPAL_ORDER_PARAM_ORDER_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.PAYPAL_ORDER_PARAM_TRANSACTION_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.VAL_PAYMENT_PAYPAL_STEP_ACCEPT_URL_PARAMETER;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsSymbols.EQUALS;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsSymbols.QUESTION_MARK;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atsistemas.mamp.ecommerce.payment.beans.ingenico.DataToBuildHtmlToSendPayment;
import com.atsistemas.mamp.ecommerce.payment.beans.ingenico.PaymentPaypalSendDataEcommerce;
import com.atsistemas.mamp.ecommerce.payment.beans.paypal.IdentificationPaypalSendData;
import com.atsistemas.mamp.ecommerce.payment.business.ingenico.interfaces.IPaypalIntegration;
import com.atsistemas.mamp.ecommerce.payment.common.ConstantsIngenico;
import com.atsistemas.mamp.ecommerce.payment.common.IConstantsCommon;
import com.atsistemas.mamp.ecommerce.payment.common.IConstantsPaypal;
import com.atsistemas.mamp.ecommerce.payment.common.IConstantsSymbols;
import com.atsistemas.mamp.ecommerce.payment.util.MessagesPaymentGateway;

import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;

/**
 * 
 *
 */
public class PaypalIntegration implements IPaypalIntegration {

	private Logger log = LoggerFactory.getLogger(PaypalIntegration.class);
	
	/* (non-Javadoc)
	 * @see com.atsistemas.mamp.ecommerce.payment.business.ingenico.interfaces.IPaypalIntegration#calculatePaypalIdentificationShaInHash(com.atsistemas.mamp.ecommerce.payment.beans.paypal.IdentificationPaypalSendData, java.lang.String)
	 */
	public String calculatePaypalIdentificationShaInHash(IdentificationPaypalSendData identificationPaypalSendData, String site) {
		
		String passphraseShaIn = ConstantsIngenico.getMapSdkConfigSites().get(site).getStringAPI_PASSPHRASE_SHAIN();
		
		String stringToEncode = INPUT_FIELD_PAYPAL_ACCEPT_URL +"="+identificationPaypalSendData.getAcceptUrl()+passphraseShaIn 
				+ INPUT_FIELD_PAYPAL_AMOUNT +"="+identificationPaypalSendData.getAmount()+passphraseShaIn  
				+ INPUT_FIELD_PAYPAL_CURRENCY +"="+identificationPaypalSendData.getCurrency()+passphraseShaIn 
				+ INPUT_FIELD_DECLINE_URL +"="+identificationPaypalSendData.getDeclineUrl()+passphraseShaIn; 
				
				if (! StringUtils.isEmpty(identificationPaypalSendData.getDevice())) {
					stringToEncode+= INPUT_FIELD_DEVICE +"="+identificationPaypalSendData.getDevice()+passphraseShaIn;
				}
				
				stringToEncode += INPUT_FIELD_LANGUAGE +"="+identificationPaypalSendData.getLanguage()+passphraseShaIn  
				+ INPUT_FIELD_PAYPAL_ORDER_ID +"="+identificationPaypalSendData.getOrderId()+passphraseShaIn 
				+ INPUT_FIELD_PM +"="+identificationPaypalSendData.getPm()+passphraseShaIn 
				+ INPUT_FIELD_PAYPAL_MERCHANT_ID +"="+identificationPaypalSendData.getPspId()+passphraseShaIn
				+ INPUT_FIELD_TX_TOKEN +"="+identificationPaypalSendData.getTxtoken()+passphraseShaIn;
		
		return DigestUtils.sha512Hex(stringToEncode);
	}

	/* (non-Javadoc)
	 * @see com.atsistemas.mamp.ecommerce.payment.business.ingenico.interfaces.IPaypalIntegration#managePaypalIdentificationResult(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	public String managePaypalIdentificationResult(HttpServletRequest request, String site) {

		String ret = null;
		
		String identificationResult = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_AUTHENT_STATUS);
		boolean identificationOk = OUTPUT_FIELD_PAYPAL_IDENTIFICATION_NO_ERROR_VALUE.equalsIgnoreCase(identificationResult);
		
		if (! identificationOk) {
			
			ret = MessagesPaymentGateway.getString(PAYPAL_IDENTIFICATION_KEY_MESSAGE_AUTHENTICATION_ERROR);
			this.log.error(ret);
			
		}else {
			
			String shaOutCalculated = this.calculatePaypalIdentificationShaOutHash(request, site);
			String shaInReceived = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_SHASIGN);
			boolean shaOutVerificationOk = (shaOutCalculated.equalsIgnoreCase(shaInReceived));
			
			if (shaOutVerificationOk ) {
				ret = OK_RESULT;
			}else {
				
				String transactionId = (MgnlContext.getAttribute(PAYPAL_ORDER_PARAM_TRANSACTION_ID) != null) ? MgnlContext.getAttribute(PAYPAL_ORDER_PARAM_TRANSACTION_ID).toString() : "desconocido";
				ret = MessagesPaymentGateway.getString(PAYPAL_IDENTIFICATION_KEY_MESSAGE_SHA_OUT_ERROR, 
						new Object[]{MgnlContext.getAttribute(PAYPAL_ORDER_PARAM_ORDER_ID), transactionId});
				this.log.error(ret);
			}
		}
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see com.atsistemas.mamp.ecommerce.payment.business.ingenico.interfaces.IPaypalIntegration#buildHtmlToSendPaymentData(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	public String buildHtmlToSendPaymentData(HttpServletRequest request, String site) {
		
		String ret = null;
		
		PaymentPaypalSendDataEcommerce paymentPaypalSendDataEcommerce = this.generatePaymentPaypalSendData(request);
		
		DataToBuildHtmlToSendPayment dataToBuildHtmlToSendPayment = new DataToBuildHtmlToSendPayment(paymentPaypalSendDataEcommerce, ConstantsIngenico.getSERVICE_ENDPOINT_PAYPAL(), this.calculatePaypalPaymentShaInHash(paymentPaypalSendDataEcommerce, site));
		
		ret = this.parseVelocityTemplate(IConstantsPaypal.PATH_TO_TEMPLATE_PAYMENT_STEP_SEND_DATA, dataToBuildHtmlToSendPayment);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.atsistemas.mamp.ecommerce.payment.business.ingenico.interfaces.IPaypalIntegration#managePaypalPaymentResult(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	public String managePaypalPaymentResult(HttpServletRequest request, String site) {

		String ret = null;
		
		String paymentResult = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_NCERROR);
		boolean paymentOk = OUTPUT_FIELD_PAYPAL_PAYMENT_NO_ERROR_VALUE.equalsIgnoreCase(paymentResult);
		
		if (! paymentOk) {
			
			ret = MessagesPaymentGateway.getString(paymentResult);
			this.log.error(ret);
			
		}else {
			
			String shaOutCalculated = this.calculatePaypalPaymentShaOutHash(request, site);
			String shaInReceived = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_SHASIGN);
			boolean shaOutVerificationOk = (shaOutCalculated.equalsIgnoreCase(shaInReceived));
			
			if (shaOutVerificationOk ) {
				
				ret = OK_RESULT;
				
			}else {
				
				ret = "Data received from payment result did not pass SHA verification";
				this.log.error(ret);
			}
		}
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see com.atsistemas.mamp.ecommerce.payment.business.ingenico.interfaces.IPaypalIntegration#resetPaymentDataFromSession()
	 */
	public void resetPaymentDataFromSession() {
		
		this.resetOrderDataFromSession();

		MgnlContext.setAttribute("currentSite", null, Context.SESSION_SCOPE);

	}
	
	
	
	
	
	


	private String parseVelocityTemplate(String _sRutaPlantilla, DataToBuildHtmlToSendPayment dataToBuildHtmlToSendPayment) {

		String ret = null;
		
		VelocityEngine veEngine = new VelocityEngine();
		VelocityContext vcContext = new VelocityContext();
		vcContext.put("dataToSendPayment", dataToBuildHtmlToSendPayment);
		
		StringWriter swStringWriter = new StringWriter();
		try {
			veEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
			veEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			veEngine.init();
			Template teTemplate = veEngine.getTemplate(_sRutaPlantilla,IConstantsCommon.UTF8);
			teTemplate.merge(vcContext, swStringWriter);
			ret = swStringWriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	private void resetOrderDataFromSession() {
		
		MgnlContext.setAttribute("identificationPaypalSendData", null, Context.SESSION_SCOPE);
	}
	
	private PaymentPaypalSendDataEcommerce generatePaymentPaypalSendData(HttpServletRequest request) {
		
		String site = MgnlContext.getAttribute("currentSite") != null ? MgnlContext.getAttribute("currentSite").toString() : null;
		IdentificationPaypalSendData identificationPaypalSendData = MgnlContext.getAttribute("identificationPaypalSendData") != null ? MgnlContext.getAttribute("identificationPaypalSendData") : null;
		
		PaymentPaypalSendDataEcommerce ret = null;
		
		if (StringUtils.isNotEmpty(site) && identificationPaypalSendData != null) {
		
			ret = new PaymentPaypalSendDataEcommerce(MgnlContext.getAttribute("currentSite").toString());
			
			ret.setAcceptUrl(ConstantsIngenico.getMapSdkConfigSites().get(site).getACCEPT_URL() + QUESTION_MARK 
					+ ATTR_PAYPAL_STEP_ACCEPT_URL_PARAMETER + EQUALS + VAL_PAYMENT_PAYPAL_STEP_ACCEPT_URL_PARAMETER);
			ret.setDeclineUrl(ConstantsIngenico.getMapSdkConfigSites().get(site).getDECLINE_URL());
			// We do not use amount contained into request because is not adapted
			ret.setAmount(identificationPaypalSendData.getAmount());
			ret.setCurrency(request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_CURRENCY));
			ret.setLanguage(request.getLocale().toString());
			ret.setOrderId(request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_ORDER_ID));
			ret.setPayId(request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYID));
			ret.setTxtoken(request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_TXTOKEN));
			
			// Overrides operation default configuration to directly send a payment request (not an authorisation request)
			ret.setOperation(OPERATION_SALE);
			
		}else {
			
			this.log.error("Datos de pago y/o configuracion faltantes");
		}
		
		return ret;
	}
	
	/**
	 * All sent parameters (which are included in SHA parameters list) must be included in SHA generation.
	 * Moreover SHA parameters must be uppercase and in alphabetic order
	 * 
	 * @param paymentPaypalSendDataEcommerce
	 * @param site
	 * @return hash calculated
	 */
	private String calculatePaypalPaymentShaInHash(PaymentPaypalSendDataEcommerce paymentPaypalSendDataEcommerce, String site) {
		
		String passphraseShaIn = ConstantsIngenico.getMapSdkConfigSites().get(site).getStringAPI_PASSPHRASE_SHAIN();
		
		String stringToEncode = 
				INPUT_FIELD_PAYPAL_ACCEPT_URL +"="+paymentPaypalSendDataEcommerce.getAcceptUrl()+passphraseShaIn
				+ INPUT_FIELD_PAYPAL_AMOUNT +"="+paymentPaypalSendDataEcommerce.getAmount()+passphraseShaIn  
				+ INPUT_FIELD_PAYPAL_CURRENCY +"="+paymentPaypalSendDataEcommerce.getCurrency()+passphraseShaIn 
				+ INPUT_FIELD_DECLINE_URL +"="+paymentPaypalSendDataEcommerce.getDeclineUrl()+passphraseShaIn
				+ INPUT_FIELD_LANGUAGE +"="+paymentPaypalSendDataEcommerce.getLanguage()+passphraseShaIn  
				+ INPUT_FIELD_OPERATION +"="+paymentPaypalSendDataEcommerce.getOperation()+passphraseShaIn
				+ INPUT_FIELD_PAYPAL_ORDER_ID +"="+paymentPaypalSendDataEcommerce.getOrderId()+passphraseShaIn
				+ INPUT_FIELD_PAYPAL_PAY_ID +"="+paymentPaypalSendDataEcommerce.getPayId()+passphraseShaIn
				+ INPUT_FIELD_PM +"="+paymentPaypalSendDataEcommerce.getPm()+passphraseShaIn 
				+ INPUT_FIELD_PAYPAL_MERCHANT_ID +"="+paymentPaypalSendDataEcommerce.getPspId()+passphraseShaIn
				+ INPUT_FIELD_TX_TOKEN +"="+paymentPaypalSendDataEcommerce.getTxtoken()+passphraseShaIn;
		
		return DigestUtils.sha512Hex(stringToEncode);
	}
	
	/**
	 * All sent parameters (which are included in SHA parameters list) with value must be included in SHA generation.
	 * This SHA-OUT calculation should not to be confused with the SHA-OUT calculation on the transaction feedback (see e-Commerce)
	 * 
	 * @param request
	 * @param site: site to load Paypal configuration
	 * @return hash calculated
	 */
	private String calculatePaypalIdentificationShaOutHash(HttpServletRequest request, String site) {
		
		String passphraseShaOut = ConstantsIngenico.getMapSdkConfigSites().get(site).getStringAPI_PASSPHRASE_SHAOUT();
		
		StringBuffer sbToEncode = new StringBuffer();
		
		String currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_EMAIL);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ID);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_STATUS);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_SALUTATION);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_FIRST_NAME);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_MIDDLE_NAME);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_LAST_NAME);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_SUFFIX);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_COUNTRY);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_BUSINESS);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_STATUS);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_NAME);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_STREET1);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_STREET2);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_CITYNAME);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_STATEORPROVINCE);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_POSTALCODE);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYER_ADR_COUNTRY);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_CUSTOM);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_INVOICE_ID);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_CONTACT_PHONE);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_TXTOKEN);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PAYID);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_PSPID);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_ORDER_ID);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_CURRENCY);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_AMOUNT);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_IDENTIFICATION_AUTHENT_STATUS);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(currentParameter);
		}
		
		sbToEncode.append(passphraseShaOut);
		
		return DigestUtils.sha512Hex(sbToEncode.toString());
	}
	
	/**
	 * All sent parameters (which are included in SHA parameters list) with value must be included in SHA generation.
	 * 
	 * @param request
	 * @param site: site to load Paypal configuration
	 * @return hash calculated
	 */
	private String calculatePaypalPaymentShaOutHash(HttpServletRequest request, String site) {
		
		String passphraseShaOut = ConstantsIngenico.getMapSdkConfigSites().get(site).getStringAPI_PASSPHRASE_SHAOUT();
		
		StringBuffer sbToEncode = new StringBuffer();
		
		String currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_AAV_CHECK);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_AAV_CHECK.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_ACCEPTANCE);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_ACCEPTANCE.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_AMOUNT);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_AMOUNT.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_BRAND);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_BRAND.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_CARDNO);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_CARDNO.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_CCCTY);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_CCCTY.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_CN);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_CN.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_CURRENCY);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_CURRENCY.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_CVC_CHECK);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_CVC_CHECK.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_COMMPERCENTAGE);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_COMMPERCENTAGE.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_CONVAMOUNT);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_CONVAMOUNT.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_CONVCCY);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_CONVCCY.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_EXCHRATE);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_EXCHRATE.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_EXCHRATESOURCE);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_EXCHRATESOURCE.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_EXCHRATETS);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_EXCHRATETS.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_INDICATOR);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_INDICATOR.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_MARGINPERCENTAGE);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_MARGINPERCENTAGE.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_VALIDHOURS);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_DCC_VALIDHOURS.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_ECI);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_ECI.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_ED);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_ED.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_IP);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_IP.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_IPCTY);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_IPCTY.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_NCERROR);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_NCERROR.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_ORDER_ID);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_ORDER_ID.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_PAYID);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_PAYID.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_PAYID_SUB);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_PAYID_SUB.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_PM);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_PM.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_SCO_CATEGORY);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_SCO_CATEGORY.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_SCORING);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_SCORING.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_STATUS);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_STATUS.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_TRXDATE);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_TRXDATE.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		currentParameter = request.getParameter(OUTPUT_FIELD_PAYPAL_PAYMENT_VC);
		if (! StringUtils.isEmpty(currentParameter)) {
			sbToEncode.append(OUTPUT_FIELD_PAYPAL_PAYMENT_VC.toUpperCase()).append(IConstantsSymbols.EQUALS).append(currentParameter).append(passphraseShaOut);
		}
		
		return DigestUtils.sha512Hex(sbToEncode.toString());
	}
	

}
