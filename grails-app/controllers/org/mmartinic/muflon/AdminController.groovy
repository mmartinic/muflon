package org.mmartinic.muflon

import grails.plugins.springsecurity.Secured

import org.mmartinic.muflon.parser.Updater

@Secured([Role.ADMIN])
class AdminController {

	Updater updater
	
    def index() { }
	
    def rssUpdate() {
		updater.addOnlyFromRSSFeed()
		flash.message = message(code: "update.rss.finished", default:"Rss update finished")
		redirect(action: "index", params: params)
	}
	
    def partialUpdate() {
    	updater.partialUpdate()
    	flash.message = message(code: "update.partial.finished", default:"Partial update finished")
    	redirect(action: "index", params: params)
    }
	
    def completeUpdate() {
    	updater.completeUpdate()
    	flash.message = message(code: "update.complete.finished", default:"Complete update finished")
    	redirect(action: "index", params: params)
    }
}
