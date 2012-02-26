import org.joda.time.LocalDate
import org.mmartinic.muflon.model.Episode
import org.mmartinic.muflon.model.Episode.EpisodeKey;
import org.mmartinic.muflon.model.Show;

import grails.util.GrailsUtil

class BootStrap {
	
    def init = { servletContext ->
		
		LocalDate.metaClass.minus = {int days ->
			return delegate.minusDays(days)
		}
		
		LocalDate.metaClass.plus = {int days ->
			return delegate.plusDays(days)
		}
		
		switch(GrailsUtil.environment) {
			case "development":
//			Show show1 = new Show(id: 1, name: "Modern Family")
//			Show show2 = new Show(id: 2, name: "The Simpsons")
//			show1.save(failOnError: true)
//			show2.save(failOnError: true)
//			new Episode(name: "Lifetime Supply", airDate: new LocalDate(), show: show1, episodeNumber: 11, seasonNumber: 3).save(failOnError: true)
//			new Episode(name: "Lifetime Supply2", airDate: new LocalDate()-1, show: show1, episodeNumber: 12, seasonNumber: 3).save(failOnError: true)
//			new Episode(name: "Lifetime Supply3", airDate: new LocalDate()-2, show: show1, episodeNumber: 13, seasonNumber: 3).save(failOnError: true)
//			new Episode(name: "Politically Inept, With Homer Simpson", airDate: new LocalDate(), show: show2, episodeNumber: 10, seasonNumber: 23).save(failOnError: true)
//			new Episode(name: "Politically Inept, With Homer Simpson2", airDate: new LocalDate()-1, show: show2, episodeNumber: 11, seasonNumber: 23).save(failOnError: true)
//			new Episode(name: "Politically Inept, With Homer Simpson3", airDate: new LocalDate()-2, show: show2, episodeNumber: 12, seasonNumber: 23).save(failOnError: true)
			break
			case "production" : 
			break
		}
    }
    def destroy = {
    }
}
