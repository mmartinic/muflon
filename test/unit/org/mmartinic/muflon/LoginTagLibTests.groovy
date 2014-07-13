package org.mmartinic.muflon

import static org.junit.Assert.*

import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.*
import grails.test.mixin.support.*
import groovy.mock.interceptor.StubFor

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(LoginTagLib)
class LoginTagLibTests {

    void testUserLoggedIn() {
        def mockCreator = new StubFor(SpringSecurityService)
        mockCreator.demand.isLoggedIn() { true }
        mockCreator.demand.getCurrentUser() { [username:"Frank"] }

        tagLib.springSecurityService = mockCreator.proxyInstance()

        assertEquals ("Hello Frank[<a href=\"/logout\">Logout</a>]", applyTemplate('<g:loginControl />'))
    }
    void testNotUserLoggedIn() {
        def mockCreator = new StubFor(SpringSecurityService)
        mockCreator.demand.isLoggedIn() { false }

        tagLib.springSecurityService = mockCreator.proxyInstance()

        assertEquals ("[<a href=\"/login\">Login</a>]", applyTemplate('<g:loginControl />'))
    }
}
