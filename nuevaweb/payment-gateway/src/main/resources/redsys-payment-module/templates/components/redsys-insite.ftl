<div id="card-form" class="full-height"/>
<input type="hidden" id="token" ></input>
<script>
[#if content.Link?has_content] 
  [#assign myResource = cmsfn.contentByPath("${content.Link!}", "website")!] 
[#if myResource?has_content]
[#assign ml = cmsfn.link(myResource)!]
[/#if]
[/#if]
  window.addEventListener("message", function receiveMessage(event) {
      storeIdOper(event,"token");        
      setIdAndShowSummary(event.data.idOper, "${model.restUrl}","${ml}");      
  });  
</script>

  ${model.restUrl}
  ${ml}
[#if cmsfn.editMode]
	${i18n['insite.text.editmode']!"editmode"}
[#else]


	<script>
	[#-- i18n['products.browser.actionbar.sections.root.label']!"Continuar con el pago" --]
	  getInSiteForm('card-form',"border-color: black;border: dashed;","border-color: black;border: dashed;","border-color: black;border: dashed;","border-color: black;border: dashed;",'${i18n['checkout.buton']!""}','${model.merchant!}','${model.terminal!}','${model.order!}');
	</script>	
	<hr class="mb-4">
[/#if]