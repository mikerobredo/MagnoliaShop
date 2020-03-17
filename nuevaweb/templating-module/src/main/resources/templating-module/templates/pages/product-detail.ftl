[#assign title = content.title!"Titulo no encontrado"]
<!DOCTYPE html>
<html>

<head>
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,400italic,700,700italic,900,900italic" rel="stylesheet">
    <title>${title}</title>
    <link rel="stylesheet" href="${ctx.contextPath}/.resources/templating-module/webresources/css/styleBasic.css">

    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" /> [@cms.page /]
</head>

<body>
    [@cms.area name="header"/]
    <div class="container">
        <div class="main">
            [@cms.area name="main"/]

        </div>
    </div>
    [@cms.area name="footer"/]
</body>

</html>