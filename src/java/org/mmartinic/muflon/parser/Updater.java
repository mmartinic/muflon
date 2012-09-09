package org.mmartinic.muflon.parser;

import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmartinic.muflon.dao.model.IEpisodeService;
import org.mmartinic.muflon.dao.model.IShowService;
import org.mmartinic.muflon.model.Episode;
import org.mmartinic.muflon.model.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Updater {

    private static final Log log = LogFactory.getLog(Updater.class);

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
    public void partialUpdate(String myEpisodesUsername, String pwdmd5, String cookie) {
        log.info("Starting partialUpdate");
        MyEpisodesHTTPClient myEpisodesHTTPClient = new MyEpisodesHTTPClient(cookie);
        try {
            List<Show> currentShows = myEpisodesHTTPClient.getAllShows();
            List<Show> dbShows = showService.getAllShows();

            List<Show> newShows = ListUtils.removeAll(currentShows, dbShows);
            List<Show> removedShows = ListUtils.removeAll(dbShows, currentShows);

            log.info("partialUpdate - currentShows: count(" + currentShows.size() + ") :" + currentShows);
            log.info("partialUpdate - dbShows: count(" + dbShows.size() + ") :" + dbShows);
            log.info("partialUpdate - newShows: count(" + newShows.size() + ") :" + newShows);
            log.info("partialUpdate - removedShows: count(" + removedShows.size() + ") :" + removedShows);

            log.info("Starting partialUpdate - remove shows");
            for (Show show : removedShows) {
                // TODO set on delete cascade to delete all episodes for show
                List<Episode> episodes = episodeService.getAllEpisodesForShow(show.getId());
                for (Episode episode : episodes) {
                    episodeService.deleteEpisode(episode.getEpisodeKey());
                }
                showService.deleteShow(show.getId());
            }
            showService.flush();

            log.info("Starting partialUpdate - add new shows");
            for (Show show : newShows) {
                Long showId = showService.addShow(show);
                List<Episode> episodesForShow = myEpisodesHTTPClient.getAllEpisodesForShow(showId);
                for (Episode newEpisode : episodesForShow) {
                    episodeService.addEpisode(newEpisode);
                }
                // pause for 1 second
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                }
            }

            log.info("Starting partialUpdate - rss update");
            RSSParser rssParser = new RSSParser(myEpisodesUsername, pwdmd5);
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
                }
                else {
                    // episode doesn't exist in DB - add it
                    episodeService.addEpisode(episode);
                }
            }
        }
        catch (Exception e) {
            log.error(e, e);
        }
        finally {
            // TODO check this
            myEpisodesHTTPClient.shutdown();
        }
        log.info("Exiting partialUpdate");
    }

    /**
     * Complete update, we start from scratch:<br>
     * All shows and its episodes are deleted from DB<br>
     * Current show list is retrieved from MyEpisodes and all shows and its episodes are added to DB
     */
    public void completeUpdate(String cookie) {
        log.info("Starting completeUpdate");
        MyEpisodesHTTPClient myEpisodesHTTPClient = new MyEpisodesHTTPClient(cookie);
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
            showService.flush();

            List<Show> currentShows = myEpisodesHTTPClient.getAllShows();
            for (Show show : currentShows) {
                Long showId = showService.addShow(show);
                List<Episode> episodesForShow = myEpisodesHTTPClient.getAllEpisodesForShow(showId);
                for (Episode newEpisode : episodesForShow) {
                    episodeService.addEpisode(newEpisode);
                }
                // pause for 1 second
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                }
            }
        }
        catch (Exception e) {
            log.error(e, e);
        }
        finally {
            // TODO check this
            myEpisodesHTTPClient.shutdown();
        }
        log.info("Exiting completeUpdate");
    }

    public void addOnlyFromRSSFeed(String myEpisodesUsername, String pwdmd5) {
        log.info("Starting addOnlyFromRSSFeed");
        try {
            RSSParser rssParser = new RSSParser(myEpisodesUsername, pwdmd5);
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
                }
                else {
                    // episode doesn't exist in DB - add it
                    episodeService.addEpisode(episode);
                }
            }
        }
        catch (Exception e) {
            log.error(e, e);
        }
        log.info("Exiting addOnlyFromRSSFeed");
    }
}
