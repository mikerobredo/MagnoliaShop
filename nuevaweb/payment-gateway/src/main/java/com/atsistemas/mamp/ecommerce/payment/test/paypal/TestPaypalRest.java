/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.test.paypal;

import com.atsistemas.mamp.ecommerce.payment.business.paypal.samples.PaymentWithPaypalSample;

/**
 * Test para verificar funcionamiento correcto de una compra en Paypal a traves
 * de la API REST
 *
 * @author malupion
 */
public class TestPaypalRest {

	public static void main(String[] args) throws Exception {
		TestPaypalRest testPaypal = new TestPaypalRest();
		testPaypal.executeCreatePaymentTest();
	}

	/**
	 * Método que invoca la creación de un pago a traves de la Api Rest de Paypal
	 */
	private void executeCreatePaymentTest() {
		PaymentWithPaypalSample sampleTest = new PaymentWithPaypalSample();
		sampleTest.createPaymentPaypal();
		System.out.println("Test finished");
	}
}
