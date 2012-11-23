package creative.air.jersey.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.util.Assert;

/**
 * 
 * @author
 * @since  0.0.1
 * @version 0.0.1
 */
@SuppressWarnings("unchecked")
public class GenericDao<T, ID extends Serializable> extends JpaDaoSupport {
	private Class<T> entityClass;

	public GenericDao() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public T findById(ID id) throws Exception {
		Assert.notNull(id);
		return (T) getJpaTemplate().find(entityClass, id);
	}

	public List<T> findAll() {
		final String queryString = "select model from " + entityClass.getSimpleName() + " model";
		return (List<T>) getJpaTemplate().find(queryString);
	}

	public List<T> findByProperty(String propertyName, final Object value) {
		final String queryString = "select model from " + entityClass.getSimpleName() + " model where model." + propertyName + "= ?";
		return findByQueryString(queryString, value);
	}

	@SuppressWarnings("rawtypes")
	public List<T> findByQueryString(final String queryString, final Object... values) {
		Assert.hasText(queryString);
		return getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				return (List<T>) createQuery(em, queryString, values).getResultList();
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public List<Object> findObjectsByQueryString(final String queryString, final Object... values) {
		Assert.hasText(queryString);
		return getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				return (List<Object[]>) createQuery(em, queryString, values).getResultList();
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public List<Object> findByNativeQueryString(final String queryString, final Object... values) {
		return getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				return (List<Object[]>) createNativeQuery(em, queryString, values).getResultList();
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public <K> List<K> findByNativeQueryString(final String queryString, final Class<K> className, final Object... values) {
		return getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				return (List<K>) createNativeQuery(em, queryString, className, values).getResultList();
			}
		});
	}

	public Query createQuery(EntityManager em, String queryString, Object... values) {
		Query query = em.createQuery(queryString);
		if (values != null)
			for (int i = 0; i < values.length; i++)
				query.setParameter(i + 1, values[i]);

		return query;
	}

	public Query createNativeQuery(EntityManager em, String queryString, Object... values) {

		Query query = em.createNativeQuery(queryString);
		if (values != null)
			for (int i = 0; i < values.length; i++)
				query.setParameter(i + 1, values[i]);
		return query;
	}

	public <K> Query createNativeQuery(EntityManager em, String queryString, Class<K> className, Object... values) {
		Query query = em.createNativeQuery(queryString, className);
		if (values != null)
			for (int i = 0; i < values.length; i++)
				query.setParameter(i + 1, values[i]);

		return query;
	}

	public void save(T entity) {
		getJpaTemplate().persist(entity);
	}

	public void remove(T entity) {
		getJpaTemplate().remove(entity);
		// Note: you must synchronized the entity before removing it!
	}

	public T update(T entity) {
		T t = getJpaTemplate().merge(entity);
		getJpaTemplate().flush();
		return t;
	}

	@SuppressWarnings("unused")
	private static String removeSelect(String queryString) {
		Assert.hasText(queryString);
		int beginPos = queryString.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " queryString : " + queryString + " must has a keyword 'from'");
		return queryString.substring(beginPos);
	}

	@SuppressWarnings("unused")
	private static String removeOrders(String queryString) {
		Assert.hasText(queryString);
		Pattern pattern = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(queryString);
		StringBuffer stringBuffer = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(stringBuffer, "");
		}
		matcher.appendTail(stringBuffer);
		return stringBuffer.toString();
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
}
