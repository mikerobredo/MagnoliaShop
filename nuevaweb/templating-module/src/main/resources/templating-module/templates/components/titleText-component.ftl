
[#--imprimir un content que tiene richtext, text y un objeto --]
<h1 class="title-ppal">${cmsfn.decode(content).richtextTitle!""}</h1>[#--richtext --]
<h1 class="title-ppal">${content.textSubtitle!""}</h1>[#--text --]
[#assign objetito = cmsfn.contentByPath(content.objeto, "caja")][#--el objeto del componente lo asigno a una variable con content bypath --]
${objetito.date?string.short} [#--desde la variable puedo operar con este --]
${objetito.description!}[#--otro campo del objeto dentro del componente --]
[#if (objetito.image?has_content) ][#--evito nulos --]
<img width="300px" height="300px" src="${damfn.getAssetLink(objetito.image!)}"/>
[/#if]


<li>Vamos a imprimir el contenido del multivalue</li>
[#if (content.selectCategories?has_content)]
	[#-- ArrayList<Node> --]
	[#assign childrenCategories = cmsfn.children(content.selectCategories, "mgnl:contentNode")]
	[#list childrenCategories as category]
		[#if category.selectCategories?has_content]			
			[#assign multivalueObjeto = cmsfn.contentById(category.selectCategories, "caja" )]
			
			[#--imprimo susu atributos --]
			${multivalueObjeto.date?string.short}
			${multivalueObjeto.description!}
			[#if (multivalueObjeto.image?has_content) ][#--evito nulos --]
			<img width="300px" height="300px" src="${damfn.getAssetLink(multivalueObjeto.image!)}"/>
			[/#if]
		
		[/#if]
	[/#list]
	
	${multivalueObjeto}
[/#if] 

<li>aqui termina</li>

imprimir un asset hardcodeado en el ftl
[#assign myResource = cmsfn.contentByPath("/boligrafo", "caja")]
${myResource.description}

[#--evitar el nulo y imprimir un vacio (metodo no guay) para imprimir una imagen --]
<img width="300px" src="${damfn.getAssetLink(myResource.image)!""}"/>

[#--evitar el nulo modo official , para imprimir una imagen--]
	[#if (myResource.image?has_content) ]
    <img width="300px" height="300px" src="${damfn.getAssetLink(myResource.image)}"/>
    [/#if]
    
 
    

[#assign root = cmsfn.contentByPath("/", "caja")]