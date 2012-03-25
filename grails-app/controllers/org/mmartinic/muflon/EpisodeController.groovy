package org.mmartinic.muflon

import org.joda.time.LocalDate
import org.mmartinic.muflon.model.Episode
import org.springframework.dao.DataIntegrityViolationException

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
