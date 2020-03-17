<div>

	<form id="paypalPaymentForm" method="post">
		<p>Paypal payment test form</p>
	
		Paypal configuration node name
		<input type="text" id="jcrPaypalConfigName" name="jcrPaypalConfigName"><br>
	
		Currency
		<select id="currency">
		  <option value="EUR">€</option>
		  <option value="USD">$</option>
		</select><br>
		
		
		
		Product unit cost
		<input type="text" id="productCost" name="productCost"><br>
		
		Units to buy
		<input type="text" id="productUnits" name="productUnits"><br>
		
		Product name
		<input type="text" id="productName" name="productName"><br>
		
		Product description
		<input type="text" id="productDescription" name="productDescription"><br>
		
		<div>
			<input type="submit" id="executePayment" value="Execute NVP/SOAP Payment"> 
		</div>		
		<br>
		<div>
			<input type="submit" id="executeRestPayment" value="Execute Rest Payment"> 
		</div>
								
	</form>

</div>

<script>
	$( document ).ready(function() {
	
		$('#executePayment').click(function(ev) {
			
			$('.cssload-loader').show();
				
			var protocol = window.location.protocol;
			var host = window.location.host;
			var ctxPath = '${ctx.contextPath}';
			var urlRootPath = protocol + '//' + host + ctxPath;
			var urlPaypalServlet = urlRootPath + '/.PaypalNativeServlet';
			
			var jcrPaypalConfigName = $("#jcrPaypalConfigName").val();
			var currency = $("#currency").val();
			var productCost = $("#productCost").val();
			var productUnits = $("#productUnits").val();
			var productName = $("#productName").val();
			var productDescription = $("#productDescription").val();
															
			var paymentData = {'currency' : currency, 'productCost' : productCost, 'productUnits' : productUnits,
							'productName' : productName, 'productDescription' : productDescription};
			
			$.post(urlPaypalServlet, 
		 		{paymentData: JSON.stringify(paymentData), jcrPaypalConfigName : jcrPaypalConfigName}
		 	)
			.done( function(data) {
		    	
		    	var expressCheckoutToken = data;
		    	
				if(expressCheckoutToken) {
				
					var paypalUrlToRedirectUser = 'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&useraction=commit&token=';
				
					window.location.href = paypalUrlToRedirectUser + expressCheckoutToken;
				}
				
			})
			.fail( function(xhr, textStatus, errorThrown) {
				
				var htmlResponseText =  xhr.responseText;
				var errorMsg = htmlResponseText.split("error#-#")[1].split("error#-#")[0];
				window.location.href = 'http://localhost:8080/magnolia-webapp/error?errorMessage=' + errorMsg;
			});
					
			ev.preventDefault();		     	     	     	
		     
		});
		
		$('#executeRestPayment').click(function(ev) {
			
			$('.cssload-loader').show();
				
			var protocol = window.location.protocol;
			var host = window.location.host;
			var ctxPath = '${ctx.contextPath}';
			var urlRootPath = protocol + '//' + host + ctxPath;
			var urlPaypalServlet = urlRootPath + '/.PaypalRestServlet';
			
			var currency = $("#currency").val();
			var productCost = $("#productCost").val();
			var productUnits = $("#productUnits").val();
			var productName = $("#productName").val();												
			var paymentData = {'currency' : currency, 'productCost' : productCost, 'productUnits' : productUnits, 'productName' : productName};							
			var paymentDataJSON = JSON.stringify(paymentData);
			
			$.post(urlPaypalServlet, {paymentData: paymentDataJSON})
			.done( function(url) {
				if(url) {
					window.location.href = url;
				}
			})
			.fail( function(xhr, textStatus, errorThrown) {				
				var htmlResponseText =  xhr.responseText;
				var errorMsg = htmlResponseText.split("error#-#")[1].split("error#-#")[0];	
				window.location.href = 'http://localhost:8080/magnolia-webapp/error?errorMessage=' + errorMsg;
			});
			
			ev.preventDefault();		     	     	     
		});
	
	});
</script>