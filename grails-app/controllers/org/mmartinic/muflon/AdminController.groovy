package org.mmartinic.muflon

import grails.plugins.springsecurity.Secured

import org.mmartinic.muflon.parser.Updater

@Secured(['ROLE_ADMIN'])
class AdminController {

	Updater updater
	
    def index() { }
	
    def rssUpdate() {
		updater.addOnlyFromRSSFeed()
		redirect(action: "index", params: params)
	}
	
    def partialUpdate() {
    	updater.partialUpdate()
    	redirect(action: "index", params: params)
    }
	
    def completeUpdate() {
    	updater.completeUpdate()
    	redirect(action: "index", params: params)
    }
}
