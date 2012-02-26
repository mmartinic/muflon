package org.mmartinic.muflon.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;

public interface IGenericDao<T, PK extends Serializable> {

	PK create(T newInstance);

	void update(T transientObject);

	void createOrUpdate(T transientObject);

	void delete(T persistentObject);

	T findById(PK id);

	List<T> findAll();

	List<T> findByCriteria(Criteria criteria, String... joins);

	T findUniqueResultByCriteria(Criteria criteria, String... joins);

	Long getAllCount();

	Long getCountByCriteria(Criteria criteria);

	boolean exists(PK id);

	boolean existsByCriteria(Criteria criteria);

	List<T> findByQuery(String hql);

	List<T> findByQuery(String hql, Map<String, Object> parameters);

	T findUniqueResultByQuery(String hql);

	T findUniqueResultByQuery(String hql, Map<String, Object> parameters);

	int updateByQuery(String hql);

	int updateByQuery(String hql, Map<String, Object> parameters);

}
