import grails.util.GrailsUtil

import org.joda.time.LocalDate
import org.mmartinic.muflon.Role
import org.mmartinic.muflon.User
import org.mmartinic.muflon.UserRole



class BootStrap {
	
    def init = { servletContext ->
		
		LocalDate.metaClass.minus = {int days ->
			return delegate.minusDays(days)
		}
		
		LocalDate.metaClass.plus = {int days ->
			return delegate.plusDays(days)
		}
		
		switch(GrailsUtil.environment) {
			case "development":
			def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
			def userRole = new Role(authority: 'ROLE_USER').save(flush: true)
			
			def testUser = new User(username: 'me', enabled: true, password: 'password')
			testUser.save(flush: true)
			
			UserRole.create testUser, adminRole, true
			
			assert User.count() == 1
			assert Role.count() == 2
			assert UserRole.count() == 1
			break
			case "production" : 
			break
		}
    }
    def destroy = {
    }
}
