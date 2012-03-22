package org.mmartinic.muflon.parser;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.ClientProtocolException;
import org.mmartinic.muflon.dao.model.EpisodeServiceException;
import org.mmartinic.muflon.dao.model.IEpisodeService;
import org.mmartinic.muflon.dao.model.IShowService;
import org.mmartinic.muflon.dao.model.ShowServiceException;
import org.mmartinic.muflon.model.Episode;
import org.mmartinic.muflon.model.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Updater {

	@Autowired
	private IEpisodeService episodeService;
	@Autowired
	private IShowService showService;

	/**
	 * Partial update, executes the following actions:<br>
	 * Removes all shows and its episodes from DB that are no longer in MyEpisodses<br>
	 * Adds all new shows and its episodes from MyEpisodes that are not in DB<br>
	 * Retrieves episodes from RSS feed and updates them in DB
	 */
	@SuppressWarnings("unchecked")
	public void partialUpdate() {
		MyEpisodesHTTPClient myEpisodesHTTPClient = new MyEpisodesHTTPClient();
		try {
			List<Show> currentShows = myEpisodesHTTPClient.getAllShows();
			List<Show> dbShows = showService.getAllShows();

			List<Show> newShows = (List<Show>) CollectionUtils.removeAll(currentShows, dbShows);
			List<Show> removedShows = (List<Show>) CollectionUtils.removeAll(dbShows, currentShows);

			for (Show show : removedShows) {
				// TODO set on delete cascade to delete all episodes for show
				List<Episode> episodes = episodeService.getAllEpisodesForShow(show.getId());
				for (Episode episode : episodes) {
					episodeService.deleteEpisode(episode.getEpisodeKey());
				}
				showService.deleteShow(show.getId());
			}
			for (Show show : newShows) {
				Long showId = showService.addShow(show);
				List<Episode> episodesForShow = myEpisodesHTTPClient.getAllEpisodesForShow(showId);
				for (Episode newEpisode : episodesForShow) {
					episodeService.addEpisode(newEpisode);
				}
				// pause for 5 seconds
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					System.out.println("ERROR: " + e.getMessage());
				}
			}

			RSSParser rssParser = new RSSParser();
			List<Episode> episodesFromRSSFeed = rssParser.getEpisodesFromRSSFeed();
			for (Episode episode : episodesFromRSSFeed) {
				Episode dbEpisode = episodeService.getEpisode(episode.getEpisodeKey());
				if (dbEpisode != null) {
					// episode exists in DB
					if (!dbEpisode.equals(episode)) {
						// episode data in DB is different from one in RSS feed - update it
						dbEpisode.setAirDate(episode.getAirDate());
						dbEpisode.setName(episode.getName());
						episodeService.updateEpisode(dbEpisode);
					}
				} else {
					// episode doesn't exist in DB - add it
					episodeService.addEpisode(episode);
				}
			}
		} catch (EpisodeServiceException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (ShowServiceException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (ClientProtocolException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (ParseException e) {
			System.out.println("ERROR: " + e.getMessage());
		} finally {
			// TODO check this
			myEpisodesHTTPClient.shutdown();
		}
	}

	/**
	 * Complete update, we start from scratch:<br>
	 * All shows and its episodes are deleted from DB<br>
	 * Current show list is retrieved from MyEpisodes and all shows and its episodes are added to DB
	 */
	public void completeUpdate() {
		MyEpisodesHTTPClient myEpisodesHTTPClient = new MyEpisodesHTTPClient();
		try {
			List<Show> dbShows = showService.getAllShows();
			for (Show show : dbShows) {
				// TODO set on delete cascade to delete all episodes for show
				List<Episode> episodes = episodeService.getAllEpisodesForShow(show.getId());
				for (Episode episode : episodes) {
					episodeService.deleteEpisode(episode.getEpisodeKey());
				}
				showService.deleteShow(show.getId());
			}

			List<Show> currentShows = myEpisodesHTTPClient.getAllShows();
			for (Show show : currentShows) {
				Long showId = showService.addShow(show);
				List<Episode> episodesForShow = myEpisodesHTTPClient.getAllEpisodesForShow(showId);
				for (Episode newEpisode : episodesForShow) {
					episodeService.addEpisode(newEpisode);
				}
				// pause for 5 seconds
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					System.out.println("ERROR: " + e.getMessage());
				}
			}
		} catch (ClientProtocolException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (ShowServiceException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (ParseException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (EpisodeServiceException e) {
			System.out.println("ERROR: " + e.getMessage());
		} finally {
			// TODO check this
			myEpisodesHTTPClient.shutdown();
		}
	}

	public void addOnlyFromRSSFeed() {
		try {
			RSSParser rssParser = new RSSParser();
			List<Episode> episodesFromRSSFeed = rssParser.getEpisodesFromRSSFeed();
			for (Episode episode : episodesFromRSSFeed) {
				Show dbShow = showService.getShow(episode.getEpisodeKey().getShow().getId());
				if (dbShow == null) {
					showService.addShow(episode.getEpisodeKey().getShow());
				}
				Episode dbEpisode = episodeService.getEpisode(episode.getEpisodeKey());
				if (dbEpisode != null) {
					// episode exists in DB
					if (!dbEpisode.equals(episode)) {
						// episode data in DB is different from one in RSS feed - update it
						dbEpisode.setAirDate(episode.getAirDate());
						dbEpisode.setName(episode.getName());
						episodeService.updateEpisode(dbEpisode);
					}
				} else {
					// episode doesn't exist in DB - add it
					episodeService.addEpisode(episode);
				}
			}
		} catch (EpisodeServiceException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (ShowServiceException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	public void listAllFromDb() {
		try {
			List<Show> shows = showService.getAllShows();
			for (Show show : shows) {
				List<Episode> episodes = episodeService.getAllEpisodesForShow(show.getId());
				for (Episode episode : episodes) {
					System.out.println(episode.toString());
				}
			}
		} catch (ShowServiceException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (EpisodeServiceException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
}
