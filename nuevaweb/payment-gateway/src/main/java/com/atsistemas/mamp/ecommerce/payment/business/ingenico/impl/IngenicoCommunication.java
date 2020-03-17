/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.business.ingenico.impl;

import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.ALIAS_GATEWAY_KEY_MESSAGE_ROOT_PATH_ERROR;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.CONTENT_TYPE_FORM_URL_ENCODED;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.CONTENT_TYPE_HEADER_ATTR;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.DIRECT_LINK_KEY_MESSAGE_ROOT_PATH_ERROR;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.ERROR_MESSAGE_SEPARATOR;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.ERROR_RESULT;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_ALIAS;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_AMOUNT;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_CURRENCY;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_MERCHANT_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_OPERATION;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_ORDER_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_PSWD;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_SHA_HASH;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.INPUT_FIELD_USER_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OK_RESULT;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_ALIAS_GATEWAY_ALIAS;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_ALIAS_GATEWAY_NCERROR;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_ALIAS_GATEWAY_NCERRORCARDNO;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_ALIAS_GATEWAY_NCERRORCN;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_ALIAS_GATEWAY_NCERRORCVC;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_ALIAS_GATEWAY_NCERRORED;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_DIRECT_LINK_NCERROR;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_DIRECT_LINK_NCERRORPLUS;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_DIRECT_LINK_PAYID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.OUTPUT_FIELD_NO_ERROR_VALUE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.SPACE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.maxSendDataRetries;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.originalError;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.InputSource;

import com.atsistemas.mamp.ecommerce.payment.beans.ingenico.DirectLinkSendData;
import com.atsistemas.mamp.ecommerce.payment.business.ingenico.interfaces.IIngenicoCommunication;
import com.atsistemas.mamp.ecommerce.payment.common.ConstantsIngenico;
import com.atsistemas.mamp.ecommerce.payment.util.MessagesPaymentGateway;



/**
 * 
 *
 */
public class IngenicoCommunication implements IIngenicoCommunication {

	private Logger log = LoggerFactory.getLogger(IngenicoCommunication.class);
	
	
	/* (non-Javadoc)
	 * @see com.atsistemas.mamp.ecommerce.payment.business.ingenico.interfaces.IIngenicoCommunication#processAliasGatewayResponse(javax.servlet.http.HttpServletRequest)
	 */
	public String processAliasGatewayResponse(HttpServletRequest response) throws ClientProtocolException, IOException, URISyntaxException {

		String ret = null;
		
		Map<String, String> mapResponseParameters = this.putResponseAliasGatewayParametersIntoMap(response);
		
		String manageErrorResult = this.manageAliasGatewayError(mapResponseParameters);
		if ((manageErrorResult.length() < ERROR_RESULT.length()) || ! ERROR_RESULT.equalsIgnoreCase(manageErrorResult.substring(0, ERROR_RESULT.length()))) {
			
			ret = this.getAliasGatewayPaymentAcceptedMessage(mapResponseParameters);
			
		}else {
			
			ret = manageErrorResult;
		}
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see com.atsistemas.mamp.ecommerce.payment.business.ingenico.interfaces.IIngenicoCommunication#manageDataPaymentToDirectLink(com.atsistemas.mamp.ecommerce.payment.beans.ingenico.DirectLinkSendData, java.lang.String)
	 */
	public String manageDataPaymentToDirectLink(DirectLinkSendData directLinkSendData, String shaIn) throws ClientProtocolException, IOException, URISyntaxException {

		String ret = null;
		
		HttpResponse response = this.sendDataPaymentToDirectLink(directLinkSendData, shaIn);
		
		ret = this.processDirectLinkResponse(response, directLinkSendData, shaIn);
		
		return ret;
	}
	
	
	
	

	/**
	 * @param response
	 * @return map containing parameters/values of alias gateway response
	 * @throws URISyntaxException
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, String> putResponseAliasGatewayParametersIntoMap(HttpServletRequest response) throws URISyntaxException {
		
		Map<String, String> ret = null;
		
		Map params = response.getParameterMap();
		if (params != null) {
			
			ret = new HashMap<String, String>();
			
			Iterator it = params.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry)it.next();
				ret.put((String) e.getKey(), ((String[])e.getValue())[0]);
			}
		}
		
		return ret;
	}

	/**
	 * In case of error detected, it is logged
	 * 
	 * @param mapResponseParameters
	 * @return generic string indicating if some error occurred or not
	 */
	private String manageAliasGatewayError(Map<String, String> mapResponseParameters) {

		String ret = null;
		
		StringBuffer sbRet = new StringBuffer(ERROR_RESULT);
		
		String keyMessageRootPath = ALIAS_GATEWAY_KEY_MESSAGE_ROOT_PATH_ERROR;
		
		String currentErrorActive = this.logPossibleError(keyMessageRootPath, OUTPUT_FIELD_ALIAS_GATEWAY_NCERRORCN, mapResponseParameters, this.log);
		sbRet = (currentErrorActive != null) ? sbRet.append(ERROR_MESSAGE_SEPARATOR).append(currentErrorActive) : sbRet;
		
		currentErrorActive = this.logPossibleError(keyMessageRootPath, OUTPUT_FIELD_ALIAS_GATEWAY_NCERRORCARDNO, mapResponseParameters, this.log);
		sbRet = (currentErrorActive != null) ? sbRet.append(ERROR_MESSAGE_SEPARATOR).append(currentErrorActive) : sbRet;
		
		currentErrorActive = this.logPossibleError(keyMessageRootPath, OUTPUT_FIELD_ALIAS_GATEWAY_NCERRORCVC, mapResponseParameters, this.log);
		sbRet = (currentErrorActive != null) ? sbRet.append(ERROR_MESSAGE_SEPARATOR).append(currentErrorActive) : sbRet;
		
		currentErrorActive = this.logPossibleError(keyMessageRootPath, OUTPUT_FIELD_ALIAS_GATEWAY_NCERRORED, mapResponseParameters, this.log);
		sbRet = (currentErrorActive != null) ? sbRet.append(ERROR_MESSAGE_SEPARATOR).append(currentErrorActive) : sbRet;
		
		currentErrorActive = this.logPossibleError(keyMessageRootPath, OUTPUT_FIELD_ALIAS_GATEWAY_NCERROR, mapResponseParameters, this.log);
		// General error only added if no other error occurred
		sbRet = (currentErrorActive != null && ERROR_RESULT.equals(sbRet.toString())) ? sbRet.append(ERROR_MESSAGE_SEPARATOR).append(currentErrorActive) : sbRet;
		
		
		if (ERROR_RESULT.equalsIgnoreCase(sbRet.toString())) {
			ret = OK_RESULT;
		}else {
			ret = sbRet.toString();
		}
		
		return ret;
	}

	private String getAliasGatewayPaymentAcceptedMessage(Map<String, String> mapResponseParameters) {
		
		return mapResponseParameters.get(OUTPUT_FIELD_ALIAS_GATEWAY_ALIAS);
	}
	
	/**
	 * @param messageRootPathError
	 * @param outputFieldError
	 * @param mapResponseParameters
	 * @param log
	 * @return logged error if some error occurred
	 */
	private String logPossibleError(String messageRootPathError, String outputFieldError, Map<String, String> mapResponseParameters, Logger log) {
		
		String ret = null;
		
		String currentErrorMessage = null;
		
		String errorValue = mapResponseParameters.get(outputFieldError);
		if (! OUTPUT_FIELD_NO_ERROR_VALUE.equalsIgnoreCase(errorValue)) {
			
			currentErrorMessage = "Error: " + errorValue;
			
			log.error(currentErrorMessage);
			ret = currentErrorMessage;
		}
		
		return ret;
	}
	
	private HttpResponse sendDataPaymentToDirectLink(DirectLinkSendData directLinkSendData, String shaIn) throws ClientProtocolException, IOException {

		HttpResponse ret = null;
		
		HttpClient client = HttpClientBuilder.create().build();
		 
		 
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair(INPUT_FIELD_MERCHANT_ID, directLinkSendData.getPspId()));
		urlParameters.add(new BasicNameValuePair(INPUT_FIELD_ORDER_ID, directLinkSendData.getOrderId()));
		urlParameters.add(new BasicNameValuePair(INPUT_FIELD_USER_ID, directLinkSendData.getUserId()));
		urlParameters.add(new BasicNameValuePair(INPUT_FIELD_PSWD, directLinkSendData.getPswd()));
		urlParameters.add(new BasicNameValuePair(INPUT_FIELD_ALIAS, directLinkSendData.getAlias()));
		urlParameters.add(new BasicNameValuePair(INPUT_FIELD_AMOUNT, directLinkSendData.getAmount()));
		urlParameters.add(new BasicNameValuePair(INPUT_FIELD_CURRENCY, directLinkSendData.getCurrency()));
		urlParameters.add(new BasicNameValuePair(INPUT_FIELD_OPERATION, directLinkSendData.getOperation()));
		urlParameters.add(new BasicNameValuePair(INPUT_FIELD_SHA_HASH, this.calculateDirectLinkShaHash(directLinkSendData, shaIn)));

		HttpPost post = new HttpPost(ConstantsIngenico.getENDPOINT_DIRECT_LINK());

		// add header
		post.setHeader(CONTENT_TYPE_HEADER_ATTR, CONTENT_TYPE_FORM_URL_ENCODED);
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
 
		ret = client.execute(post);
		
		return ret;
	}
	
	/**
	 * All sent parameters (which are included in SHA parameters list) must be included in SHA generation.
	 * Moreover SHA parameters must be uppercase and in alphabetic order
	 * 
	 * @param directLinkSendData
	 * @param passphraseShaIn
	 * @return hash calculated
	 */
	private String calculateDirectLinkShaHash(DirectLinkSendData directLinkSendData, String passphraseShaIn) {
		
		String stringToEncode = INPUT_FIELD_ALIAS+"="+directLinkSendData.getAlias()+passphraseShaIn 
				+ INPUT_FIELD_AMOUNT+"="+directLinkSendData.getAmount()+passphraseShaIn 
				+ INPUT_FIELD_CURRENCY+"="+directLinkSendData.getCurrency()+passphraseShaIn 
				+ INPUT_FIELD_OPERATION+"="+directLinkSendData.getOperation()+passphraseShaIn 
				+ INPUT_FIELD_ORDER_ID+"="+directLinkSendData.getOrderId()+passphraseShaIn 
				+ INPUT_FIELD_MERCHANT_ID+"="+directLinkSendData.getPspId()+passphraseShaIn 
				+ INPUT_FIELD_PSWD+"="+directLinkSendData.getPswd()+passphraseShaIn 
				+ INPUT_FIELD_USER_ID+"="+directLinkSendData.getUserId()+passphraseShaIn;
		
		return DigestUtils.sha512Hex(stringToEncode);
	}
	
	/**
	 * @param response
	 * @param directLinkSendData
	 * @param shaIn
	 * @return "payid" parameter in case of success, "error" type message otherwise
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private String processDirectLinkResponse(HttpResponse response, DirectLinkSendData directLinkSendData, String shaIn) throws ClientProtocolException, IOException, URISyntaxException {

		String ret = null;
		
		int actualRetries = 0;
		boolean responseProcessed = false;
		
		Map<String, String> mapResponseParameters = this.putResponseDirectLinkParametersIntoMap(response);
		
		while ((! responseProcessed) && (actualRetries < maxSendDataRetries)) {
			
			if (this.isConnectionFailed(response)) {
				
				actualRetries++;
				response = this.sendDataPaymentToDirectLink(directLinkSendData, shaIn);
			
			}else if (this.isConnectionOk(response)) {
				
				String manageErrorResult = this.manageDirectLinkError(mapResponseParameters);
				if ((manageErrorResult.length() < ERROR_RESULT.length()) || ! ERROR_RESULT.equalsIgnoreCase(manageErrorResult.substring(0, ERROR_RESULT.length()))) {
					
					ret = this.getDirectLinkPaymentAcceptedMessage(mapResponseParameters);
					
				}else {
					
					ret = manageErrorResult;
				}
				
				responseProcessed = true;
				
			}else {
				
				log.error("Payment response not identified");
				responseProcessed = true;
			}
				
		}
		
		if ((! responseProcessed) && (this.isConnectionFailed(response))) {
			ret = "Connection failed";
		}
		
		return ret;
	}
	
	/**
	 * @param response
	 * @return map containing parameters/values of directLink response
	 * @throws URISyntaxException
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 */
	private Map<String, String> putResponseDirectLinkParametersIntoMap(HttpResponse response) throws URISyntaxException, UnsupportedOperationException, IOException {

		Map<String, String> ret = null;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			
			DocumentBuilder builder = factory.newDocumentBuilder();

			HttpEntity responseEntity = response.getEntity();
			String xmlResponse = EntityUtils.toString(responseEntity).trim();
			InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(xmlResponse));
			Document doc = builder.parse(is);

			Element element = doc.getDocumentElement();

			NamedNodeMap nodes = element.getAttributes();
			if (nodes.getLength() > 0) {
				
				ret = new HashMap<String, String>();
				
				for (int i = 0; i < nodes.getLength(); i++) {
					ret.put(nodes.item(i).getNodeName(), nodes.item(i).getTextContent());
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return ret;
	}
	
	private boolean isConnectionFailed(HttpResponse response) {
		
		int statusCode = response.getStatusLine().getStatusCode();
		
		return (statusCode == HttpStatus.SC_GATEWAY_TIMEOUT) || (statusCode == HttpStatus.SC_REQUEST_TIMEOUT);
	}
	
	private boolean isConnectionOk(HttpResponse response) {

		int statusCode = response.getStatusLine().getStatusCode();
		
		return (statusCode == HttpStatus.SC_OK) || (statusCode == HttpStatus.SC_MOVED_TEMPORARILY);
	}
	
	/**
	 * In case of error detected, it is logged
	 * 
	 * @param mapResponseParameters
	 * @return generic string indicating if some error occurred or not
	 */
	private String manageDirectLinkError(Map<String, String> mapResponseParameters) {

		String ret = null;
		
		StringBuffer sbRet = new StringBuffer(ERROR_RESULT);
		
		String keyMessageRootPath = DIRECT_LINK_KEY_MESSAGE_ROOT_PATH_ERROR;
		
		String currentErrorActive = this.logPossibleError(keyMessageRootPath, OUTPUT_FIELD_DIRECT_LINK_NCERROR, mapResponseParameters, this.log);
		sbRet = (currentErrorActive != null) ? sbRet.append(ERROR_MESSAGE_SEPARATOR).append(currentErrorActive) : sbRet;
		
		// Original error message
		String errorValue = mapResponseParameters.get(OUTPUT_FIELD_DIRECT_LINK_NCERROR);
		if (! OUTPUT_FIELD_NO_ERROR_VALUE.equalsIgnoreCase(errorValue)) {
			
			// In this case error message is received directly from payment response
			String errorMessage = MessagesPaymentGateway.getString(originalError) + SPACE + mapResponseParameters.get(OUTPUT_FIELD_DIRECT_LINK_NCERRORPLUS);
			log.error(errorMessage);
			sbRet.append(ERROR_MESSAGE_SEPARATOR + errorMessage);
		}
		
		if (ERROR_RESULT.equalsIgnoreCase(sbRet.toString())) {
			ret = OK_RESULT;
		}else {
			ret = sbRet.toString();
		}
		
		return ret;
	}
	
	private String getDirectLinkPaymentAcceptedMessage(Map<String, String> mapResponseParameters) {
		
		return mapResponseParameters.get(OUTPUT_FIELD_DIRECT_LINK_PAYID);
	}

}
