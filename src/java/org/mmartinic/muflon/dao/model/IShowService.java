package org.mmartinic.muflon.dao.model;

import java.util.List;

import org.mmartinic.muflon.model.Show;

public interface IShowService {

	List<Show> getAllShows() throws ShowServiceException;

	Long getShowsCount() throws ShowServiceException;

	Show getShow(Long showId) throws ShowServiceException;

	Long addShow(Show show) throws ShowServiceException;

	void updateShow(Show show) throws ShowServiceException;

	void deleteShow(Long showId) throws ShowServiceException;
}
