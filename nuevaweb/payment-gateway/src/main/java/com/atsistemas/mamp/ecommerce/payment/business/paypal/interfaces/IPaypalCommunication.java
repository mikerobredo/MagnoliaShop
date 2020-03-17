/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.business.paypal.interfaces;

import com.atsistemas.mamp.ecommerce.payment.beans.paypal.Phase1GetToken;

import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;

/**
 * 
 *
 */
public interface IPaypalCommunication {

	/**
	 * @param phase1GetToken
	 * @return response from API call to set express checkout operation
	 */
	SetExpressCheckoutResponseType setExpressCheckout(Phase1GetToken phase1GetToken);
	
	/**
	 * @param token
	 * @param currentSite
	 * @return details from response of express checkout details operation
	 */
	GetExpressCheckoutDetailsResponseType getExpressCheckoutDetails(String token, String currentSite);
	
	/**
	 * @param getExpressCheckoutDetailsResponseType
	 * @param currentSite
	 * @return response from API call to do express checkout operation
	 */
	DoExpressCheckoutPaymentResponseType doExpressCheckout(GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponseType, String currentSite);
	
}
