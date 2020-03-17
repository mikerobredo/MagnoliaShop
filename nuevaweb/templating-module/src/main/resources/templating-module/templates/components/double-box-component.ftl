<section id="about">
      <div class="content">
        <div class="col">
          <div class="video-container">
          [#if (content.leftText?has_content)]
          <iframe width="560" height="315" src="${content.leftText}" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
          [/#if]
          </div>
        </div>
        <div class="col">
        	[#if (content.rightTitle?has_content)]
        	<h2>${cmsfn.decode(content).rightTitle!""}</h2>
        	[/#if]
        	[#if (content.rightText?has_content)]
        	${cmsfn.decode(content).rightText!""}
        	[/#if]
        </div>
      </div>
    </section>