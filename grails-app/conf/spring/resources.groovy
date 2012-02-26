import org.mmartinic.muflon.dao.impl.EpisodeService
import org.mmartinic.muflon.dao.impl.ShowService
import org.mmartinic.muflon.parser.Updater

beans = {
	updater(Updater) {
	}
	episodeService(EpisodeService) {
	}
	showService(ShowService) {
	}
}
