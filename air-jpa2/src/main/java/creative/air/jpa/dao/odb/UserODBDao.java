package creative.air.jpa.dao.odb;

import javax.persistence.EntityManager;

import creative.air.jpa.model.User;
/**
 * @author feuyeux@gmail.com
 * 2012-11-11
 */
public class UserODBDao {
	private EntityManager em;

	public UserODBDao(EntityManager em) {
		this.em = em;
	}

	public User retrieveUser(int userId) {
		User user = em.find(User.class, userId);
		return user;
	}
}
