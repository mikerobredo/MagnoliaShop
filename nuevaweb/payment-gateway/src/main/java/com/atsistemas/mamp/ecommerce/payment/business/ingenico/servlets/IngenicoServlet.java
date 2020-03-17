/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.business.ingenico.servlets;

import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.ERROR_MESSAGE_SEPARATOR;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.ERROR_RESULT;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_ACCEPT_URL;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_EXCEPTION_URL;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_MERCHANT_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_ORDER_ID;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atsistemas.mamp.ecommerce.payment.beans.ingenico.AliasGatewaySendData;
import com.atsistemas.mamp.ecommerce.payment.beans.ingenico.DirectLinkSendData;
import com.atsistemas.mamp.ecommerce.payment.beans.ingenico.PublicPaymentUserData;
import com.atsistemas.mamp.ecommerce.payment.beans.ingenico.SdkConfigIngenico;
import com.atsistemas.mamp.ecommerce.payment.business.ingenico.interfaces.IIngenicoCommunication;
import com.atsistemas.mamp.ecommerce.payment.common.ConstantsIngenico;
import com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico;

import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;

/**
 * 
 *
 */
public class IngenicoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Logger log = LoggerFactory.getLogger(IngenicoServlet.class);
	
	@Inject
	private IIngenicoCommunication ingenicoCommunication;

	private SdkConfigIngenico sdkConfigIngenico;
	
	public IngenicoServlet() {
		super();
	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String messageToLog = null;
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node;
		
		String site = MgnlContext.getAttribute("site") != null ? MgnlContext.getAttribute("site").toString() : null;
		
		if (StringUtils.isNotEmpty(site)) {
			
			this.sdkConfigIngenico = ConstantsIngenico.getMapSdkConfigSites().get(site);
			
			try {
				
				String sJsonAliasGatewaySendData = MgnlContext.getAttribute("aliasGatewaySendData") != null ? MgnlContext.getAttribute("aliasGatewaySendData").toString() : null;
				node = mapper.readTree(sJsonAliasGatewaySendData);
				
				AliasGatewaySendData aliasGatewaySendData = mapper.readValue(node, AliasGatewaySendData.class);
				
				if (aliasGatewaySendData != null) {
					
					String sJsonPublicPaymentUserData = MgnlContext.getAttribute("paymentData") != null ? MgnlContext.getAttribute("paymentData").toString() : null;
					
					if (sJsonPublicPaymentUserData != null) {
						
						// Save data payment in session
						MgnlContext.setAttribute("publicPaymentUserData", sJsonPublicPaymentUserData, Context.SESSION_SCOPE);
						aliasGatewaySendData.setAcceptUrl(this.sdkConfigIngenico.getACCEPT_URL());
						aliasGatewaySendData.setExceptionUrl(this.sdkConfigIngenico.getEXCEPTION_URL());
						aliasGatewaySendData.setPspId(this.sdkConfigIngenico.getPSPID());
						aliasGatewaySendData.setShaSign(this.calculateAliasGatewayShaHash(aliasGatewaySendData, site));
						
						String jsonInString = mapper.writeValueAsString(aliasGatewaySendData);
						PrintWriter responseWriter = response.getWriter();
					    responseWriter.println(jsonInString);
					    responseWriter.flush();
					    responseWriter.close();
					    
					}else {
						
						messageToLog = "Missing payment data: product cost, units, etc";
						this.managePaymentGatewayErrors(response, messageToLog);
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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException {
		
		String messageToLog = null;
		
		try {
			
			String responseResult = this.ingenicoCommunication.processAliasGatewayResponse(request);
			
			if(responseResult != null){
	        	
	        	if(responseResult.contains(IConstantsIngenico.ERROR_RESULT)){
	        		
	        		this.managePaymentGatewayErrors(response, responseResult);
					
	        	}else{

	        		doIngenicoPay(responseResult, response);
	        		
	        	}
	        }else{
	        	
	        	messageToLog = "It was not possible to obtain the result in alias gateway phase";
	        	this.managePaymentGatewayErrors(response, messageToLog);
	        	
	        }
			
		} catch (URISyntaxException e) {
			
			this.managePaymentGatewayErrors(response, e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	 private void doIngenicoPay(String aliasGatewayResponse, HttpServletResponse response) throws IOException{
		
		if(aliasGatewayResponse != null){
			
			String sJsonPublicPaymentUserData = MgnlContext.getAttribute("publicPaymentUserData", Context.SESSION_SCOPE) != null ? MgnlContext.getAttribute("publicPaymentUserData", Context.SESSION_SCOPE) : null;
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(sJsonPublicPaymentUserData);
			
			PublicPaymentUserData publicPaymentUserData =  mapper.readValue(node, PublicPaymentUserData.class);
			
			if (publicPaymentUserData != null) {
				
				Integer amount = Double.valueOf((Double.valueOf(publicPaymentUserData.getProductCost())*Double.valueOf(publicPaymentUserData.getProductUnits()) * 100)).intValue();
				DirectLinkSendData directLinkSendData = new DirectLinkSendData();
				directLinkSendData.setPspId(this.sdkConfigIngenico.getPSPID());
				directLinkSendData.setUserId(this.sdkConfigIngenico.getAPI_USERID());
				directLinkSendData.setPswd(this.sdkConfigIngenico.getStringAPI_USER_PSWD());
				directLinkSendData.setAlias(aliasGatewayResponse);
				directLinkSendData.setAmount(amount.toString());
				directLinkSendData.setCurrency(publicPaymentUserData.getCurrency());
				directLinkSendData.setOperation(IConstantsIngenico.OPERATION_SALE);
				directLinkSendData.setOrderId(publicPaymentUserData.getOrderId());
				
				try {
				
					String directLinkResponse = this.ingenicoCommunication.manageDataPaymentToDirectLink(directLinkSendData, this.sdkConfigIngenico.getStringAPI_PASSPHRASE_SHAIN());
					
					if(directLinkResponse != null){
						
						if(! directLinkResponse.contains(IConstantsIngenico.ERROR_RESULT)) {
							
							// Reset session data
							MgnlContext.setAttribute("publicPaymentUserData", null, Context.SESSION_SCOPE);
							
							response.sendRedirect(this.sdkConfigIngenico.getOK_URL());
						
						}else{
							
							this.managePaymentGatewayErrors(response, directLinkResponse);
							
						}
						
					}else{
						
						String message = "It was not possible to obtain the result in direct link phase";
						this.managePaymentGatewayErrors(response, message);
					}
				
				} catch (Exception e) {
					
					String message = "Failed to pay by Ingenico";
					this.managePaymentGatewayErrors(response, message);
				}
			}else {
				
				String message = "It is necessary to obtain public payment data";
				this.managePaymentGatewayErrors(response, message);
			}
		}
		
	}
	
	/**
	 * @param response
	 * @param concreteMsgError
	 * @throws IOException 
	 */
	private void managePaymentGatewayErrors(HttpServletResponse response, String concreteMsgError) throws IOException {
		
		log.error(concreteMsgError);
		
		// Reset session data
		MgnlContext.setAttribute("publicPaymentUserData", null, Context.SESSION_SCOPE);
		
		response.sendError(500, ERROR_RESULT + ERROR_MESSAGE_SEPARATOR + concreteMsgError + ERROR_RESULT + ERROR_MESSAGE_SEPARATOR);
		
	}

	/**
	 * All sent parameters (which are included in SHA parameters list) must be included in SHA generation.
	 * Moreover SHA parameters must be uppercase and in alphabetic order
	 * 
	 * @param aliasGatewaySendData
	 * @param site
	 * @return hash calculated
	 */
	private String calculateAliasGatewayShaHash(AliasGatewaySendData aliasGatewaySendData, String site) {
		
		String passphraseShaIn = this.sdkConfigIngenico.getStringAPI_PASSPHRASE_SHAIN();
		
		String stringToEncode = INPUT_FIELD_ACCEPT_URL +"="+aliasGatewaySendData.getAcceptUrl()+passphraseShaIn 
				+ INPUT_FIELD_EXCEPTION_URL +"="+aliasGatewaySendData.getExceptionUrl()+passphraseShaIn 
				+ INPUT_FIELD_ORDER_ID +"="+aliasGatewaySendData.getOrderId()+passphraseShaIn 
				+ INPUT_FIELD_MERCHANT_ID +"="+aliasGatewaySendData.getPspId()+passphraseShaIn;
		
		return DigestUtils.sha512Hex(stringToEncode);
	}
}
