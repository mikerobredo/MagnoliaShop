/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.test.paypal;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.PaymentInfoType;

//# DoExpressCheckout API
// The DoExpressCheckoutPayment API operation completes an Express Checkout
// transaction. If you set up a billing agreement in your SetExpressCheckout
// API call, the billing agreement is created when you call the
// DoExpressCheckoutPayment API operation.
// This sample code uses Merchant Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
public class DoExpressCheckout {

	public DoExpressCheckoutPaymentResponseType doExpressCheckout(GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponseType) {

		Logger logger = LoggerFactory.getLogger(this.getClass().toString());

		// ## DoExpressCheckoutPaymentReq
		DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();

		DoExpressCheckoutPaymentRequestDetailsType doExpressCheckoutPaymentRequestDetails = new DoExpressCheckoutPaymentRequestDetailsType();

		// The timestamped token value that was returned in the
		// `SetExpressCheckout` response and passed in the
		// `GetExpressCheckoutDetails` request.
		doExpressCheckoutPaymentRequestDetails.setToken(getExpressCheckoutDetailsResponseType.getGetExpressCheckoutDetailsResponseDetails().getToken());

		// Unique paypal buyer account identification number as returned in
		// `GetExpressCheckoutDetails` Response
		doExpressCheckoutPaymentRequestDetails.setPayerID(getExpressCheckoutDetailsResponseType.getGetExpressCheckoutDetailsResponseDetails().getPayerInfo().getPayerID());

		doExpressCheckoutPaymentRequestDetails
				.setPaymentDetails(getExpressCheckoutDetailsResponseType.getGetExpressCheckoutDetailsResponseDetails().getPaymentDetails());
		DoExpressCheckoutPaymentRequestType doExpressCheckoutPaymentRequest = new DoExpressCheckoutPaymentRequestType(
				doExpressCheckoutPaymentRequestDetails);
		doExpressCheckoutPaymentReq
				.setDoExpressCheckoutPaymentRequest(doExpressCheckoutPaymentRequest);

		// ## Creating service wrapper object
		// Creating service wrapper object to make API call and loading
		// configuration file for your credentials and endpoint
		PayPalAPIInterfaceServiceService service = null;
		try {
			service = new PayPalAPIInterfaceServiceService(
					"src/main/resources/paypalCredentials/sdk_config.properties");
		} catch (IOException e) {
			logger.error("Error Message : " + e.getMessage());
		}
		DoExpressCheckoutPaymentResponseType doExpressCheckoutPaymentResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			 doExpressCheckoutPaymentResponse = service
					.doExpressCheckoutPayment(doExpressCheckoutPaymentReq);
		} catch (Exception e) {
			logger.error("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (doExpressCheckoutPaymentResponse.getAck().getValue()
				.equalsIgnoreCase("success")) {

			// Transaction identification number of the transaction that was
			// created.
			// This field is only returned after a successful transaction
			// for DoExpressCheckout has occurred.
			if (doExpressCheckoutPaymentResponse
					.getDoExpressCheckoutPaymentResponseDetails()
					.getPaymentInfo() != null) {
				Iterator<PaymentInfoType> paymentInfoIterator = doExpressCheckoutPaymentResponse
						.getDoExpressCheckoutPaymentResponseDetails()
						.getPaymentInfo().iterator();
				while (paymentInfoIterator.hasNext()) {
					PaymentInfoType paymentInfo = paymentInfoIterator
							.next();
					logger.info("Transaction ID : "
							+ paymentInfo.getTransactionID());
				}
			}
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			List<ErrorType> errorList = doExpressCheckoutPaymentResponse
					.getErrors();
			logger.error("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}
		return doExpressCheckoutPaymentResponse;
	}
}
