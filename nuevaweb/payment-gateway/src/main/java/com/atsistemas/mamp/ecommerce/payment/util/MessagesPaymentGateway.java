/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import info.magnolia.context.MgnlContext;

/**
 * 
 *
 */
public class MessagesPaymentGateway {

	private MessagesPaymentGateway() {
		super();
	}

	// property file is: package.app-nameOfApp-messages
	private static final String BUNDLE_NAME = "payment-gateway.i18n.module-payment-gateway-messages_en";

	
	
	public static String getString(String key, Object... params) {
		
		try {
			return MessageFormat.format(MessagesPaymentGateway.getString(key), params);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static String getString(String key) {
		
        ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, MgnlContext.getAggregationState().getLocale());
		String msg = MessageFormat.format(RESOURCE_BUNDLE.getString(key), null);
		
		return msg;
	}
	
}
