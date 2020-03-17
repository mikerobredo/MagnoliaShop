[#assign title = content.title!"Titulo de la pagina ejercicio"]
<!DOCTYPE html>
<html>
   <head>
      <link href="https://fonts.googleapis.com/css?family=Lato:300,400,400italic,700,700italic,900,900italic" rel="stylesheet">
      <title>${title}</title>
      <link rel="stylesheet" href="${ctx.contextPath}/.resources/templating-module/webresources/css/styleEjercicio.css">
      [@cms.page /]
   </head>
   <body>
      <div class="container">
         <h1>${title}</h1>
         <h2>Esta es mi dummy page</h2>
         <div class="main">
            [@cms.area name="main"/]
         </div>
      </div>
   </body>
</html>