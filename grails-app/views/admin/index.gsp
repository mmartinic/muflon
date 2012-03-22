
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>${message(code: 'admin.title.label', default: 'Admin')}</title>
	</head>
	<body>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div class="content" role="main">
			<h1>${message(code: 'admin.title.label', default: 'Admin')}</h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:link class="create" action="rssUpdate"><input type="button" value="${message(code: 'admin.rss.update.label', default: 'RSS update')}"/></g:link>
			<g:link class="create" action="partialUpdate"><input type="button" value="${message(code: 'admin.partial.update.label', default: 'Partial update')}"/></g:link>
			<g:link class="create" action="completeUpdate"><input type="button" value="${message(code: 'admin.complete.update.label', default: 'Complete update')}"/></g:link>
		</div>
	</body>
</html>
