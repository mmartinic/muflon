package org.mmartinic.muflon

import org.mmartinic.muflon.parser.RSSParser;
import org.mmartinic.muflon.parser.Updater

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
