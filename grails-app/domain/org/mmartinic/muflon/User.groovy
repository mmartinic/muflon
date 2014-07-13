package org.mmartinic.muflon

import org.apache.commons.lang.StringUtils

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	String myEpisodesUsername
	String myEpisodesCookie
	String myEpisodesPwdmd5

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password blank: false
		myEpisodesUsername nullable:true
		myEpisodesCookie nullable:true
		myEpisodesPwdmd5 nullable:true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	def isLoggedInToMyEpisodes() {
		return StringUtils.isNotEmpty(myEpisodesPwdmd5)
	}
}
