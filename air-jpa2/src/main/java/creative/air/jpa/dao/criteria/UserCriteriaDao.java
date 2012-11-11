package creative.air.jpa.dao.criteria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import creative.air.jpa.model.User;
/**
 * @author feuyeux@gmail.com
 * 2012-11-11
 */
public class UserCriteriaDao {
	private EntityManager em;

	public UserCriteriaDao(EntityManager em) {
		this.em = em;
	}

	public List<User> retrieveUsers() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> c = cb.createQuery(User.class);
		Root<User> emp = c.from(User.class);
		c.select(emp);

		TypedQuery<User> q = em.createQuery(c);
		List<User> result = q.getResultList();
		return result;
	}

	public User retrieveUser(int userId) {
		TypedQuery<User> q = em.createQuery("SELECT user FROM User user WHERE user.userId = :uId", User.class);
		// force read from database
		q.setHint("eclipselink.cache-usage", "DoNotCheckCache");
		q.setParameter("uId", userId);

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> c = cb.createQuery(User.class);
		Root<User> emp = c.from(User.class);
		c.select(emp).where(cb.equal(emp.get("userId"), userId));

		User user = q.getSingleResult();
		return user;
	}
}
