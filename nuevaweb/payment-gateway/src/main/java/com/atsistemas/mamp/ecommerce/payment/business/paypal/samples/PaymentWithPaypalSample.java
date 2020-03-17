package com.atsistemas.mamp.ecommerce.payment.business.paypal.samples;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

/**
 * Ejemplo de clase que invoca la creación de un pago a traves de la Api Rest de
 * Paypal
 * 
 * @author malupion
 */
public class PaymentWithPaypalSample {

	/**
	 * Datos de prueba suministrados por Paypal para realizar pruebas en Test
	 */
	public static final String clientId = "AYSq3RDGsmBLJE-otTkBtM-jBRd1TCQwFf9RGfwddNXWz0uFU9ztymylOhRS";
	public static final String clientSecret = "EGnHDxD_qRPdaLdZz8iCr8N7_MzF-YHPTkjs6NKYQvQSBngp4PTTVWkPZRbL";
	public static final String mode = "sandbox";

	/**
	 * Método que realiza la creación de un pago haciendo uso de las clases
	 * definidas en la Api Rest de Paypal
	 */
	public void createPaymentPaypal() {

		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal("1.00");

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		RedirectUrls redirectUrls = new RedirectUrls();
		String url = "http://localhost:8080/magnolia-webapp/.PaypalRestServlet";
		redirectUrls.setCancelUrl(url);
		redirectUrls.setReturnUrl(url);
		payment.setRedirectUrls(redirectUrls);

		try {
			APIContext apiContext = new APIContext(clientId, clientSecret, mode);
			Payment createdPayment = payment.create(apiContext);

			Iterator<Links> links = createdPayment.getLinks().iterator();
			while (links.hasNext()) {
				Links link = links.next();
				if (link.getRel().equalsIgnoreCase("approval_url")) {
					// REDIRECT USER TO link.getHref()
					System.out.println("REDIRECT USER TO " + link.getHref());
				}
			}

			System.out.println("Created payment with id = " + createdPayment.getId() + " and status = " + createdPayment.getState());

		} catch (PayPalRESTException e) {
			System.err.println("Payment with PayPal error. Request: " + Payment.getLastRequest() + " Error: " + e.getMessage());
			System.err.println(e.getDetails());
		}
	}
}
