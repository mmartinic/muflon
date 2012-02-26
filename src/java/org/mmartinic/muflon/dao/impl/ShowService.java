package org.mmartinic.muflon.dao.impl;

import java.util.List;

import org.mmartinic.muflon.dao.base.AbstractDaoService;
import org.mmartinic.muflon.dao.model.IShowService;
import org.mmartinic.muflon.dao.model.ShowServiceException;
import org.mmartinic.muflon.model.Show;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShowService extends AbstractDaoService<Show, Long> implements IShowService {

	@Override
	public List<Show> getAllShows() throws ShowServiceException {
		try {
			return findAll();
		} catch (Exception e) {
			throw new ShowServiceException(e);
		}
	}

	@Override
	public Long getShowsCount() throws ShowServiceException {
		try {
			return getAllCount();
		} catch (Exception e) {
			throw new ShowServiceException(e);
		}
	}

	@Override
	public Show getShow(Long showId) throws ShowServiceException {
		try {
			return findById(showId);
		} catch (Exception e) {
			throw new ShowServiceException(e);
		}
	}

	@Override
	public Long addShow(Show show) throws ShowServiceException {
		try {
			return create(show);
		} catch (Exception e) {
			throw new ShowServiceException(e);
		}
	}

	@Override
	public void updateShow(Show show) throws ShowServiceException {
		try {
			update(show);
		} catch (Exception e) {
			throw new ShowServiceException(e);
		}
	}

	@Override
	public void deleteShow(Long showId) throws ShowServiceException {
		try {
			delete(getShow(showId));
		} catch (Exception e) {
			throw new ShowServiceException(e);
		}
	}

}
