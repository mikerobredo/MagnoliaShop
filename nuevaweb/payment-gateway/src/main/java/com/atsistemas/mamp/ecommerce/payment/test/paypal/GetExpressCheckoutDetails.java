/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.test.paypal;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsReq;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.eBLBaseComponents.ErrorType;

//# GetExpressCheckout API
// The GetExpressCheckoutDetails API operation obtains information about
// an Express Checkout transaction.
// This sample code uses Merchant Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
public class GetExpressCheckoutDetails {

	public GetExpressCheckoutDetailsResponseType getExpressCheckout(String token) {

		Logger logger = LoggerFactory.getLogger(this.getClass().toString());

		// ## GetExpressCheckoutDetailsReq
		GetExpressCheckoutDetailsReq getExpressCheckoutDetailsReq = new GetExpressCheckoutDetailsReq();

		// A timestamped token, the value of which was returned by
		// `SetExpressCheckout` response.
		GetExpressCheckoutDetailsRequestType getExpressCheckoutDetailsRequest = new GetExpressCheckoutDetailsRequestType(token);

		getExpressCheckoutDetailsReq.setGetExpressCheckoutDetailsRequest(getExpressCheckoutDetailsRequest);

		// ## Creating service wrapper object
		// Creating service wrapper object to make API call and loading
		// configuration file for your credentials and endpoint
		PayPalAPIInterfaceServiceService service = null;
		try {
			
			service = new PayPalAPIInterfaceServiceService("src/main/resources/paypalCredentials/sdk_config.properties");
			
		} catch (IOException e) {
			logger.error("Error Message : " + e.getMessage());
		}

		GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			 getExpressCheckoutDetailsResponse = service.getExpressCheckoutDetails(getExpressCheckoutDetailsReq);
			 
		} catch (Exception e) {
			logger.error("Error Message : " + e.getMessage());
		}
		
		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (getExpressCheckoutDetailsResponse.getAck().getValue().equalsIgnoreCase("success")) {

			// Unique PayPal Customer Account identification number. This
			// value will be null unless you authorize the payment by
			// redirecting to PayPal after `SetExpressCheckout` call.
			logger.info("PayerID : "+ getExpressCheckoutDetailsResponse.getGetExpressCheckoutDetailsResponseDetails().getPayerInfo().getPayerID());

		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			
			List<ErrorType> errorList = getExpressCheckoutDetailsResponse
					.getErrors();
			logger.error("API Error Message : " + errorList.get(0).getLongMessage());
		}
		
		return getExpressCheckoutDetailsResponse;
	}
}

