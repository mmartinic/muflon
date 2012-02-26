package org.mmartinic.muflon

import org.mmartinic.muflon.parser.RSSParser;
import org.mmartinic.muflon.parser.Updater

class AdminController {

	Updater updater
	
    def index() { }
	
    def refresh() {
//		RSSParser rssParser = new RSSParser()
//		println rssParser.getEpisodesFromRSSFeed()
		updater.addOnlyFromRSSFeed()
		redirect(action: "index", params: params)
	}
}
