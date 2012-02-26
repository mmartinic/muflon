package org.mmartinic.muflon.dao.model;

import java.util.List;

import org.mmartinic.muflon.model.Episode;
import org.mmartinic.muflon.model.Episode.EpisodeKey;

public interface IEpisodeService {

	List<Episode> getAllEpisodes() throws EpisodeServiceException;

	Long getEpisodesCount() throws EpisodeServiceException;

	List<Episode> getAllEpisodesForShow(Long showId) throws EpisodeServiceException;

	Long getEpisodesForShowCount(Long showId) throws EpisodeServiceException;

	Episode getEpisode(EpisodeKey episodeKey) throws EpisodeServiceException;

	EpisodeKey addEpisode(Episode episode) throws EpisodeServiceException;

	void updateEpisode(Episode episode) throws EpisodeServiceException;

	void deleteEpisode(EpisodeKey episodeKey) throws EpisodeServiceException;

}