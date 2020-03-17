/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.business.ingenico.interfaces;

import javax.servlet.http.HttpServletRequest;

import com.atsistemas.mamp.ecommerce.payment.beans.paypal.IdentificationPaypalSendData;

/**
 * 
 *
 */
public interface IPaypalIntegration {
	
	/**
	 * All sent parameters (which are included in SHA parameters list) must be included in SHA generation.
	 * Moreover SHA parameters must be uppercase and in alphabetic order
	 * 
	 * @param identificationPaypalSendData
	 * @param site
	 * @return hash calculated
	 */
	String calculatePaypalIdentificationShaInHash(IdentificationPaypalSendData identificationPaypalSendData, String site);
	
	/**
	 * Check the result of Paypal identification:
	 * 
	 * @param request
	 * @param site
	 * @return result of identification
	 */
	String managePaypalIdentificationResult(HttpServletRequest request, String site);

	/**
	 * @param request
	 * @param site
	 * @return a string representing an html that sends automatically all needed
	 * parameters to execute payment phase
	 */
	String buildHtmlToSendPaymentData(HttpServletRequest request, String site);
	
	/**
	 * Check the result of Paypal payment:
	 * 
	 * @param request
	 * @param site: Magnolia site name
	 * @return result of payment
	 */
	String managePaypalPaymentResult(HttpServletRequest request, String site);
	
	/**
	 * Reset session parameters containing payment data 
	 */
	void resetPaymentDataFromSession();
}
