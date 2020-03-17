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

    <div class="alert alert-info" role="alert">
        <h2>${i18n['checkout.testing']!"test mode"}</h2>
    </div>
    <hr class="mb-4">
    <section>
        [@cms.area name="main" /]
        <div class="py-5 text-center">
            <h1>${i18n['checkout.dothepay']!"pay"}</h1>
            <br> ${ctx.setAttribute("ordenInterna",20002,2)}
            <h5>${i18n['checkout.selectpayment']!"select payment"} ${ctx.getAttribute("ordenInterna")!}</h5>
             [#if content.Link?has_content] 
             	[#assign myResource = cmsfn.contentByPath("${content.Link!}", "website")!] 
             	[#if myResource?has_content]
            		<button type="button" class="btn btn-primary" onclick="window.location.href = '${cmsfn.link(myResource)}';">${i18n['checkout.buton']!"pay"}</button>
            	[/#if]
             [/#if]
        </div>
        <hr class="mb-4"> 
    </section>
</body>

</html>