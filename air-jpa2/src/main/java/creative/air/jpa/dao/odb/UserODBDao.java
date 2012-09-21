package creative.air.jpa.dao.odb;

import javax.persistence.EntityManager;

import creative.air.jpa.AirJPAFactory;
import creative.air.jpa.model.User;

public class UserODBDao {
	public User retrieveUser(int userId) {
		EntityManager em = AirJPAFactory.getInstance().getEntityManager();
		User user = em.find(User.class, userId);
		return user;
	}
}
