/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.beans.paypal.factories.interfaces;

import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;


/**
 * 
 *
 */
public interface IPaymentDetailsItemTypeFactory {

	PaymentDetailsItemType create(String singlePrice, Integer quantity, String description, CurrencyCodeType currencyCodeType);
	
}
