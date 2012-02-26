
<%@ page import="org.mmartinic.muflon.Episode" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'episode.label', default: 'Episode')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-episode" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-episode" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list episode">
			
				<g:if test="${episodeInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="episode.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${episodeInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${episodeInstance?.episodeNumber}">
				<li class="fieldcontain">
					<span id="episodeNumber-label" class="property-label"><g:message code="episode.episodeNumber.label" default="Episode Number" /></span>
					
						<span class="property-value" aria-labelledby="episodeNumber-label"><g:fieldValue bean="${episodeInstance}" field="episodeNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${episodeInstance?.seasonNumber}">
				<li class="fieldcontain">
					<span id="seasonNumber-label" class="property-label"><g:message code="episode.seasonNumber.label" default="Season Number" /></span>
					
						<span class="property-value" aria-labelledby="seasonNumber-label"><g:fieldValue bean="${episodeInstance}" field="seasonNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${episodeInstance?.airDate}">
				<li class="fieldcontain">
					<span id="airDate-label" class="property-label"><g:message code="episode.airDate.label" default="Air Date" /></span>
					
						<span class="property-value" aria-labelledby="airDate-label"><g:fieldValue bean="${episodeInstance}" field="airDate"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${episodeInstance?.show}">
				<li class="fieldcontain">
					<span id="show-label" class="property-label"><g:message code="episode.show.label" default="Show" /></span>
					
						<span class="property-value" aria-labelledby="show-label"><g:link controller="show" action="show" id="${episodeInstance?.show?.id}">${episodeInstance?.show?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${episodeInstance?.id}" />
					<g:link class="edit" action="edit" id="${episodeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
