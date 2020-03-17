<!DOCTYPE html>
<html xml:lang="en" lang="en" class="no-js">

<head>
    [@cms.page /]
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>${content.title!content.@name}</title>
   [#assign site = sitefn.site()!] 
[#assign theme = sitefn.theme(site)!]
[#list theme.cssFiles as cssFile]
    <link rel="stylesheet" href="${cssFile.link}" media="${cssFile.media}" />
[/#list]
 
[#list theme.jsFiles as jsFile]
    <script src="${jsFile.link}"></script>
[/#list]
</head>

<body>
    <section>
        [@cms.area name="main" /]
   
        <div class="py-5 text-center">
        
			[#if content.Link?has_content] 
			[#assign myResource = cmsfn.contentByPath("${content.Link!}", "website")!] 
			[#if myResource?has_content]
			[#assign ml = cmsfn.link(myResource)!]
			[#assign mlr ="http://localhost:8080"+ ctx.contextPath+"${model.proceedUrlRest}"!]
			
			[/#if]
			[/#if]

            <script>
                $(document).ready(function() {
                    $("#vbutton").click(function() {
                    	
                        sendRest( "${model.proceedUrlRest}","${ml}");                                
                    });
                });
            </script>
            <h1>${i18n['summary.paysummary']!""}</h1>
            <br>
            <h5>${i18n['summary.articlessummary']!""}</h5>
            <h5>${i18n['summary.ordernumber']!""} ${ctx.getAttribute("ordenInterna")!}</h5>

            <div class="container">
                <table class="table">
                    <thead class="thead-dark">
                        <tr>
                            <th>${i18n['summary.article']!""}</th>
                            <th>${i18n['summary.cuantity']!""}</th>
                            <th>${i18n['summary.price']!""}</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Disco duro</td>
                            <td>1</td>
                            <td>50.50</td>
                        </tr>
                        <tr>
                            <td>Memoria ram 16Gb </td>
                            <td>2</td>
                            <td>80</td>
                        </tr>
                        <tr>
                            <td>Cable minijack</td>
                            <td>4</td>
                            <td>2.50</td>
                        </tr>
                    </tbody>
                </table>
            </div>
                     
            [#if ctx.getAttribute("proceed")?has_content]
	            <div class="alert alert-danger alert-dismissible" role="alert">
	                resultado de la operacion : ${model.check!}
	            </div>
            [#else]
	            <hr class="mb-4">
	            <button id="vbutton">${i18n['checkout.buton']!""}</button>
            [/#if]

    </section>
</body>

</html>