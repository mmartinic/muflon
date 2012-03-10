package org.mmartinic.muflon.util

import org.joda.time.LocalDate
import org.mmartinic.muflon.model.Episode

class CssUtil {

	static def getEpisodeRowClass(String cssClass, LocalDate previousDate, Episode episode) {
		LocalDate today = new LocalDate()
		
		if (previousDate != episode.airDate) {
			if (previousDate != null) {
				if (cssClass.startsWith("odd")) {
					cssClass = "even"
				}
				else {
					cssClass = "odd"
				}
			}
			else {
				cssClass = "odd"
			}
			
			if (episode.airDate > today) {
				cssClass += "Future"
			}
			else if (episode.airDate < today) {
				cssClass += "Past"
			}
			
			previousDate = episode.airDate
		}
		cssClass = episode.airDate == today ? "today" : cssClass
		[cssClass, previousDate]
	}
	
}
