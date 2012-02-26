
<%@page import="org.joda.time.LocalDate"%>
<%@ page import="org.mmartinic.muflon.Episode" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'episode.label', default: 'Episode')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-episode" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-episode" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="showName" title="${message(code: 'episode.showName.label', default: 'Show Name')}" />
						
						<g:sortableColumn property="name" title="${message(code: 'episode.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="episodeNumber" title="${message(code: 'episode.episodeNumber.label', default: 'Episode Number')}" />
					
						<g:sortableColumn property="airDate" title="${message(code: 'episode.airDate.label', default: 'Air Date')}" />
					
						<th>${message(code: 'episode.link.label', default: 'Link')}</th>
						
					</tr>
				</thead>
				<tbody>
				<%
				LocalDate previousDate
				String cssClass
				String cssTime
				LocalDate today = new LocalDate()
				%>
				<g:each in="${episodeInstanceList}" var="episodeInstance">
					<%
					if (previousDate != episodeInstance.airDate) {
						if (previousDate != null) {
							if (cssClass == "odd") {
								cssClass = "even"
							}
							else {
								cssClass = "odd"
							}
						}
						else {
							cssClass = "odd"
						}
				
						if (episodeInstance.airDate > today) {
							cssTime = "Future"
						}
						else if (episodeInstance.airDate < today) {
							cssTime = "Past"
						}
				
						previousDate = episodeInstance.airDate
					}
					%>
					<tr class="${episodeInstance.airDate == today ? "today" : cssClass+cssTime}">
					
						<td>${fieldValue(bean: episodeInstance.show, field: "name")}</td>
					
						<td>${fieldValue(bean: episodeInstance, field: "name")}</td>
						
						<td><g:episodeNumber episode="${episodeInstance}" /></td>
					
						<td><g:formatDate date="${episodeInstance.airDate.toDate()}" format="dd/MM/yyyy"/> </td>
						
						<td>
							<div>
								<g:isohuntLink episode="${episodeInstance}" />
								<g:addic7edLink episode="${episodeInstance}" />
							</div>
						</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
		</div>
	</body>
</html>
