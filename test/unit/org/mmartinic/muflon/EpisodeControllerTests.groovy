package org.mmartinic.muflon



import org.junit.*
import org.mmartinic.muflon.model.Episode

import grails.test.mixin.*

@TestFor(EpisodeController)
@Mock(Episode)
class EpisodeControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/episode/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.episodeInstanceList.size() == 0
        assert model.episodeInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.episodeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.episodeInstance != null
        assert view == '/episode/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/episode/show/1'
        assert controller.flash.message != null
        assert Episode.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/episode/list'


        populateValidParams(params)
        def episode = new Episode(params)

        assert episode.save() != null

        params.id = episode.id

        def model = controller.show()

        assert model.episodeInstance == episode
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/episode/list'


        populateValidParams(params)
        def episode = new Episode(params)

        assert episode.save() != null

        params.id = episode.id

        def model = controller.edit()

        assert model.episodeInstance == episode
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/episode/list'

        response.reset()


        populateValidParams(params)
        def episode = new Episode(params)

        assert episode.save() != null

        // test invalid parameters in update
        params.id = episode.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/episode/edit"
        assert model.episodeInstance != null

        episode.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/episode/show/$episode.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        episode.clearErrors()

        populateValidParams(params)
        params.id = episode.id
        params.version = -1
        controller.update()

        assert view == "/episode/edit"
        assert model.episodeInstance != null
        assert model.episodeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/episode/list'

        response.reset()

        populateValidParams(params)
        def episode = new Episode(params)

        assert episode.save() != null
        assert Episode.count() == 1

        params.id = episode.id

        controller.delete()

        assert Episode.count() == 0
        assert Episode.get(episode.id) == null
        assert response.redirectedUrl == '/episode/list'
    }
}
