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

    <script src="https://sis-i.redsys.es:25443/sis/NC/inte/redsys2.js"></script>    
</head>

<body onload="loadRedsysForm()">
	<div class="center-block">
    	<div class="alert alert-success center-block" role="alert" width="100px">
${i18n['redsys.page.advert']!""}    	</div>
    		<hr class="mb-4">
   
    		
    </div>
    <section>
        [@cms.area name="main" /]
    </section>

</body>

</html>