<div>

	<form id="paypalIngenicoPaymentForm" method="post">
		<p>Paypal payment test form (integrated with Ingenico)</p>
	
		Paypal/Ingenico configuration node name
		<input type="text" id="jcrIngenicoConfigName" name="jcrIngenicoConfigName"><br>
	
		Currency
		<select id="currencyPaypalIngenico">
		  <option value="EUR">€</option>
		  <option value="USD">$</option>
		</select><br>
		
		Product unit cost
		<input type="text" id="productCostPaypalIngenico" name="productCost"><br>
		
		Units to buy
		<input type="text" id="productUnitsPaypalIngenico" name="productUnits"><br>
		
		<div>
			<input type="submit" id="executePaymentPaypalIngenico" value="Execute payment"> 
		</div>		
		
	</form>

</div>

<script>

	$( document ).ready(function() {
	
		$('#executePaymentPaypalIngenico').click(function(ev) {
			
			$('.cssload-loader').show();
			
			var protocol = window.location.protocol;
			var host = window.location.host;
			var ctxPath = '${ctx.contextPath}';
			var urlRootPath = protocol + '//' + host + ctxPath;
			var urlPaypalIngenicoServlet = urlRootPath + '/.PaypalIngenicoServlet';
			
			var jcrIngenicoConfigName = $("#jcrIngenicoConfigName").val();
			var currency = $("#currencyPaypalIngenico").val();
			var productCost = $("#productCostPaypalIngenico").val();
			var productUnits = $("#productUnitsPaypalIngenico").val();
															
			var paymentData = {'currency' : currency, 'productCost' : productCost, 'productUnits' : productUnits};
							
			ev.preventDefault();
			
			generateIdentificationPaypalSendData(urlPaypalIngenicoServlet, jcrIngenicoConfigName, paymentData, processIdentificationPaypalSendData);
				    
		});
		
		function generateIdentificationPaypalSendData(urlPaypalIngenicoServlet, jcrIngenicoConfigName, paymentData, callback) {
		
			paymentData.orderId = Math.round(Math.random()*1000000);
			
			$.post(urlPaypalIngenicoServlet + '?phase=identificationRequest', 
	     		{paymentData: JSON.stringify(paymentData), site: jcrIngenicoConfigName}
	     	)
	 		.done( function(data) {
		    	
		    	callback(JSON.parse(data));
				
			})
			.fail( function(xhr, textStatus, errorThrown) {
				
				var htmlResponseText =  xhr.responseText;
				var errorMsg = htmlResponseText.split("error#-#")[1].split("error#-#")[0];
				window.location.href = 'http://localhost:8080/magnolia-webapp/error?errorMessage=' + errorMsg;
			});
		}
		
		function processIdentificationPaypalSendData(paymentPaypalSendData) {
		
			if(paymentPaypalSendData) {
				
				createFormIdentificationPaypal(paymentPaypalSendData, fillAndSendFormAliasGateway);
				
			}
		}
	
		function createFormIdentificationPaypal(paymentPaypalSendData, callback) {
		
			$form = $('<form method="post" action=' + paymentPaypalSendData.serviceEndpointPaypal + ' id="formAliasGatewayData" name="formAliasGatewayData"></form>');
			$form.append('<input type="hidden" name="PSPID">');
			$form.append('<input type="hidden" name="ORDERID">');
			$form.append('<input type="hidden" name="AMOUNT">');
			$form.append('<input type="hidden" name="CURRENCY">');
			$form.append('<input type="hidden" name="LANGUAGE">');
			$form.append('<input type="hidden" name="PM">');
			$form.append('<input type="hidden" name="TXTOKEN">');
			$form.append('<input type="hidden" name="ACCEPTURL">');
			$form.append('<input type="hidden" name="DECLINEURL">');
			$form.append('<input type="hidden" name="SHASIGN">');
			
			$('#main').append($form);
							
			callback(paymentPaypalSendData);
		}
	
		function fillAndSendFormAliasGateway(paymentPaypalSendData) {
		
			$('input[name="PSPID"]').val(paymentPaypalSendData.pspId);
			$('input[name="ORDERID"]').val(paymentPaypalSendData.orderId);
			$('input[name="AMOUNT"]').val(paymentPaypalSendData.amount);
			$('input[name="CURRENCY"]').val(paymentPaypalSendData.currency);
			$('input[name="LANGUAGE"]').val(paymentPaypalSendData.language);
			$('input[name="PM"]').val(paymentPaypalSendData.pm);
			$('input[name="TXTOKEN"]').val(paymentPaypalSendData.txtoken);
			$('input[name="ACCEPTURL"]').val(paymentPaypalSendData.acceptUrl);
			$('input[name="DECLINEURL"]').val(paymentPaypalSendData.declineUrl);
			$('input[name="SHASIGN"]').val(paymentPaypalSendData.shaSign);
			
			$( "#formAliasGatewayData" ).submit();
		}
	
	}
	);

</script>