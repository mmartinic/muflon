<%@ page import="org.mmartinic.muflon.Episode" %>



<div class="fieldcontain ${hasErrors(bean: episodeInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="episode.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${episodeInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: episodeInstance, field: 'episodeNumber', 'error')} required">
	<label for="episodeNumber">
		<g:message code="episode.episodeNumber.label" default="Episode Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="episodeNumber" required="" value="${fieldValue(bean: episodeInstance, field: 'episodeNumber')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: episodeInstance, field: 'seasonNumber', 'error')} required">
	<label for="seasonNumber">
		<g:message code="episode.seasonNumber.label" default="Season Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="seasonNumber" required="" value="${fieldValue(bean: episodeInstance, field: 'seasonNumber')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: episodeInstance, field: 'airDate', 'error')} required">
	<label for="airDate">
		<g:message code="episode.airDate.label" default="Air Date" />
		<span class="required-indicator">*</span>
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: episodeInstance, field: 'show', 'error')} required">
	<label for="show">
		<g:message code="episode.show.label" default="Show" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="show" name="show.id" from="${org.mmartinic.muflon.Show.list()}" optionKey="id" required="" value="${episodeInstance?.show?.id}" class="many-to-one"/>
</div>

