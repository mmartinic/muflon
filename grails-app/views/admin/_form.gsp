<div class="fieldcontain required">
	<label for="username">
		<g:message code="springSecurity.login.username.label" default="Username" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" required=""/>
</div>

<div class="fieldcontain required">
	<label for="password">
		<g:message code="springSecurity.login.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="password" name="password" required=""/>
</div>
