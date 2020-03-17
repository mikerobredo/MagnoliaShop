/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.test.paypal;

import java.io.IOException;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atsistemas.mamp.ecommerce.payment.beans.paypal.Phase1GetToken;
import com.atsistemas.mamp.ecommerce.payment.business.paypal.impl.PaypalCommunication;

import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;

/**
 * 
 *
 */
public class TestPaypal {

	private Logger log = LoggerFactory.getLogger(TestPaypal.class);
	 
	public static void main(String[] args) throws Exception {
		
//		PayPalNoApi payPal = new PayPalNoApi();
//		payPal.setExpressCheckout();
		
		TestPaypal testPaypal = new TestPaypal();
		testPaypal.executeDefaultTest();
//		testPaypal.executeApiTest();
	}
	
	private void executeDefaultTest() {
		
		SetExpressCheckout setExpressCheckoutService = new SetExpressCheckout();
		SetExpressCheckoutResponseType expressCheckoutResponseType = setExpressCheckoutService.setExpressCheckout();
		
		// It should redirect user to redirect URL to login into Paypal and authorise the payment
		
		GetExpressCheckoutDetails getExpressCheckoutDetails = new GetExpressCheckoutDetails();
		GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponseType = getExpressCheckoutDetails.getExpressCheckout(expressCheckoutResponseType.getToken());
		
		DoExpressCheckout doExpressCheckout = new DoExpressCheckout();
		DoExpressCheckoutPaymentResponseType doExpressCheckoutPaymentResponseType = doExpressCheckout.doExpressCheckout(getExpressCheckoutDetailsResponseType);
		
		System.out.println("Test finished");
	}
	
	private void executeApiTest() throws LoginException, RepositoryException, IOException {
	
		PaypalCommunication paypalCommunication = new PaypalCommunication();
		
		// This constant must be equal to config node in Magnolia configuration under "/modules/payment-gateway/paypal"
		String currentSite = "default-site";
		
		Phase1GetToken phase1GetToken = new Phase1GetToken("http://www.ciberespacio.com.ve/wp-content/uploads/2015/02/ok.png", "https://www.onedesk.com/wordpress/wp-content/uploads/2013/02/fail.png", 
				CurrencyCodeType.USD.toString(), Double.toString(Math.random()), currentSite, "2.00", 1, "Product description");
		SetExpressCheckoutResponseType setExpressCheckoutResponseType = paypalCommunication.setExpressCheckout(phase1GetToken);
		
		// It should redirect user to redirect URL to login into Paypal and authorise the payment
		
		GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponseType = paypalCommunication.getExpressCheckoutDetails(setExpressCheckoutResponseType.getToken(), currentSite);
		
		DoExpressCheckoutPaymentResponseType doExpressCheckoutPaymentResponseType = paypalCommunication.doExpressCheckout(getExpressCheckoutDetailsResponseType, currentSite);
		
		System.out.println("Test finished");
		
	}
	
	
}
