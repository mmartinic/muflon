package org.mmartinic.muflon

import org.mmartinic.muflon.model.Episode

class EpisodeTagLib {

    def episodeNumber = {attrs ->
        Episode episode = attrs.episode
        out << String.format("%02d", episode.seasonNumber)
        out << "x"
        out << String.format("%02d", episode.episodeNumber)
    }

    def isohuntLink = {attrs ->
        Episode episode = attrs.episode
        out << "<a href='http://isohunt.com/torrents/?ihq="
        out << episode.show.name.encodeAsURL()
        out << "+S"
        out << String.format("%02d", episode.seasonNumber)
        out << "E"
        out << String.format("%02d", episode.episodeNumber)
        out << """'><img src="${resource(dir:'images',file:'isohunt.ico')}">Isohunt</a>"""
    }

    def addic7edLink = {attrs ->
        Episode episode = attrs.episode
        out << "<a href='http://www.addic7ed.com/search.php?search="
        out << episode.show.name.encodeAsURL()
        out << "+S"
        out << String.format("%02d", episode.seasonNumber)
        out << "E"
        out << String.format("%02d", episode.episodeNumber)
        out << """&Submit=Search'><img src="${resource(dir:'images',file:'addic7ed.ico')}">Addic7ed</a>"""
    }

    def piratebayLink = {attrs ->
        Episode episode = attrs.episode
        out << "<a href='http://thepiratebay.se/search/"
        out << episode.show.name.encodeAsURL()
        out << " ".encodeAsURL()
        out << "S"
        out << String.format("%02d", episode.seasonNumber)
        out << "E"
        out << String.format("%02d", episode.episodeNumber)
        out << """/0/7/0'><img src="${resource(dir:'images',file:'piratebay.ico')}">Piratebay</a>"""
    }
}
