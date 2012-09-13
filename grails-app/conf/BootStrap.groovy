import grails.util.GrailsUtil

import org.mmartinic.muflon.Role
import org.mmartinic.muflon.User
import org.mmartinic.muflon.UserRole
import org.mmartinic.muflon.util.MetaClassInjector



class BootStrap {

    def init = { servletContext ->

        MetaClassInjector.init()

        def adminRole = new Role(authority: Role.ADMIN).save(flush: true)
        def userRole = new Role(authority: Role.USER).save(flush: true)
        def user = new User(username: 'corax', enabled: true, password: 'change.me').save(flush: true)
        UserRole.create user, adminRole, true
        assert Role.count() == 2
        assert User.count() == 1
        assert UserRole.count() == 1

        switch(GrailsUtil.environment) {
            case "development":
                break
            case "production" :
                break
        }
    }
    def destroy = {
    }
}
