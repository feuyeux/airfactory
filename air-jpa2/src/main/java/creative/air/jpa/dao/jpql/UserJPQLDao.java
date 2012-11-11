package creative.air.jpa.dao.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import creative.air.jpa.model.User;
/**
 * @author feuyeux@gmail.com
 * 2012-11-11
 */
public class UserJPQLDao {
	private EntityManager em;

	public UserJPQLDao(EntityManager em) {
		this.em = em;
	}

	public List<User> retrieveUsers() {
		List<User> result = em.createQuery("SELECT user FROM User user ORDER BY user.userName", User.class).getResultList();
		return result;
	}

	public User retrieveUser(int userId) {
		TypedQuery<User> q = em.createQuery("SELECT user FROM User user WHERE user.userId = :uId", User.class);
		// force read from database
		q.setHint("eclipselink.cache-usage", "DoNotCheckCache");
		q.setParameter("uId", userId);
		User user = q.getSingleResult();
		return user;
	}
}
