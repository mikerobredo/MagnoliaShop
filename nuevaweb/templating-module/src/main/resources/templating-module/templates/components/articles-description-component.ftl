



[#if (content.link?has_content)]
<textarea name="comentarios" rows="5" cols="100" position: absolute; left: 500px; top: 500px; right: 500px; bottom: 500px;>
    [#assign id = ctx.getParameter('id')!?html] el valor recibido es ${id}
    <br>hay contenido en el campo link</br>
    <br>El content link apunta al articulo (a su id si sacamos su contenido): ${content.link} </br>
    [#--
    <br>la misma podemos obtener el nombre con content by id:${articulo}</br> --]

</textarea>
[#-- modo de control de errores sin el try catch de ftl --] 
[#if (cmsfn.contentById(id, "caja" )?has_content)]
	[#-- encontrado --] 
	[#assign articulo = cmsfn.contentById(id, "caja" )]
[#else] 
	<br>Id articulo >> erronea</br>
[/#if]

 
[#-- modo con el try catch --] 
[#-- este metodo da errores en el log!
[#attempt] (cmsfn.contentById(id, "caja" )?has_content)
	[#assign articulo = cmsfn.contentById(id, "caja" )] 
[#recover]
	<br>Error al buscar articulo</br>
[/#attempt]--] 



    	[#assign paginas = model.cosas] 
    	<br>sql devuelve</br> 
    	<br> numero total de articulos en contrados : ${model.getTamQuery()}</br>
    	[#list paginas  as p]        	 
    	  [#assign myResource = cmsfn.contentByPath("/Home/Listado-de-articulos/detalle-de-articulo", "website")]
    	  	    [#assign art1 = cmsfn.contentById(p, "caja")]
    	  	    
    	  	    [#if (cmsfn.contentById(p, "caja" )?has_content)]
							[#-- encontrado --] 
					[#assign articulo2 = cmsfn.contentById(p, "caja" )]
				[#else] 
							<br>Id articulo >> erronea</br>
				[/#if]
    	  	    
    	  	    
    	  	    
		        <li><a href="${cmsfn.link(myResource)}?id=${p}">${articulo2!"${p}"}</a></li>
		        
    	[/#list] 
 
[#if (articulo?has_content)]
	<h2>${articulo}</h2>
	<div class="content">
    <div class="col">
    	<h5>${articulo}</h5> 
    	[#list articulo.categories as category] 
    	[#assign cat = cmsfn.contentById(category, "category" )] 
    	Categoria de ${cat!""} 
    	[/#list] 
    	
    	[#if (articulo.price?has_content)]
        <h3> Precio ${articulo.price!""} $</h3> 
        [#else]
        <h3> Sin stock</h3> 
        [/#if]
    		</div>
    		<div class="col">
        		<img width="300px" src="${damfn.getAssetLink(articulo.image)!" "}"/>
    		</div>
		</div>
		<div class="col">
    		<br>${articulo.description}</br>
		</div>
		[#else]
			<h3> Articulo no a la venta</h3> 
		[/#if] 
[/#if]
<link rel="icon" type="image/png" href=${damfn.getAssetLink(articulo.image)} sizes="16x16">