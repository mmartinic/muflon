package org.mmartinic.muflon

import grails.converters.JSON

import org.joda.time.LocalDate
import org.mmartinic.muflon.model.Episode
import org.springframework.security.access.annotation.Secured

@Secured('permitAll')
class EpisodeController {

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        def episodeInstanceList
        def range = params.range
        if (range == "all") {
            episodeInstanceList = Episode.withCriteria {
                and {
                    order("airDate", "asc")
                    order("episodeKey.show.id", "asc")
                    order("episodeKey.seasonNumber", "asc")
                    order("episodeKey.episodeNumber", "asc")
                }
            }
        }
        else if (range == "today") {
            def today = LocalDate.now()
            episodeInstanceList = Episode.withCriteria {
                and {
                    eq("airDate", today)
                    order("airDate", "asc")
                    order("episodeKey.show.id", "asc")
                    order("episodeKey.seasonNumber", "asc")
                    order("episodeKey.episodeNumber", "asc")
                }
            }
        }
        else {
            def today = LocalDate.now()
            def date1 = today - 20
            def date2 = today + 20
            episodeInstanceList = Episode.withCriteria {
                and {
                    between("airDate", date1, date2)
                    order("airDate", "asc")
                    order("episodeKey.show.id", "asc")
                    order("episodeKey.seasonNumber", "asc")
                    order("episodeKey.episodeNumber", "asc")
                }
            }
        }
        withFormat{
            html{ return [episodeInstanceList: episodeInstanceList] }
            json{ render episodeInstanceList as JSON }
        }
    }
}
