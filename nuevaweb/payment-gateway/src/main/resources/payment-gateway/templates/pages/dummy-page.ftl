[#assign title = content.title!"Dummy page (created by maven archetype)"]
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>

    <link rel="stylesheet" href="${ctx.contextPath}/.resources/payment-gateway/webresources/css/style.css">
	<script src="https://code.jquery.com/jquery-3.1.0.min.js"></script>
	
	[@cms.page /]
</head>
<body>
<div class="container">
    <header>
        <h1>${title}</h1>
    </header>
	
	<div class="cssload-loader" style="display:none">Loading</div>

    <div id="main" class="main">
    [@cms.area name="main"/]
    </div>

</div>
</body>
</html>
