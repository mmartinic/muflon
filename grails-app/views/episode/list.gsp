
<%@page import="org.joda.time.LocalDate"%>
<%@ page import="org.mmartinic.muflon.util.CssUtil" %>
<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'episode.label', default: 'Episode')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-episode" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div id="list-episode" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
                <thead>
                    <tr>
                        <th>${message(code: 'episode.showName.label', default: 'Show Name')}</th>
                        <th>${message(code: 'episode.name.label', default: 'Name')}</th>
                        <th>${message(code: 'episode.episodeNumber.label', default: 'Episode Number')}</th>
                        <th>${message(code: 'episode.airDate.label', default: 'Air Date')}</th>
                        <th>${message(code: 'episode.link.label', default: 'Link')}</th>
                    </tr>
                </thead>
                <tbody>
                <%
                LocalDate previousDate
                String cssClass
                %>
                <g:each in="${episodeInstanceList}" var="episodeInstance">
                    <%
                    (cssClass, previousDate) = CssUtil.getEpisodeRowClass(cssClass, previousDate, episodeInstance)
                    %>
                    <tr class="${cssClass}">
                    
                        <td>${fieldValue(bean: episodeInstance.show, field: "name")}</td>
                    
                        <td>${fieldValue(bean: episodeInstance, field: "name")}</td>
                        
                        <td><g:episodeNumber episode="${episodeInstance}" /></td>
                    
                        <td><g:formatDate date="${episodeInstance.airDate.toDate()}" format="dd/MM/yyyy"/> </td>
                        
                        <td>
                            <div>
                                <g:isohuntLink episode="${episodeInstance}" />
                                <g:piratebayLink episode="${episodeInstance}" />
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
