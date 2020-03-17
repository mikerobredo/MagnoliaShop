<h1>Ejemplo de multivalue</h1>
<br>

${content.merchantSignature!""}
<br>
contenido de una propiedad en ingles



<br>
valores del multivalue de links a paginas
<br>
    	[#list content.mv as page] 
    	[#assign pag = cmsfn.contentById(page, "website" )] 
    	<br>pagina con nombre: ${pag!""} 
    	<a href="${cmsfn.link(pag)}">${pag.title!}</a>
    	[/#list] 
    	
<br>    	
<h1>Ejemplo de composite</h1> 	
<br>  
pelicula  	
${content.ScoreFilm!""}    	
puntuacion:
${content.ScorePunt!""}    	

<h1>Ejemplo de Switchable</h1>
${content.message!""}
<br>    	
Ahora un switchable  
    [#assign msgslctd =content.message] 
		[#if msgslctd?has_content]
			[#if msgslctd == "richText"]
				<br>    	
				de tipo texto rico
				<br>
				${content.messagerichText!""}
			[#elseif msgslctd == "plainText"]
				<br>    	
				de tipo texto plano	
				<br>
				${content.messageplainText!""}
			[/#if]
		[/#if]
		
		
		
<h1>Ejemplo de Transformers</h1>
<br> 
DelegatingCompositeFieldTransformer
<br>  
${content.titleCompositeFieldDefinition!""}
<br>  
${content.dateCompositeFieldDefinition!""}
<br> 

CompositeTransformer
<br>  
${content.compositecomptitle!""}
<br>  
${content.compositecompdate!""}
<br> 

nooop
<br>  

<br>
valores del multivalue de json
<br>
${content.mvjson}

<br> 
<br>
valores del multivalue de DelegatingMultiValueFieldTransformer
<br>
[#assign p1 =content.shoppingList0] 
${p1.shoppingitem!""}
<br> 


<br>
valores del multivalue de DMultiValueChildrenNodeTransformer
<br>
ni idea!!! no me deja poner 00
<br> 
  <br>    
valores del multivalue de MultiValueChildNodeTransformer
<br>
[#assign p1 =content.shoppingListSubChildNodeTransformer] 
    	${content.shoppingListSubChildNodeTransformer!""}

<br>    <br>     
valores del multivalue de MultiValueSubChildrenNodePropertiesTransformer
<br>
[#assign p3 =content.shoppingListsubchilpropr] 

     	[#list cmsfn.children(p3, "mgnl:contentNode") as child ]
    		<li>${child.shoppingListsubchilpropr!}</li>
		[/#list]
<br>     
<br>    <br>     
valores del multivalue composite
<br>
[#assign p3 =content.MVCevents] 

     	[#list cmsfn.children(p3, "mgnl:contentNode") as child ]
    		<li>${child.MVCdate!}</li>
    		<li>${child.MVCtitle!}</li>
		[/#list]
<br>         
<h1>Ejemplo MVCMultivalue</h1>
[#-- [#if .....] --]
	${model.iterateMultivalueNodes(cmsfn.asJCRNode(content))!"ERROR"}
      de esta manera le enviamos a java el node desde el cual va a buscar las propiedades !!! 
   
   <br>  
   <h1>el  resumen definitivo</h1>   
   <br>  
${model.iterateFlights(cmsfn.asJCRNode(content))!"ERROR"}      
<br>  
         