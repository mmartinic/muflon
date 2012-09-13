package org.mmartinic.muflon

import grails.converters.JSON

import org.joda.time.LocalDate
import org.mmartinic.muflon.model.Episode

class EpisodeController {

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        def episodeInstanceList
        def range = params.range
        if (range == "all") {
            episodeInstanceList = Episode.list(sort:"airDate", order:"asc")
        }
        else if (range == "today") {
            def today = LocalDate.now()
            episodeInstanceList = Episode.findAllByAirDate(today, [sort:"airDate", order:"asc"])
        }
        else {
            def today = LocalDate.now()
            def date1 = today - 20
            def date2 = today + 20
            episodeInstanceList = Episode.findAllByAirDateBetween (date1, date2, [sort:"airDate", order:"asc"])
        }
        withFormat{
            html{ return [episodeInstanceList: episodeInstanceList] }
            json{ render episodeInstanceList as JSON }
        }
    }
}
