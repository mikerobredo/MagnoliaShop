<script>
	$( document ).ready(function() {
		[#assign errorMessageFromServer = ctx.getRequest().getAttribute("javax.servlet.error.message")!]
		[#if errorMessageFromServer?has_content]
		
			var errorMsg = '${errorMessageFromServer}'.split("error#-#")[1].split("error#-#")[0];
			$('#errorMessage').text(errorMsg);
		
		[#else]
		
			[#assign errorMessage = ctx.getRequest().getParameter("errorMessage")!'Error no reconocido']
			$('#errorMessage').text('${errorMessage}');
			
		[/#if]
	});
</script>
<div>
	<h1 id='errorMessage'></h1>
</div>