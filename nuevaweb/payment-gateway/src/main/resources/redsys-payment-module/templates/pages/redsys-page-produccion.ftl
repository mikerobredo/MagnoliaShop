<!DOCTYPE html>
<html xml:lang="en" lang="en" class="no-js">

<head>
    [@cms.page /]
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />

    <title>${content.title!content.@name}</title>

    ${resfn.css("/redsys-payment-module.*css")} // Produccionn
    <script src="https://sis.redsys.es/sis/NC/redsys.js"></script>
[#assign site = sitefn.site()!] 
[#assign theme = sitefn.theme(site)!]
[#list theme.cssFiles as cssFile]
    <link rel="stylesheet" href="${cssFile.link}" media="${cssFile.media}" />
[/#list]
 
[#list theme.jsFiles as jsFile]
    <script src="${jsFile.link}"></script>
[/#list]
</head>

<body onload="loadRedsysForm()">

    <div class="alert alert-success" role="alert">
        estamos en el modo produccion, para que funcione los datos fun terminal y codigo de sesion deben ser correctos
    </div>
    <div class="alert alert-info" role="alert">
        pruebas en el servidor de produccion de redsys
    </div>
    <section>
        [@cms.area name="main" /]
    </section>
</body>

</html>