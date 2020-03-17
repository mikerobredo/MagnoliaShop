/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.test.ingenico;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 */
public class TestIngenico {

	private Logger log = LoggerFactory.getLogger(TestIngenico.class);
	
	private final String USER_AGENT = "Mozilla/5.0";
	/**
     * The default HTML form content type.
     */
    private final String ALIAS_GATEWAY_RESPONSE_CONTENT_TYPE = "text/html";
    private final String LOCATION_HEADER_RESPONSE_PARAM = "Location";
    
    
    
    
	 
	public static void main(String[] args) throws Exception {
		
		TestIngenico testIngenico = new TestIngenico();
		
		testIngenico.executeDefaultTest();
	}
	
	
	
	// HTTP POST request
	private void executeDefaultTest() throws Exception {
 
		String url = "https://ogone.test.v-psp.com/ncol/test/alias_gateway_utf8.asp";
 
		HttpClient client = HttpClientBuilder.create().build();
 
 
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("ACCEPTURL", this.getAcceptUrl()));
		urlParameters.add(new BasicNameValuePair("CARDNO", this.getCardNo()));
		urlParameters.add(new BasicNameValuePair("CN", this.getCardHolderCompleteName()));
		urlParameters.add(new BasicNameValuePair("CVC", this.getCardVerificationCode()));
		urlParameters.add(new BasicNameValuePair("ED", this.getExpirationDate()));
		urlParameters.add(new BasicNameValuePair("EXCEPTIONURL", this.getExceptionUrl()));
		urlParameters.add(new BasicNameValuePair("ORDERID", this.getOrderId()));
		urlParameters.add(new BasicNameValuePair("PSPID", this.getMerchantIdentification()));
		urlParameters.add(new BasicNameValuePair("SHASIGN", this.calculateShaHash()));

		HttpPost post = new HttpPost(url);
		// add header
		post.setHeader("User-Agent", USER_AGENT);
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
 
		HttpResponse response = client.execute(post);
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " + 
                                    response.getStatusLine().getStatusCode());
		System.out.println("Response Location : \n" + this.getResponseLocation(response));
 
		BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
 
		System.out.println(result.toString());
 
		// Close BufferedReader and also InputStreamReader and Http Connection
		rd.close();
	}


	private String getResponseLocation(HttpResponse response) throws URISyntaxException {
		
		StringBuffer ret = new StringBuffer("");
		
		String url = response.getLastHeader(this.LOCATION_HEADER_RESPONSE_PARAM).getValue();
		List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), this.getCharset(response));

		for (NameValuePair param : params) {
		  ret.append(param.getName() + " : " + param.getValue() + "\n");
		}
		
		return ret.toString();
	}
	
	private String getCharset(HttpResponse response) {

		String ret = null;
		
		final ContentType contentType = ContentType.get(response.getEntity());
        if (!(contentType == null) && (contentType.getMimeType().equalsIgnoreCase(ALIAS_GATEWAY_RESPONSE_CONTENT_TYPE))) {
        	
        	final Charset charset = contentType.getCharset() != null ? contentType.getCharset() : HTTP.DEF_CONTENT_CHARSET;
        	ret = charset.displayName();
        }else {
			log.error("Content type null or not recognised in Alias Gateway response");
		}
		
		return ret;
	}


	private String getPassphraseShaIn() {
		
		return "est@eslafraseparaencript4r";
	}

	private String calculateShaHash() {
		
		String stringToEncode = "ACCEPTURL="+this.getAcceptUrl()+this.getPassphraseShaIn() 
				+ "EXCEPTIONURL="+this.getExceptionUrl()+this.getPassphraseShaIn() 
				+ "ORDERID="+this.getOrderId()+this.getPassphraseShaIn() 
				+ "PSPID="+this.getMerchantIdentification()+this.getPassphraseShaIn();
		
		return DigestUtils.sha512Hex(stringToEncode);
	}


	private String getMerchantIdentification() {
		
		return "atsistemastest";
	}

	private String getOrderId() {
		
		return "ABREF137";
	}


	private String getExceptionUrl() {
		
		return "http://www.example.com/error.html";
	}


	private String getCardVerificationCode() {
		
		return "111";
	}


	private String getExpirationDate() {
		
		return "1219";
	}


	private String getCardHolderCompleteName() {
		
		return this.getCardHolderName() + " " + this.getCardHolderSurname();
	}


	private String getCardHolderName() {
		
		return "MANUEL";
	}


	private String getCardHolderSurname() {
		
		return "SALVATIERRA NAVARRO";
	}


	private String getCardNo() {
		
		return "4111111111111111";
	}


	private String getAcceptUrl() {
		
		return "http://www.example.com/success.html";
	}

}
