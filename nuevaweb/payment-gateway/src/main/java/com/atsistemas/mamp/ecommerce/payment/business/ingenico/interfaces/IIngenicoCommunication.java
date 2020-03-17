/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.business.ingenico.interfaces;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;

import com.atsistemas.mamp.ecommerce.payment.beans.ingenico.DirectLinkSendData;

/**
 * 
 *
 */
public interface IIngenicoCommunication {

	/**
	 * @param response
	 * @return alias (token) in case of success or an "error" type message otherwise
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	String processAliasGatewayResponse(HttpServletRequest response) throws ClientProtocolException, IOException, URISyntaxException;
	
	/**
	 * Sends data, receives the response, manages it and returns it 
	 * 
	 * @param directLinkSendData
	 * @param shaIn
	 * @return directlink call result
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws URISyntaxException 
	 */
	String manageDataPaymentToDirectLink(DirectLinkSendData directLinkSendData, String shaIn) throws ClientProtocolException, IOException, URISyntaxException;
	
}
