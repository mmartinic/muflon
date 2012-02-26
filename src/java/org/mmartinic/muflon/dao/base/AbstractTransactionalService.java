package org.mmartinic.muflon.dao.base;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AbstractTransactionalService {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	protected <T> T safelyLoadObject(Class<T> clazz, Serializable primaryKey) {
		if (primaryKey == null) {
			return null;
		}
		return (T) getSession().load(clazz, primaryKey);
	}

}