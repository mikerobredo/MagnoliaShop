// #Create Payment Using PayPal Sample
// This sample code demonstrates how you can process a 
// PayPal Account based Payment.
// API used: /v1/payments/payment
package com.atsistemas.mamp.ecommerce.payment.business.paypal.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.atsistemas.mamp.ecommerce.payment.beans.PaypalPaymentData;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import info.magnolia.context.MgnlContext;

/**
 * Servlet definido en la configuración de magnolia para comunicarse con Paypal
 * a través de su Api Rest
 * 
 * @author malupion
 */
public class PaypalRestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Datos de prueba suministrados por Paypal para realizar pruebas en Test
	 */
	public static final String clientID = "AYSq3RDGsmBLJE-otTkBtM-jBRd1TCQwFf9RGfwddNXWz0uFU9ztymylOhRS";
	public static final String clientSecret = "EGnHDxD_qRPdaLdZz8iCr8N7_MzF-YHPTkjs6NKYQvQSBngp4PTTVWkPZRbL";
	public static final String mode = "sandbox";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sJsonPaymentData = MgnlContext.getAttribute("paymentData") != null ? MgnlContext.getAttribute("paymentData").toString() : null;

		// CREATE PAYMENT
		if (sJsonPaymentData != null) {
			createPayment(req, resp, sJsonPaymentData);

		// EXECUTE PAYMENT
		} else if (req.getParameter("PayerID") != null && req.getParameter("paymentId") != null) {
			String url = executePayment(req, resp);
			resp.sendRedirect(url);

		// CANCEL PAYMENT
		} else {
			resp.sendRedirect("http://www.example.com/cancel.html");
		}
	}

	/**
	 * Metodo que invoca la confirmación de un pago en paypal a través de su Api Rest
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String executePayment(HttpServletRequest req, HttpServletResponse resp) {
		APIContext apiContext = new APIContext(clientID, clientSecret, mode);

		Payment createdPayment = null;
		String url_redirect = null;

		Payment payment = new Payment();
		payment.setId(req.getParameter("paymentId"));

		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(req.getParameter("PayerID"));

		try {
			createdPayment = payment.execute(apiContext, paymentExecution);
			System.out.println(createdPayment);
			url_redirect = "http://www.example.com/success.html";

		} catch (PayPalRESTException e) {
			System.err.println(e.getDetails());
			url_redirect = "http://www.example.com/error.html";
		}

		return url_redirect;
	}

	/**
	 * Método que invoca la creación de un pago en Paypal a través de su Api Rest
	 * 
	 * @param req
	 * @param resp
	 * @param sJsonPaymentData
	 */
	public void createPayment(HttpServletRequest req, HttpServletResponse resp, String sJsonPaymentData) {
		try {
			if (sJsonPaymentData != null) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node;

				node = mapper.readTree(sJsonPaymentData);
				PaypalPaymentData paypalPaymentData = mapper.readValue(node, PaypalPaymentData.class);

				Amount amount = new Amount();
				amount.setCurrency(paypalPaymentData.getCurrency());

				BigDecimal productCost = new BigDecimal(paypalPaymentData.getProductCost());
				BigDecimal productUnits = new BigDecimal(paypalPaymentData.getProductUnits());
				BigDecimal big = productCost.multiply(productUnits).setScale(2, RoundingMode.HALF_UP);
				String total = String.valueOf(big);
				amount.setTotal(total);

				Transaction transaction = new Transaction();
				transaction.setAmount(amount);
				transaction.setDescription("This is the payment transaction description.");

				// ### Items
				Item item = new Item();
				item.setName(paypalPaymentData.getProductName()).setQuantity(paypalPaymentData.getProductUnits()).setCurrency(paypalPaymentData.getCurrency())
						.setPrice(paypalPaymentData.getProductCost());
				ItemList itemList = new ItemList();
				List<Item> items = new ArrayList<Item>();
				items.add(item);
				itemList.setItems(items);
				transaction.setItemList(itemList);

				List<Transaction> transactions = new ArrayList<Transaction>();
				transactions.add(transaction);

				Payer payer = new Payer();
				payer.setPaymentMethod("paypal");

				Payment payment = new Payment();
				payment.setIntent("sale");
				payment.setPayer(payer);
				payment.setTransactions(transactions);

				RedirectUrls redirectUrls = new RedirectUrls();
				redirectUrls.setCancelUrl(MgnlContext.getWebContext().getAggregationState().getOriginalURL());
				redirectUrls.setReturnUrl(MgnlContext.getWebContext().getAggregationState().getOriginalURL());
				payment.setRedirectUrls(redirectUrls);

				try {
					APIContext apiContext = new APIContext(clientID, clientSecret, mode);
					Payment createdPayment = payment.create(apiContext);

					Iterator<Links> links = createdPayment.getLinks().iterator();
					while (links.hasNext()) {
						Links link = links.next();
						if (link.getRel().equalsIgnoreCase("approval_url")) {
							PrintWriter responseWriter = resp.getWriter();
							responseWriter.println(link.getHref());
							responseWriter.flush();
							responseWriter.close();
						}
					}

					System.out.println("Created payment with id = " + createdPayment.getId() + " and status = " + createdPayment.getState());

				} catch (PayPalRESTException e) {
					System.err.println("Payment with PayPal error. Request: " + Payment.getLastRequest() + " Error: " + e.getMessage());
					System.err.println(e.getDetails());
				}
			}

		} catch (JsonParseException e1) {
			e1.printStackTrace();

		} catch (JsonMappingException e1) {
			e1.printStackTrace();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
