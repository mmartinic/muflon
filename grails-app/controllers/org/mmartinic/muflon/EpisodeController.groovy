package org.mmartinic.muflon

import grails.plugins.springsecurity.Secured

import org.joda.time.LocalDate

import org.mmartinic.muflon.model.Episode

@Secured([Role.USER, Role.ADMIN])
class EpisodeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
		def date1 = new LocalDate()-20
		def date2 = new LocalDate()+20
		[episodeInstanceList: Episode.findAllByAirDateBetween (date1, date2, [sort:"airDate", order:"asc"])]
    }
    
}
