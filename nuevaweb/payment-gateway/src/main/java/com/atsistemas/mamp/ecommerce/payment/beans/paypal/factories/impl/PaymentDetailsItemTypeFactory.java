/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.beans.paypal.factories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atsistemas.mamp.ecommerce.payment.beans.paypal.factories.interfaces.IPaymentDetailsItemTypeFactory;

import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;

/**
 * 
 *
 */
public class PaymentDetailsItemTypeFactory implements IPaymentDetailsItemTypeFactory {

	Logger logger = LoggerFactory.getLogger(this.getClass().toString());

	/* (non-Javadoc)
	 * @see com.atsistemas.ecommerce.grupojulia.beans.paypal.factories.interfaces.IPaymentDetailsItemTypeFactory#create(com.atsistemas.ecommerce.grupojulia.bean.Product, urn.ebay.apis.eBLBaseComponents.CurrencyCodeType)
	 */
	public PaymentDetailsItemType create(String singlePrice, Integer quantity, String description, CurrencyCodeType currencyCodeType) {

		PaymentDetailsItemType ret = new PaymentDetailsItemType();
			
		BasicAmountType amountItem = new BasicAmountType();
		amountItem.setCurrencyID(currencyCodeType);
		amountItem.setValue(singlePrice);
		ret.setAmount(amountItem);
		ret.setQuantity(quantity);
		ret.setName(description);
		// ret.setDescription(product.getDescription()); Not setted due to the same information in "name" parameter
		
		return ret;
	}

}
