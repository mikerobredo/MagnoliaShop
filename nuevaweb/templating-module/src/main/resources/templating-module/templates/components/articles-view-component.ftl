[#if (content.link?has_content)]
	<textarea name="comentarios" rows="5" cols="100" position: absolute; left: 500px; top: 500px; right: 500px; bottom: 500px;>
	    <br>hay contenido en el campo link</br>
	    <br>El content link apunta a la carpeta(a su id si sacamos su contenido): ${content.link} </br>
	    [#assign carpeta = cmsfn.contentById(content.link, "caja" )]
	
	    <br>la misma podemos obtener el nombre con content by id:${carpeta}</br>
	</textarea>
	<h2>${carpeta}</h2> 
	[#assign objetosdecarpeta = cmsfn.children(carpeta, "mgnl:objeto")] 
	[#list objetosdecarpeta as category]
		<div class="content">
		    <div class="col">
		        <h3>${category}</h3>
		        [#assign myResource = cmsfn.contentByPath("/Home/Listado-de-articulos/detalle-de-articulo", "website")]
		        <li><a href="${cmsfn.link(myResource)}?id=${category.@uuid}">${category!}</a></li>
		        ${myResource}
		    </div>
		    <div class="col">
		        <img width="300px" src="${damfn.getAssetLink(category.image)!" "}"/>
		    </div>
		</div>
	[/#list] 
[/#if]

[#-- .@type, .@uuid, .@name, .@path --]