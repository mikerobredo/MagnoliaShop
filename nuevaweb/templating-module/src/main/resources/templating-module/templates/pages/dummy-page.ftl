[#assign title = content.title!"Dummy page (created by maven archetype)"]
<!DOCTYPE html>
<html>
   <head>
      <title>${title}</title>
      <link rel="stylesheet" href="${ctx.contextPath}/.resources/templating-module/webresources/css/style.css">
      [@cms.page /]
   </head>
   <body>
      <div class="container">
         <header>
            <h1>${title}</h1>
         </header>
         <h2>Esta es mi dummy page</h2>
         <div class="main">
            [@cms.area name="main"/]
         </div>
      </div>
   </body>
</html>