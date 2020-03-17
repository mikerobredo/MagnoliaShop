<div>

	<form id="ingenicoPaymentForm" method="post">
		<p>Ingenico payment test form</p>
	
		Ingenico configuration node name
		<input type="text" id="jcrIngenicoConfigName" name="jcrIngenicoConfigName"><br>
	
		Currency
		<select id="currencyIngenico">
		  <option value="EUR">€</option>
		  <option value="USD">$</option>
		</select><br>
		
		Product unit cost
		<input type="text" id="productCostIngenico" name="productCost"><br>
		
		Units to buy
		<input type="text" id="productUnitsIngenico" name="productUnits"><br>
		
		Product name
		<input type="text" id="productNameIngenico" name="productName"><br>
		
		Product description
		<input type="text" id="productDescriptionIngenico" name="productDescription"><br>
		
		<h2>Card data</h2>
		
		Owner name
		<input type="text" id="nameIngenico" name="nameIngenico"><br>
		
		Card number
		<input type="text" id="num1Ingenico" name="num1Ingenico">
		
		<input type="text" id="num2Ingenico" name="num2Ingenico">
		
		<input type="text" id="num3Ingenico" name="num3Ingenico">
		
		<input type="text" id="num4Ingenico" name="num4Ingenico"><br>
		
		Expire month
		<input type="text" id="expirationMonthIngenico" name="expirationMonthIngenico"><br>
		
		Expire year
		<input type="text" id="expirationYearIngenico" name="expirationYearIngenico"><br>
		
		CVC
		<input type="text" id="cvcIngenico" name="cvcIngenico"><br>
		
		<div>
			<input type="submit" id="executePaymentIngenico" value="Execute payment"> 
		</div>		
		
	</form>

</div>

<script>
	$( document ).ready(function() {
	
		$('#executePaymentIngenico').click(function(ev) {
			
			$('.cssload-loader').show();
				
			var protocol = window.location.protocol;
			var host = window.location.host;
			var ctxPath = '${ctx.contextPath}';
			var urlRootPath = protocol + '//' + host + ctxPath;
			var urlIngenicoServlet = urlRootPath + '/.IngenicoServlet';
			
			var jcrIngenicoConfigName = $("#jcrIngenicoConfigName").val();
			var currency = $("#currencyIngenico").val();
			var productCost = $("#productCostIngenico").val();
			var productUnits = $("#productUnitsIngenico").val();
			var productName = $("#productNameIngenico").val();
			var productDescription = $("#productDescriptionIngenico").val();
															
			var paymentData = {'currency' : currency, 'productCost' : productCost, 'productUnits' : productUnits,
							'productName' : productName, 'productDescription' : productDescription};
			
			var name = $("#nameIngenico").val();
			var num1 = $("#num1Ingenico").val();
			var num2 = $("#num2Ingenico").val();
			var num3 = $("#num3Ingenico").val();
			var num4 = $("#num4Ingenico").val();
			var cardNo = num1 + num2 + num3 + num4;
			var expirationMonth = $("#expirationMonthIngenico").val();
			var expirationYear = $("#expirationYearIngenico").val(); 
			var cvc = $("#cvcIngenico").val();
			
			var sensitivePaymentData = {'name': name, 'cardNo': cardNo,'expirationMonth': expirationMonth, 
							'expirationYear': expirationYear, 'cvc' : cvc};
					
			ev.preventDefault();
							
			generateAliasGatewaySendData(urlIngenicoServlet, jcrIngenicoConfigName, sensitivePaymentData, paymentData, processAliasGatewaySendData);
		     
		});
		
		function generateAliasGatewaySendData(urlIngenicoServlet, jcrIngenicoConfigName, sensitivePaymentData, paymentData, callback) {
		
			var aliasGatewaySendData = {};
			
			aliasGatewaySendData.orderId = Math.round(Math.random()*1000000);
			paymentData.orderId = aliasGatewaySendData.orderId; 
			
			$.post(urlIngenicoServlet, 
	     		{aliasGatewaySendData: JSON.stringify(aliasGatewaySendData), paymentData: JSON.stringify(paymentData), site: jcrIngenicoConfigName}
	     	)
	 		.done( function(data) {
		    	
		    	callback(JSON.parse(data), sensitivePaymentData);
				
			})
			.fail( function(xhr, textStatus, errorThrown) {
				
				var htmlResponseText =  xhr.responseText;
				var errorMsg = htmlResponseText.split("error#-#")[1].split("error#-#")[0];
				window.location.href = 'http://localhost:8080/magnolia-webapp/error?errorMessage=' + errorMsg;
			});
		}
		
		function processAliasGatewaySendData(aliasGatewaySendData, sensitivePaymentData) {
		
			if(aliasGatewaySendData) {
				
				createFormAliasGateway(aliasGatewaySendData, sensitivePaymentData, fillAndSendFormAliasGateway);
				
			}
		}
	
		function createFormAliasGateway(aliasGatewaySendData, sensitivePaymentData, callback) {
		
			$form = $('<form method="post" action=' + aliasGatewaySendData.serviceEndpointAliasGateway + ' id="formAliasGatewayData" name="formAliasGatewayData"></form>');
			$form.append('<input type="hidden" name="ACCEPTURL">');
			$form.append('<input type="hidden" name="CARDNO">');
			$form.append('<input type="hidden" name="CN">');
			$form.append('<input type="hidden" name="CVC">');
			$form.append('<input type="hidden" name="ED">');
			$form.append('<input type="hidden" name="EXCEPTIONURL">');
			$form.append('<input type="hidden" name="ORDERID">');
			$form.append('<input type="hidden" name="PSPID">');
			$form.append('<input type="hidden" name="SHASIGN">');
			
			$('#main').append($form);
							
			callback(aliasGatewaySendData, sensitivePaymentData);
		}
	
		function fillAndSendFormAliasGateway(aliasGatewaySendData, sensitivePaymentData) {
		
			$('input[name="ACCEPTURL"]').val(aliasGatewaySendData.acceptUrl);
			$('input[name="CARDNO"]').val(sensitivePaymentData.cardNo);
			$('input[name="CN"]').val(sensitivePaymentData.name);
			$('input[name="CVC"]').val(sensitivePaymentData.cvc);
			$('input[name="ED"]').val(sensitivePaymentData.expirationMonth + sensitivePaymentData.expirationYear.substring(2));
			$('input[name="EXCEPTIONURL"]').val(aliasGatewaySendData.exceptionUrl);
			$('input[name="ORDERID"]').val(aliasGatewaySendData.orderId);
			$('input[name="PSPID"]').val(aliasGatewaySendData.pspId);
			$('input[name="SHASIGN"]').val(aliasGatewaySendData.shaSign);
			
			$( "#formAliasGatewayData" ).submit();
		}
	
	});
</script>