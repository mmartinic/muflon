<div id="grailsLogo" role="banner"><img src="${resource(dir: 'images', file: 'muflon_logo.png')}" alt="Muflon">
	<span class="header-main">Muflon</span>  
	<span id="loginHeader"><g:loginControl /></span>  
</div>
<div class="nav" role="navigation">
	<ul>
		<sec:ifLoggedIn>
			<li><g:link class="home" controller="episode"><g:message code="default.episodes.label" default="Episodes"/></g:link></li>
		</sec:ifLoggedIn>
		<sec:access controller="admin">
			<li><g:link class="create" controller="admin"><g:message code="default.admin.console.label" default="Admin console"/></g:link></li>
		</sec:access>
	</ul>
</div>