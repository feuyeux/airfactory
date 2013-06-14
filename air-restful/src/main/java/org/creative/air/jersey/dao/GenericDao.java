package org.creative.air.jersey.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.util.Assert;

/**
 * JPA DAO Tier Bean
 * 
 * @author feuyeux@gmail.com
 * 05/08/2012
 * @version 0.1.0
 * @since 0.0.1
 */
public class GenericDao<T, ID extends Serializable> {
	private Class<T> entityClass;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public GenericDao() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		Type[] types = parameterizedType.getActualTypeArguments();
		this.entityClass = (Class<T>) types[0];
	}

	public T findById(ID id) {
		Assert.notNull(id);
		return entityManager.find(entityClass, id);
	}

	public List<T> findAll() {
		return findAll(false, -1, -1);
	}

	public List<T> findAll(boolean isPaging, int firstResult, int maxResults) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);
		Root<T> kmsArticle = cq.from(entityClass);
		cq.select(kmsArticle);
		TypedQuery<T> q = entityManager.createQuery(cq);
		if (isPaging) {
			q.setMaxResults(maxResults);
			q.setFirstResult(firstResult);
		}
		return q.getResultList();
	}

	Query createNativeQuery(String queryString, Object... values) {
		final Query query = entityManager.createNativeQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
		return query;
	}

	<K> Query createNativeQuery(String queryString, Class<K> className, Object... values) {
		final Query query = entityManager.createNativeQuery(queryString, className);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
		return query;
	}

	List<T> findByProperty(String propertyName, final Object value) {
		final String queryString = "select model from " + entityClass.getSimpleName() + " model where model." + propertyName + "= ?";
		return findByQueryString(queryString, value);
	}

	@SuppressWarnings("unchecked")
	List<T> findByQueryString(final String queryString, final Object... values) {
		Assert.hasText(queryString);
		return createNativeQuery(queryString, entityClass, values).getResultList();
	}

	public void save(T entity) {
		entityManager.persist(entity);
	}

	public void remove(T entity) {
		entityManager.remove(entity);
	}

	public T update(T entity) {
		final T t = entityManager.merge(entity);
		entityManager.flush();
		return t;
	}
}
