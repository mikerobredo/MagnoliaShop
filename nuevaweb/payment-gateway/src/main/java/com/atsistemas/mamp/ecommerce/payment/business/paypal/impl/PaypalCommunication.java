/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.business.paypal.impl;

import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsPaypal.ACK_SUCCESS;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsPaypal.SDK_CONFIG_PROP_SELLER_EMAIL;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsPaypal.listPaymentItemDetailsNullError;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsPaypal.quantityAmountNotNullError;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsPaypal.quantityAmountValidNumberError;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atsistemas.mamp.ecommerce.payment.beans.paypal.Phase1GetToken;
import com.atsistemas.mamp.ecommerce.payment.beans.paypal.factories.impl.PaymentDetailsItemTypeFactory;
import com.atsistemas.mamp.ecommerce.payment.beans.paypal.factories.interfaces.IPaymentDetailsItemTypeFactory;
import com.atsistemas.mamp.ecommerce.payment.business.paypal.interfaces.IPaypalCommunication;
import com.atsistemas.mamp.ecommerce.payment.common.ConstantsPaypal;
import com.atsistemas.mamp.ecommerce.payment.util.MessagesPaymentGateway;

import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsReq;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SellerDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;

/**
 * 
 *
 */
public class PaypalCommunication implements IPaypalCommunication {

	Logger logger = LoggerFactory.getLogger(this.getClass().toString());
	
	private IPaymentDetailsItemTypeFactory paymentDetailsItemTypeFactory = new PaymentDetailsItemTypeFactory();

	/* (non-Javadoc)
	 * @see com.atsistemas.mamp.ecommerce.payment.business.paypal.interfaces.IPaypalCommunication#setExpressCheckout(com.atsistemas.mamp.ecommerce.payment.beans.paypal.Phase1GetToken)
	 */
	public SetExpressCheckoutResponseType setExpressCheckout(Phase1GetToken phase1GetToken) {

		SetExpressCheckoutResponseType ret = null;
		
		// Creating service wrapper object to make API call and loading
		// configuration file for your credentials and endpoint
		PayPalAPIInterfaceServiceService service = null;
		try {
			
			service = new PayPalAPIInterfaceServiceService(ConstantsPaypal.getSdkConfig(phase1GetToken.getCurrentSite()));
			
			ret = service.setExpressCheckout(this.generateExpressCheckoutReq(phase1GetToken));
			
			if (! ret.getAck().getValue().equalsIgnoreCase(ACK_SUCCESS)) {

				List<ErrorType> errorList = ret.getErrors();
				if (errorList != null) {
					for (ErrorType errorType : errorList) {
						logger.error("API Error Message : "	+ errorType.getLongMessage());
					}
				}
				ret = null;

			}
			
		} catch (IOException e) {
			logger.error("Error Message : " + e.getMessage());
			ret = null;
		} catch (Exception e) {
			logger.error("Error Message : " + e.getMessage());
			ret = null;
		}
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see com.atsistemas.mamp.ecommerce.payment.business.paypal.interfaces.IPaypalCommunication#getExpressCheckoutDetails(java.lang.String, java.lang.String)
	 */
	public GetExpressCheckoutDetailsResponseType getExpressCheckoutDetails(String token, String currentSite) {

		GetExpressCheckoutDetailsResponseType ret = null;
		
		Logger logger = LoggerFactory.getLogger(this.getClass().toString());

		GetExpressCheckoutDetailsRequestType getExpressCheckoutDetailsRequest = new GetExpressCheckoutDetailsRequestType(token);

		GetExpressCheckoutDetailsReq getExpressCheckoutDetailsReq = new GetExpressCheckoutDetailsReq();
		getExpressCheckoutDetailsReq.setGetExpressCheckoutDetailsRequest(getExpressCheckoutDetailsRequest);

		try {
			
			PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(ConstantsPaypal.getSdkConfig(currentSite));

			ret = service.getExpressCheckoutDetails(getExpressCheckoutDetailsReq);
			 
		} catch (IOException e) {
			logger.error("Error Message : " + e.getMessage());
			ret = null;
		} catch (Exception e) {
			logger.error("Error Message : " + e.getMessage());
			ret = null;
		}
		
		if (! ret.getAck().getValue().equalsIgnoreCase(ACK_SUCCESS)) {

			List<ErrorType> errorList = ret.getErrors();
			if (errorList != null) {
				for (ErrorType errorType : errorList) {
					logger.error("API Error Message : "	+ errorType.getLongMessage());
				}
			}
			ret = null;

		}
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see com.atsistemas.mamp.ecommerce.payment.business.paypal.interfaces.IPaypalCommunication#doExpressCheckout(urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType, java.lang.String)
	 */
	public DoExpressCheckoutPaymentResponseType doExpressCheckout(GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponseType, String currentSite) {

		DoExpressCheckoutPaymentResponseType ret = null;
		
		Logger logger = LoggerFactory.getLogger(this.getClass().toString());

		DoExpressCheckoutPaymentRequestDetailsType doExpressCheckoutPaymentRequestDetails = new DoExpressCheckoutPaymentRequestDetailsType();
		doExpressCheckoutPaymentRequestDetails.setToken(getExpressCheckoutDetailsResponseType.getGetExpressCheckoutDetailsResponseDetails().getToken());
		doExpressCheckoutPaymentRequestDetails.setPayerID(getExpressCheckoutDetailsResponseType.getGetExpressCheckoutDetailsResponseDetails().getPayerInfo().getPayerID());
		doExpressCheckoutPaymentRequestDetails.setPaymentDetails(getExpressCheckoutDetailsResponseType.getGetExpressCheckoutDetailsResponseDetails().getPaymentDetails());
		
		DoExpressCheckoutPaymentRequestType doExpressCheckoutPaymentRequest = new DoExpressCheckoutPaymentRequestType(doExpressCheckoutPaymentRequestDetails);
		
		DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();
		doExpressCheckoutPaymentReq.setDoExpressCheckoutPaymentRequest(doExpressCheckoutPaymentRequest);

		PayPalAPIInterfaceServiceService service = null;
		try {
			
			service = new PayPalAPIInterfaceServiceService(ConstantsPaypal.getSdkConfig(currentSite));

			ret = service.doExpressCheckoutPayment(doExpressCheckoutPaymentReq);
			
		} catch (IOException e) {
			logger.error("Error Message : " + e.getMessage());
			ret = null;
		} catch (Exception e) {
			logger.error("Error Message : " + e.getMessage());
			ret = null;
		}

		if (! ret.getAck().getValue().equalsIgnoreCase(ACK_SUCCESS)) {

			List<ErrorType> errorList = ret.getErrors();
			if (errorList != null) {
				for (ErrorType errorType : errorList) {
					logger.error("API Error Message : "	+ errorType.getLongMessage());
				}
			}
			ret = null;
			
		}
		
		return ret;
	}
	
	
	private String getTotalAmountOfPaymentDetails(List<PaymentDetailsItemType> listPaymentDetailsItem) {

		String ret = null;
		
		if (listPaymentDetailsItem != null) {
			
			double totalAmount = 0.0;
			for (PaymentDetailsItemType paymentDetailsItemType : listPaymentDetailsItem) {
				try {
					totalAmount += (Double.valueOf(paymentDetailsItemType.getQuantity()) * Double.valueOf(paymentDetailsItemType.getAmount().getValue()));
				} catch (NullPointerException e) {
					
					logger.error("Error Message : " + MessagesPaymentGateway.getString(quantityAmountNotNullError) + paymentDetailsItemType.getName() + "\n" + e.getMessage());
					
				} catch (NumberFormatException e) {
					
					logger.error("Error Message : " + MessagesPaymentGateway.getString(quantityAmountValidNumberError) + paymentDetailsItemType.getName() + "\n" + e.getMessage());
					
				}
			}
			
			BigDecimal big = new BigDecimal(totalAmount);
			big = big.setScale(2, RoundingMode.HALF_UP);
			ret = String.valueOf(big);
			
		}else {
			logger.error("Error Message : " + MessagesPaymentGateway.getString(listPaymentItemDetailsNullError));
		}
		
		return ret;
	}
	
	private List<PaymentDetailsType> generatePaymentsDetailsType(Phase1GetToken phase1GetToken) {
		
		// ### Payment Information
		// list of information about the payment
		List<PaymentDetailsType> ret = new ArrayList<PaymentDetailsType>();
		
		// information about the first payment
		// we have only one payment by now
		PaymentDetailsType paymentDetails1 = new PaymentDetailsType();
		
		List<PaymentDetailsItemType> listPaymentDetailsItem = this.generatePaymentDetailsItemsType(phase1GetToken);
		
		// Add items in first payment
		paymentDetails1.setPaymentDetailsItem(listPaymentDetailsItem);

		// Total cost of the transaction to the buyer. If shipping cost and tax
		// charges are known, include them in this value. If not, this value
		// should be the current sub-total of the order.
		// Total amount gets a common given currency code. It does not check if all item´s currency codes are equal
		BasicAmountType orderTotal1 = new BasicAmountType(CurrencyCodeType.fromValue(phase1GetToken.getCurrency()),
				this.getTotalAmountOfPaymentDetails(listPaymentDetailsItem));
		paymentDetails1.setOrderTotal(orderTotal1);

		// How you want to obtain payment. When implementing
		// digital goods, this field is required and must be set to `Sale`:
		// 
		// * `Sale` - This is a final sale for which you are requesting payment
		// (default).
		// `Note:
		// You cannot set this field to Sale in SetExpressCheckout request and
		// then change the value to Authorization or Order in the
		// DoExpressCheckoutPayment request. If you set the field to
		// Authorization or Order in SetExpressCheckout, you may set the field
		// to Sale.`
		paymentDetails1.setPaymentAction(PaymentActionCodeType.SALE);

		paymentDetails1.setSellerDetails(this.generateSellerDetails(phase1GetToken.getCurrentSite()));

		// A unique identifier of the specific payment request, which is
		// required for parallel payments.
		paymentDetails1.setPaymentRequestID(phase1GetToken.getOrderId());

		ret.add(paymentDetails1);
		
		return ret;
	}
	
	private List<PaymentDetailsItemType> generatePaymentDetailsItemsType(Phase1GetToken phase1GetToken) {
		
		// list of items in first payment
		List<PaymentDetailsItemType> ret = new LinkedList<PaymentDetailsItemType>();
			
		ret.add(this.paymentDetailsItemTypeFactory.create(phase1GetToken.getSinglePrice(), phase1GetToken.getQuantity(), phase1GetToken.getDescription(), CurrencyCodeType.fromValue(phase1GetToken.getCurrency())));
		
		return ret;
	}
	
	private SellerDetailsType generateSellerDetails(String currentSite) {
		
		// Unique identifier for the merchant. For parallel payments, this field
		// is required and must contain the Payer Id or the email address of the
		// merchant.
		SellerDetailsType ret = new SellerDetailsType();

		ret.setPayPalAccountID(ConstantsPaypal.getSdkConfig(currentSite).get(SDK_CONFIG_PROP_SELLER_EMAIL));
		
		return ret;
	}
	
	private SetExpressCheckoutReq generateExpressCheckoutReq(Phase1GetToken phase1GetToken) {
		
		SetExpressCheckoutReq ret = new SetExpressCheckoutReq();
		
		// ## SetExpressCheckoutReq
		SetExpressCheckoutRequestDetailsType setExpressCheckoutRequestDetails = new SetExpressCheckoutRequestDetailsType();
	
		// URL to which the buyer's browser is returned after choosing to pay
		// with PayPal.
		setExpressCheckoutRequestDetails
				.setReturnURL(phase1GetToken.getReturnURL());
	
		// URL to which the buyer is returned if the buyer does not approve the
		// use of PayPal to pay you.
		setExpressCheckoutRequestDetails
				.setCancelURL(phase1GetToken.getCancelURL());
		
		setExpressCheckoutRequestDetails.setPaymentDetails(this.generatePaymentsDetailsType(phase1GetToken));
	
		SetExpressCheckoutRequestType setExpressCheckoutRequest = new SetExpressCheckoutRequestType(setExpressCheckoutRequestDetails);
	
		ret.setSetExpressCheckoutRequest(setExpressCheckoutRequest);
		
		return ret;
	}
	
}
