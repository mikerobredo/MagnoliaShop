/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.test.paypal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 */
public class PayPalNoApi {
	
	private Logger logger = LoggerFactory.getLogger(PayPalNoApi.class);
	
	private final String USER_AGENT = "Mozilla/5.0";

	// HTTP get request to set express checkout (1st phase)
	public void setExpressCheckout() throws Exception {
			
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("USER", "sistemas-facilitator_api1.julia.net"));
		urlParameters.add(new BasicNameValuePair("PWD", "EM6BW6CLZ5PPXGUZ"));
		urlParameters.add(new BasicNameValuePair("SIGNATURE", "A07WRWaZSKz4ZxFvSSKxA82l28R6AhHSClvmaa2T1BEpQF0hKXZ6c059"));
		urlParameters.add(new BasicNameValuePair("METHOD", "SetExpressCheckout"));
		urlParameters.add(new BasicNameValuePair("VERSION", "78"));
		urlParameters.add(new BasicNameValuePair("PAYMENTREQUEST_0_PAYMENTACTION", "SALE"));
		urlParameters.add(new BasicNameValuePair("PAYMENTREQUEST_0_AMT", "19"));
		urlParameters.add(new BasicNameValuePair("PAYMENTREQUEST_0_CURRENCYCODE", "EUR"));
		urlParameters.add(new BasicNameValuePair("cancelUrl", "http://www.example.com/cancel.html"));
		urlParameters.add(new BasicNameValuePair("returnUrl", "http://www.example.com/success.html"));
		
//		URI uri = new URIBuilder().setScheme("https")
//		        .setHost("api-3t.sandbox.paypal.com")
//		        .setPath("/nvp")
//		        .setParameters(urlParameters)
//		        .build();
		URI uri = new URIBuilder("https://api-3t.sandbox.paypal.com/nvp")
				.setParameters(urlParameters)
		        .build();
		
 
		HttpGet get = new HttpGet(uri);
		// add header
		get.setHeader("User-Agent", USER_AGENT);
		
		HttpClient client = HttpClientBuilder.create().build();
		 
		HttpResponse response = client.execute(get);
		System.out.println("\nSending 'GET' request to URL : " + uri);
		System.out.println("Response Code : " + 
                                    response.getStatusLine().getStatusCode());
		
		HashMap<String, String> resultParametersMap = this.getResponseParameters(response);
		String redirectURL = null;
		if (resultParametersMap.containsKey("ACK") && resultParametersMap.get("ACK").toString().equalsIgnoreCase("Success")) {
			redirectURL = "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=".concat(resultParametersMap.get("TOKEN").toString());
			redirectURL = redirectURL.replace("&token=", "&useraction=commit&token=");
		}
		System.out.println("URL to redirect to Paypal payment : \n" + redirectURL);
	}
	

	
	
	private HashMap<String, String> getResponseParameters(HttpResponse response) throws URISyntaxException, UnsupportedOperationException, IOException {
		
		StringBuffer sbToPrint = new StringBuffer("");
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

		HashMap<String, String> resultParametersMap = new HashMap<String, String>();
		String line = "";
		while ((line = rd.readLine()) != null) {
			StringTokenizer stk = new StringTokenizer(line,"&");
			while (stk.hasMoreElements()) {
				String s = (String) stk.nextElement();
				String[] arrarasd = s.split("=");
				sbToPrint.append(arrarasd[0] + " : " + arrarasd[1] + "\n");
				resultParametersMap.put(arrarasd[0], arrarasd[1]);
			}
		}
		
		System.out.println("Response parameters : \n" + sbToPrint.toString());
		  
		// Close BufferedReader and also InputStreamReader and Http Connection
		rd.close();
		
		return resultParametersMap;
	}
	
	
	//end class
	
}
