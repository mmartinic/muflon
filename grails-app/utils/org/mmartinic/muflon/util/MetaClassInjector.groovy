package org.mmartinic.muflon.util

import grails.converters.JSON

import org.joda.time.LocalDate
import org.mmartinic.muflon.model.Episode

class MetaClassInjector {

    static def init() {
        LocalDate.metaClass.minus = {int days ->
            return delegate.minusDays(days)
        }

        LocalDate.metaClass.plus = {int days ->
            return delegate.plusDays(days)
        }

        JSON.registerObjectMarshaller(Episode) {Episode it ->
            def returnArray = [:]
            returnArray['episodeName'] = it.name
            returnArray['showName'] = it.episodeKey.show.name
            returnArray['episodeNumber'] = it.episodeKey.episodeNumber
            returnArray['seasonNumber'] = it.episodeKey.seasonNumber
            returnArray['airDate'] = it.airDate
            return returnArray
        }
    }
}