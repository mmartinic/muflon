package org.mmartinic.muflon

import grails.plugins.springsecurity.Secured

import org.mmartinic.muflon.parser.MyEpisodesHTTPClient
import org.mmartinic.muflon.parser.Updater

@Secured([Role.ADMIN])
class AdminController {

    Updater updater
    def springSecurityService

    def index() {
        if (flash.message == null) {
            if (springSecurityService.currentUser.isLoggedInToMyEpisodes()) {
                flash.message = message(code: "myepisodes.found.login", default:"Logged in to My episodes")
            }
            else {
                flash.message = message(code: "myepisodes.no.login", default:"Please login to My episodes")
            }
        }
    }

    def rssUpdate() {
        if (springSecurityService.currentUser.isLoggedInToMyEpisodes()) {
            User user = springSecurityService.currentUser
            updater.addOnlyFromRSSFeed(user.myEpisodesUsername, user.myEpisodesPwdmd5)
            flash.message = message(code: "myepisodes.update.rss.finished", default:"Rss update finished")
        }
        else {
            flash.message = message(code: "myepisodes.no.login", default:"Please login to My episodes")
        }
        redirect(action: "index")
    }

    def partialUpdate() {
        if (springSecurityService.currentUser.isLoggedInToMyEpisodes()) {
            User user = springSecurityService.currentUser
            updater.partialUpdate(user.myEpisodesUsername, user.myEpisodesPwdmd5, user.myEpisodesCookie)
            flash.message = message(code: "myepisodes.update.partial.finished", default:"Partial update finished")
        }
        else {
            flash.message = message(code: "myepisodes.no.login", default:"Please login to My episodes")
        }
        redirect(action: "index")
    }

    def completeUpdate() {
        if (springSecurityService.currentUser.isLoggedInToMyEpisodes()) {
            User user = springSecurityService.currentUser
            updater.completeUpdate(user.myEpisodesCookie)
            flash.message = message(code: "myepisodes.update.complete.finished", default:"Complete update finished")
        }
        else {
            flash.message = message(code: "myepisodes.no.login", default:"Please login to My episodes")
        }
        redirect(action: "index")
    }

    def myEpisodesLogin() {
        String username = params.username
        String password = params.password
        MyEpisodesHTTPClient myEpisodesHTTPClient = new MyEpisodesHTTPClient()
        def cookies = myEpisodesHTTPClient.login(username, password)
        if (cookies[MyEpisodesHTTPClient.PHPSESSGID]) {
            String cookie = ""
            cookies.each {key, value ->
                cookie += key + "=" + value + "; "
            }
            User user = springSecurityService.currentUser
            user.myEpisodesUsername = username
            user.myEpisodesCookie = cookie
            user.myEpisodesPwdmd5 = cookies[MyEpisodesHTTPClient.PHPSESSGID]
            user.save(flush: true)
            flash.message = message(code: "myepisodes.login.success", default:"Successful login to My episodes")
        }
        else {
            flash.message = message(code: "myepisodes.login.failed", default:"Login to My episodes failed")
        }
        redirect(action: "index")
    }
}
