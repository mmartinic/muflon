package org.mmartinic.muflon

import org.joda.time.LocalDate
import org.junit.*
import org.mmartinic.muflon.model.Episode
import org.mmartinic.muflon.model.Show
import org.mmartinic.muflon.model.Episode.EpisodeKey
import org.mmartinic.muflon.util.MetaClassInjector

import grails.test.mixin.*

@TestFor(EpisodeController)
class EpisodeControllerTests {

    @Before
    void setUp() {
        MetaClassInjector.init()
        Episode.metaClass.static.findAllByAirDateBetween = {LocalDate date1, LocalDate date2, Map map ->
            LocalDate date = new LocalDate()
            Show show = new Show(id: 1, name: "show")
            EpisodeKey episodeKey = new EpisodeKey(show: show, seasonNumber: 1, episodeNumber: 2)
            Episode episode = new Episode(name: "episode", airDate: date, episodeKey: episodeKey)
            return [episode]
        }
    }

    void testIndex() {
        controller.index()
        assert "/episode/list" == response.redirectedUrl
    }

    void testList() {
        def model = controller.list()
        assert model.episodeInstanceList.size() == 1
    }
}
