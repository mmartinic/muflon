package org.mmartinic.muflon

import org.apache.commons.lang.StringUtils;

class User {

    transient springSecurityService

    String username
    String password
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    String myEpisodesUsername
    String myEpisodesCookie
    String myEpisodesPwdmd5

    static constraints = {
        username blank: false, unique: true
        password blank: false
    }

    static mapping = { password column: '`password`' }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
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
        password = springSecurityService.encodePassword(password)
    }

    def isLoggedInToMyEpisodes() {
        return StringUtils.isNotEmpty(myEpisodesPwdmd5)
    }
}
