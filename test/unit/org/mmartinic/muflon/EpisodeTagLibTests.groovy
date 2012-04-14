package org.mmartinic.muflon

import grails.test.mixin.*

import org.joda.time.LocalDate
import org.junit.*
import org.mmartinic.muflon.model.Episode
import org.mmartinic.muflon.model.Episode.EpisodeKey;
import org.mmartinic.muflon.model.Show

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(EpisodeTagLib)
class EpisodeTagLibTests {

    void testEpisodeNumber() {
        LocalDate date = new LocalDate()
        Show show = new Show(id: 1, name: "show")
        EpisodeKey episodeKey = new EpisodeKey(show: show, seasonNumber: 1, episodeNumber: 2)
        Episode episode = new Episode(name: "episode", airDate: date, episodeKey: episodeKey)

        assertEquals ("01x02", applyTemplate('<g:episodeNumber episode="${episode}"/>', [episode: episode]))
    }

    void testIsohuntLink() {
        LocalDate date = new LocalDate()
        Show show = new Show(id: 1, name: "show")
        EpisodeKey episodeKey = new EpisodeKey(show: show, seasonNumber: 1, episodeNumber: 2)
        Episode episode = new Episode(name: "episode", airDate: date, episodeKey: episodeKey)

        assertEquals ("<a href='http://isohunt.com/torrents/?ihq=show+S01E02'><img src=\"/images/isohunt.ico\"></a>", applyTemplate('<g:isohuntLink episode="${episode}"/>', [episode: episode]))
    }

    void testAddic7edLink() {
        LocalDate date = new LocalDate()
        Show show = new Show(id: 1, name: "show")
        EpisodeKey episodeKey = new EpisodeKey(show: show, seasonNumber: 1, episodeNumber: 2)
        Episode episode = new Episode(name: "episode", airDate: date, episodeKey: episodeKey)

        assertEquals ("<a href='http://www.addic7ed.com/search.php?search=show+S01E02&Submit=Search'><img src=\"/images/addic7ed.ico\"></a>", applyTemplate('<g:addic7edLink episode="${episode}"/>', [episode: episode]))
    }
}
