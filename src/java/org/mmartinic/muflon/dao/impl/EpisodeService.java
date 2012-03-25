package org.mmartinic.muflon.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.mmartinic.muflon.dao.base.AbstractDaoService;
import org.mmartinic.muflon.dao.model.EpisodeServiceException;
import org.mmartinic.muflon.dao.model.IEpisodeService;
import org.mmartinic.muflon.model.Episode;
import org.mmartinic.muflon.model.Episode.EpisodeKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EpisodeService extends AbstractDaoService<Episode, EpisodeKey> implements IEpisodeService {

    @Override
    public List<Episode> getAllEpisodes() throws EpisodeServiceException {
        try {
            return findAll();
        } catch (Exception e) {
            throw new EpisodeServiceException(e);
        }
    }

    @Override
    public Long getEpisodesCount() throws EpisodeServiceException {
        try {
            return getAllCount();
        } catch (Exception e) {
            throw new EpisodeServiceException(e);
        }
    }

    @Override
    public List<Episode> getAllEpisodesForShow(Long showId) throws EpisodeServiceException {
        try {
            Criteria criteria = getSession().createCriteria(Episode.class);
            criteria.add(Restrictions.eq("episodeKey.show.id", showId));
            return findByCriteria(criteria);
        } catch (Exception e) {
            throw new EpisodeServiceException(e);
        }
    }

    @Override
    public Long getEpisodesForShowCount(Long showId) throws EpisodeServiceException {
        try {
            Criteria criteria = getSession().createCriteria(Episode.class);
            criteria.add(Restrictions.eq("episodeKey.show.id", showId));
            return getCountByCriteria(criteria);
        } catch (Exception e) {
            throw new EpisodeServiceException(e);
        }
    }

    @Override
    public Episode getEpisode(EpisodeKey episodeKey) throws EpisodeServiceException {
        try {
            return findById(episodeKey);
        } catch (Exception e) {
            throw new EpisodeServiceException(e);
        }
    }

    @Override
    public EpisodeKey addEpisode(Episode episode) throws EpisodeServiceException {
        try {
            return create(episode);
        } catch (Exception e) {
            throw new EpisodeServiceException(e);
        }
    }

    @Override
    public void updateEpisode(Episode episode) throws EpisodeServiceException {
        try {
            update(episode);
        } catch (Exception e) {
            throw new EpisodeServiceException(e);
        }
    }

    @Override
    public void deleteEpisode(EpisodeKey episodeKey) throws EpisodeServiceException {
        try {
            delete(getEpisode(episodeKey));
        } catch (Exception e) {
            throw new EpisodeServiceException(e);
        }
    }
}