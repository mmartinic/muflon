

package org.mmartinic.muflon.util

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.joda.time.LocalDate
import org.junit.*
import org.mmartinic.muflon.model.Episode;

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class CssUtilTests {

    @Before
    void setUp() {
        MetaClassInjector.init()
    }

    @Test
    void getEpisodeRowClassToday() {
        LocalDate today = new LocalDate()
        Episode episode = new Episode(airDate: today)
        assertEquals("today", CssUtil.getEpisodeRowClass("", null, episode)[0])
    }

    @Test
    void getEpisodeRowClassOdd() {
        LocalDate today = new LocalDate()
        Episode episode = new Episode(airDate: today - 1)
        assertEquals("oddPast", CssUtil.getEpisodeRowClass("", null, episode)[0])

        assertEquals("oddPast", CssUtil.getEpisodeRowClass("", today - 2, episode)[0])
        assertEquals("oddPast", CssUtil.getEpisodeRowClass("even", today - 2, episode)[0])

        Episode episode2 = new Episode(airDate: today + 1)
        assertEquals("oddFuture", CssUtil.getEpisodeRowClass("", today + 2, episode2)[0])
        assertEquals("oddFuture", CssUtil.getEpisodeRowClass("even", today + 2, episode2)[0])

        assertEquals("test", CssUtil.getEpisodeRowClass("test", today - 1, episode)[0])
    }

    @Test
    void getEpisodeRowClassEven() {
        LocalDate today = new LocalDate()

        Episode episode = new Episode(airDate: today - 1)
        assertEquals("evenPast", CssUtil.getEpisodeRowClass("odd", today - 2, episode)[0])

        Episode episode2 = new Episode(airDate: today + 1)
        assertEquals("evenFuture", CssUtil.getEpisodeRowClass("odd", today + 2, episode2)[0])
    }
}
