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
//        params.max = Math.min(params.max ? params.int('max') : 10, 100)
//        [episodeInstanceList: Episode.list(params), episodeInstanceTotal: Episode.count()]
		def date1 = new LocalDate()-20
		def date2 = new LocalDate()+20
		[episodeInstanceList: Episode.findAllByAirDateBetween (date1, date2, [sort:"airDate", order:"asc"])]
    }

    def create() {
        [episodeInstance: new Episode(params)]
    }

    def save() {
        def episodeInstance = new Episode(params)
        if (!episodeInstance.save(flush: true)) {
            render(view: "create", model: [episodeInstance: episodeInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'episode.label', default: 'Episode'), episodeInstance.id])
        redirect(action: "show", id: episodeInstance.id)
    }

    def show() {
        def episodeInstance = Episode.get(params.id)
        if (!episodeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'episode.label', default: 'Episode'), params.id])
            redirect(action: "list")
            return
        }

        [episodeInstance: episodeInstance]
    }

    def edit() {
        def episodeInstance = Episode.get(params.id)
        if (!episodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'episode.label', default: 'Episode'), params.id])
            redirect(action: "list")
            return
        }

        [episodeInstance: episodeInstance]
    }

    def update() {
        def episodeInstance = Episode.get(params.id)
        if (!episodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'episode.label', default: 'Episode'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (episodeInstance.version > version) {
                episodeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'episode.label', default: 'Episode')] as Object[],
                          "Another user has updated this Episode while you were editing")
                render(view: "edit", model: [episodeInstance: episodeInstance])
                return
            }
        }

        episodeInstance.properties = params

        if (!episodeInstance.save(flush: true)) {
            render(view: "edit", model: [episodeInstance: episodeInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'episode.label', default: 'Episode'), episodeInstance.id])
        redirect(action: "show", id: episodeInstance.id)
    }

    def delete() {
        def episodeInstance = Episode.get(params.id)
        if (!episodeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'episode.label', default: 'Episode'), params.id])
            redirect(action: "list")
            return
        }

        try {
            episodeInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'episode.label', default: 'Episode'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'episode.label', default: 'Episode'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
