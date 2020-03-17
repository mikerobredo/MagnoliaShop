 <section id="prices">
      <h2>${content.mainTitle}</h2>
      
      
    [#if (content.threCards?has_content)]
	[#-- ArrayList<Node> --]
	<div class="content">
	[#assign childrenCategories = cmsfn.children(content.threCards, "mgnl:contentNode")]
	[#list childrenCategories as category]
		[#if category.tx1?has_content]			
		<div class="col card">
          <div class="card-body">
            <p>${category.tx1}</p>			
			</div>
          <div class="card-title">
            <h3>${category.tx2}</h3>
            <p class="price"> ${category.tx3}</p>
            <button type="button">${category.tx4}</button>
          </div>
        </div>
		
		[/#if]
	[/#list]
	
	
	[/#if] 

      </div>
    </section>