<div class="col" id="starfleet"> 
  <header style="background-image: url('${damfn.getAssetLink(content.image)}')">
    <h1>${content.firstText}<br/><small>&mdash; <em>${content.secondText}</em> &mdash;</small></h1>
   </header>
    <nav>
    <button class="nav-trigger">
      <span class="entypo-menu"></span>
    </button>
    <button class="nav-close">
      <span class="entypo-cancel"></span>
    </button>
    <ul class="main-nav"> 
                  [#if content.headerLinks?? && content.headerLinks?has_content]
                    [#list cmsfn.children(content.headerLinks) as headerLink]
                    	[#if headerLink.linkType?has_content]
					    	[#if headerLink.linkType == 'internalLink']
					    		[#assign link = cmsfn.link("website", headerLink.linkTypeinternalLink!"")]
					    	[#else]
					    		[#assign link = headerLink.linkTypeexternalLink]
					    	[/#if]
					    [/#if]                    	
                    	<li><a href="${link!"#"}">${headerLink.text!""}</a></li>                   		
                    [/#list]
                [/#if]               
        </ul>
  </nav>  
<ul class="nav navbar-nav" id="ulmenu">  
<ul class="main-nav"> 
[#assign myResource = cmsfn.contentByPath("/Home", "website")] 
[#list cmsfn.children(myResource, "mgnl:page") as child ]
   <li><a href="${cmsfn.link(child)}">${child.title!}</a></li>
[/#list]
   </ul>
  </nav>  