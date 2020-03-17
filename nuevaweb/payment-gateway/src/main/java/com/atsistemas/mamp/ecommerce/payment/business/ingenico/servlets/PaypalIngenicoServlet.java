/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.business.ingenico.servlets;

import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.ERROR_MESSAGE_SEPARATOR;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.ERROR_RESULT;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OK_RESULT;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atsistemas.mamp.ecommerce.payment.beans.ingenico.SdkConfigIngenico;
import com.atsistemas.mamp.ecommerce.payment.beans.paypal.IdentificationPaypalSendData;
import com.atsistemas.mamp.ecommerce.payment.business.ingenico.interfaces.IPaypalIntegration;
import com.atsistemas.mamp.ecommerce.payment.common.ConstantsIngenico;

import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;

/**
 * 
 *
 */
public class PaypalIngenicoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Logger log = LoggerFactory.getLogger(PaypalIngenicoServlet.class);
	
	@Inject
	private IPaypalIntegration paypalIntegration;

	private SdkConfigIngenico sdkConfigIngenico;
	
	public PaypalIngenicoServlet() {
		super();
	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String messageToLog = null;
		
		String phase = MgnlContext.getParameter("phase") != null ? MgnlContext.getParameter("phase").toString() : null;
		
		if (StringUtils.isNotEmpty(phase)) {
			
			if ("identificationRequest".equalsIgnoreCase(phase)) {
				
				this.manageIdentificationRequest(request, response);
			
			}else if ("identificationResult".equalsIgnoreCase(phase)) {
				
				this.manageIdentificationResponse(request, response);
			}
			
		}else {

			messageToLog = "Unable to identify payment phase";
			this.managePaymentGatewayErrors(response, messageToLog);
		}
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String messageToLog = null;
		
		String phase = MgnlContext.getParameter("phase") != null ? MgnlContext.getParameter("phase").toString() : null;
		
		if (StringUtils.isNotEmpty(phase)) {
			
			if ("paymentResult".equalsIgnoreCase(phase)) {
				
				this.managePaymentPhaseResult(request, response);
			
			}else if ("identificationResult".equalsIgnoreCase(phase)) {
				
				this.manageIdentificationResponse(request, response);
			}
			
		}else {

			messageToLog = "Unable to identify payment phase";
			this.managePaymentGatewayErrors(response, messageToLog);
		}
	}
	
	
	
	
	

	
	private void managePaymentPhaseResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String site = MgnlContext.getAttribute("currentSite") != null ? MgnlContext.getAttribute("currentSite").toString() : null;
		if (! StringUtils.isEmpty(site)) {
			
			String paymentResult = this.paypalIntegration.managePaypalPaymentResult(request, site);
			if (OK_RESULT.equalsIgnoreCase(paymentResult)) {

				this.paypalIntegration.resetPaymentDataFromSession();
				
				String okUrl = ConstantsIngenico.getMapSdkConfigSites().get(site).getOK_URL();
				this.sendRedirect(response, okUrl, "");
				
			}else {
				
				this.managePaymentGatewayErrors(response, paymentResult);
			}
			
		}else {
			
			String errorMsg = "Unable to identify current site";
			this.managePaymentGatewayErrors(response, errorMsg);
		}
		
		
		// Reset payment step context parameters
		MgnlContext.setAttribute("identificationPaypalSendData", null, Context.SESSION_SCOPE);
		MgnlContext.setAttribute("currentSite", null, Context.SESSION_SCOPE);
	}
	
	private void sendRedirect(HttpServletResponse response, String uriToRedirect, String messageToLog) throws IOException {
		
		response.sendRedirect(uriToRedirect);
		
		if (! StringUtils.isEmpty(messageToLog)) {
			this.log(messageToLog);
		}
	}
	
	private void manageIdentificationRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String messageToLog = null;
	
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node;
		
		String site = MgnlContext.getAttribute("site") != null ? MgnlContext.getAttribute("site").toString() : null;
		
		if (StringUtils.isNotEmpty(site)) {
			
			this.sdkConfigIngenico = ConstantsIngenico.getMapSdkConfigSites().get(site);
			
			try {
				
				String sJsonPaymentPaypalSendData = MgnlContext.getAttribute("paymentData") != null ? MgnlContext.getAttribute("paymentData").toString() : null;
				node = mapper.readTree(sJsonPaymentPaypalSendData);
				
				IdentificationPaypalSendData identificationPaypalSendData = new IdentificationPaypalSendData(site);
				
				if (identificationPaypalSendData != null) {
	
					identificationPaypalSendData.setOrderId(node.get("orderId").toString());
						Integer amount = Double.valueOf(node.get("productCost").asDouble() * node.get("productUnits").asDouble() * 100).intValue();
					identificationPaypalSendData.setAmount(String.valueOf(amount));
					identificationPaypalSendData.setCurrency(node.get("currency").toString().replace("\"", ""));
					identificationPaypalSendData.setLanguage("ES");
					identificationPaypalSendData.setAcceptUrl(this.sdkConfigIngenico.getACCEPT_URL() + "?phase=identificationResult");
					identificationPaypalSendData.setDeclineUrl(this.sdkConfigIngenico.getDECLINE_URL() + "?phase=identificationResult");
					identificationPaypalSendData.setDevice(null);
					identificationPaypalSendData.setShaSign(this.paypalIntegration.calculatePaypalIdentificationShaInHash(identificationPaypalSendData, site));
					
					if (StringUtils.isNotEmpty(identificationPaypalSendData.getShaSign())) {
						
						// Save data payment in session
						MgnlContext.setAttribute("identificationPaypalSendData", identificationPaypalSendData, Context.SESSION_SCOPE);
						MgnlContext.setAttribute("currentSite", site, Context.SESSION_SCOPE);
						
						String jsonInString = mapper.writeValueAsString(identificationPaypalSendData);
						PrintWriter responseWriter = response.getWriter();
					    responseWriter.println(jsonInString);
					    responseWriter.flush();
					    responseWriter.close();
					    
					}else {
						
						String errorMsg = "Unable to generate SHAIN in identification phase";
						this.managePaymentGatewayErrors(response, errorMsg);
					}
				}
				
			} catch (JsonProcessingException e) {
				
				messageToLog = "Problem reading payment data: ";
				this.managePaymentGatewayErrors(response, messageToLog + e.getMessage());
				
			}
			
		}else {
			
			messageToLog = "It is necessary to identify Ingenico configuration"; 
			this.managePaymentGatewayErrors(response, messageToLog);
		}
	}


	private void manageIdentificationResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String site = MgnlContext.getAttribute("currentSite") != null ? MgnlContext.getAttribute("currentSite").toString() : null;
		
		if (StringUtils.isNotEmpty(site)) {
			
			String identificationResult = this.paypalIntegration.managePaypalIdentificationResult(request, site);
			if (OK_RESULT.equalsIgnoreCase(identificationResult)) {
				
				this.sendPaymentRequest(request, response);
				
			}else {
				
				this.managePaymentGatewayErrors(response, identificationResult);
			}
			
		}else {
			
			String messageToLog = "It is necessary to identify Ingenico configuration"; 
			this.managePaymentGatewayErrors(response, messageToLog);
		}
		
	}
	
	private void sendPaymentRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String site = MgnlContext.getAttribute("currentSite") != null ? MgnlContext.getAttribute("currentSite").toString() : null;

		if (StringUtils.isNotEmpty(site)) {
			
			response.setContentType(ContentType.TEXT_HTML.toString());
		    PrintWriter responseWriter = response.getWriter();

		    responseWriter.println(this.paypalIntegration.buildHtmlToSendPaymentData(request, site));
		    
		    responseWriter.flush();
		    responseWriter.close();
			
		}
	}
	
	/**
	 * @param response
	 * @param concreteMsgError
	 * @throws IOException 
	 */
	private void managePaymentGatewayErrors(HttpServletResponse response, String concreteMsgError) throws IOException {
		
		log.error(concreteMsgError);
		
		// Reset payment step context parameters
		MgnlContext.setAttribute("identificationPaypalSendData", null, Context.SESSION_SCOPE);
		MgnlContext.setAttribute("currentSite", null, Context.SESSION_SCOPE);
		
		response.sendError(500, ERROR_RESULT + ERROR_MESSAGE_SEPARATOR + concreteMsgError + ERROR_RESULT + ERROR_MESSAGE_SEPARATOR);
		
	}
}