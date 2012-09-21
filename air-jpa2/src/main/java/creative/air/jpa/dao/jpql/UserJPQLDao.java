package creative.air.jpa.dao.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import creative.air.jpa.AirJPAFactory;
import creative.air.jpa.model.User;

public class UserJPQLDao {
	public List<User> retrieveUsers() {
		EntityManager em = AirJPAFactory.getInstance().getEntityManager();
		List<User> result = em.createQuery("SELECT user FROM User user ORDER BY user.userName", User.class).getResultList();
		return result;
	}

	public User retrieveUser(int userId) {
		EntityManager em = AirJPAFactory.getInstance().getEntityManager();
		TypedQuery<User> q = em.createQuery("SELECT user FROM User user WHERE user.userId = :uId", User.class);
		// force read from database
		q.setHint("eclipselink.cache-usage", "DoNotCheckCache");
		q.setParameter("uId", userId);
		User user = q.getSingleResult();
		return user;
	}
}
