package org.mmartinic.muflon.dao.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public abstract class AbstractDaoService<T, PK extends Serializable> extends AbstractTransactionalService implements IGenericDao<T, PK> {

    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractDaoService() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private Class<T> getPersistentClass() {
        return persistentClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PK create(T newInstance) {
        return (PK) getSession().save(newInstance);
    }

    @Override
    public void update(T transientObject) {
        getSession().update(transientObject);
    }

    @Override
    public void createOrUpdate(T transientObject) {
        getSession().saveOrUpdate(transientObject);
    }

    @Override
    public void delete(T persistentObject) {
        getSession().delete(persistentObject);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(PK id) {
        return (T) getSession().get(getPersistentClass(), id);
    }

    @Override
    public List<T> findAll() {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        return findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByCriteria(Criteria criteria, String... joins) {
        if (joins != null && joins.length > 0) {
            for (String s : joins) {
                criteria.setFetchMode(s, FetchMode.JOIN);
            }
        }
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findUniqueResultByCriteria(Criteria criteria, String... joins) {
        if (joins != null && joins.length > 0) {
            for (String s : joins) {
                criteria.setFetchMode(s, FetchMode.JOIN);
            }
        }
        return (T) criteria.uniqueResult();
    }

    @Override
    public Long getAllCount() {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.setProjection(Projections.rowCount());
        return ((Long) criteria.list().get(0)).longValue();
    }

    @Override
    public Long getCountByCriteria(Criteria criteria) {
        criteria.setProjection(Projections.rowCount());
        return ((Long) criteria.uniqueResult()).longValue();
    }

    @Override
    public boolean exists(PK id) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.idEq(id));
        return existsByCriteria(criteria);
    }

    @Override
    public boolean existsByCriteria(Criteria criteria) {
        criteria.setProjection(Projections.rowCount());
        long cnt = ((Long) criteria.uniqueResult()).longValue();
        return (cnt != 0);
    }

    @Override
    public List<T> findByQuery(String hql) {
        return findByQuery(hql, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByQuery(String hql, Map<String, Object> parameters) {
        Query query = getSession().createQuery(hql);
        if (parameters != null && !parameters.isEmpty()) {
            for (Entry<String, Object> entry : parameters.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Collection) {
                    query.setParameterList(entry.getKey(), (Collection<?>) value);
                } else if (value instanceof Object[]) {
                    query.setParameterList(entry.getKey(), (Object[]) value);
                } else {
                    query.setParameter(entry.getKey(), value);
                }
            }
        }
        return query.list();
    }

    @Override
    public T findUniqueResultByQuery(String hql) {
        return findUniqueResultByQuery(hql, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findUniqueResultByQuery(String hql, Map<String, Object> parameters) {
        Query query = getSession().createQuery(hql);
        if (parameters != null && !parameters.isEmpty()) {
            for (Entry<String, Object> entry : parameters.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Collection) {
                    query.setParameterList(entry.getKey(), (Collection<?>) value);
                } else if (value instanceof Object[]) {
                    query.setParameterList(entry.getKey(), (Object[]) value);
                } else {
                    query.setParameter(entry.getKey(), value);
                }
            }
        }
        return (T) query.uniqueResult();
    }

    @Override
    public int updateByQuery(String hql) {
        return updateByQuery(hql, null);
    }

    @Override
    public int updateByQuery(String hql, Map<String, Object> parameters) {
        Query query = getSession().createQuery(hql);
        if (parameters != null && !parameters.isEmpty()) {
            for (Entry<String, Object> entry : parameters.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Collection) {
                    query.setParameterList(entry.getKey(), (Collection<?>) value);
                } else if (value instanceof Object[]) {
                    query.setParameterList(entry.getKey(), (Object[]) value);
                } else {
                    query.setParameter(entry.getKey(), value);
                }
            }
        }
        return query.executeUpdate();
    }

}
