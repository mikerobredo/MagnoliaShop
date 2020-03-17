/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.business.paypal.servlets;

import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.ERROR_MESSAGE_SEPARATOR;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.ERROR_RESULT;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atsistemas.mamp.ecommerce.payment.beans.PaypalPaymentData;
import com.atsistemas.mamp.ecommerce.payment.beans.paypal.Phase1GetToken;
import com.atsistemas.mamp.ecommerce.payment.business.paypal.interfaces.IPaypalCommunication;
import com.atsistemas.mamp.ecommerce.payment.common.ConstantsPaypal;

import info.magnolia.context.MgnlContext;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;

/**
 * 
 *
 */
public class PaypalNativeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Logger log = LoggerFactory.getLogger(PaypalNativeServlet.class);

	@Inject
	private IPaypalCommunication paypalCommunication;
	
	public PaypalNativeServlet() {
		super();
	}

	// Phase 1 call: setExpressCheckout
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			String currentSiteAttr = "jcrPaypalConfigName";
		String currentSite = MgnlContext.getAttribute(currentSiteAttr) != null ? MgnlContext.getAttribute(currentSiteAttr).toString() : null;
		
			String paymentDataAttr = "paymentData";
		String sJsonPaymentData = MgnlContext.getAttribute(paymentDataAttr) != null ? MgnlContext.getAttribute(paymentDataAttr).toString() : null;
		if (sJsonPaymentData != null && currentSiteAttr != null) {
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node;
			
			try {
				
				node = mapper.readTree(sJsonPaymentData);
				
				PaypalPaymentData paypalPaymentData = mapper.readValue(node, PaypalPaymentData.class);
				
				if (this.validatePaymentData(paypalPaymentData)) {
					String returnUrl = ConstantsPaypal.getSdkConfig(currentSite).get("returnUrl") + "?currentSite=" + currentSite;
					String cancelUrl = ConstantsPaypal.getSdkConfig(currentSite).get("cancelUrl");
					
					Phase1GetToken phase1GetToken = new Phase1GetToken(returnUrl, cancelUrl, paypalPaymentData.getCurrency(), Double.toString(Math.random()), 
							currentSite, paypalPaymentData.getProductCost(), Integer.valueOf(paypalPaymentData.getProductUnits()), paypalPaymentData.getProductDescription());
					SetExpressCheckoutResponseType setExpressCheckoutResponseType = paypalCommunication.setExpressCheckout(phase1GetToken);
					
					if (setExpressCheckoutResponseType != null) {
					
						String token = setExpressCheckoutResponseType.getToken();
						PrintWriter responseWriter = response.getWriter();
					    responseWriter.println(token);
					    responseWriter.flush();
					    responseWriter.close();
					    
					}else {
					
						this.managePaymentGatewayErrors(response, "Error trying Paypal setExpressCheckout. See log");
					}
				}
				
			} catch (JsonProcessingException e) {
				
				this.managePaymentGatewayErrors(response, "Problem reading payment data: " + e);
				
			}
			
		} else {
			
			this.managePaymentGatewayErrors(response, "Impossible to identify payment data");
		}
	}

	// Phase 2 call: doExpressCheckout
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String currentSite = request.getParameter("currentSite");
		String token = request.getParameter("token");
		String okUrl = ConstantsPaypal.getSdkConfig(currentSite).get("okUrl");
		String koUrl = ConstantsPaypal.getSdkConfig(currentSite).get("koUrl");
		
		GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponseType = paypalCommunication.getExpressCheckoutDetails(token, currentSite);
		
		DoExpressCheckoutPaymentResponseType doExpressCheckoutPaymentResponseType = paypalCommunication.doExpressCheckout(getExpressCheckoutDetailsResponseType, currentSite);
		
		if (doExpressCheckoutPaymentResponseType != null) {
			
			response.sendRedirect(okUrl);
			
		}else {
			
			this.managePaymentGatewayErrors(response, "Impossible to get payment response. See log");
			response.sendRedirect(koUrl);
		}
	}
	
	/**
	 * @param response
	 * @param concreteMsgError
	 * @throws IOException 
	 */
	private void managePaymentGatewayErrors(HttpServletResponse response, String concreteMsgError) throws IOException {
		
		log.error(concreteMsgError);
		
		response.sendError(500, ERROR_RESULT + ERROR_MESSAGE_SEPARATOR + concreteMsgError + ERROR_RESULT + ERROR_MESSAGE_SEPARATOR);
		
	}
	
	
	

	
	private boolean validatePaymentData(PaypalPaymentData paypalPaymentData) {
		return true;
	}
}
