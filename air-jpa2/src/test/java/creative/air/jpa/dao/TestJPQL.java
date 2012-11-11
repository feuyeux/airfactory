package creative.air.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import creative.air.jpa.AirJPAFactory;
import creative.air.jpa.dao.jpql.UserJPQLDao;
import creative.air.jpa.model.User;

public class TestJPQL {
	EntityManager em = AirJPAFactory.getInstance("air-jpa2-h2").getEntityManager();
	UserJPQLDao dao = new UserJPQLDao(em);

	@Test
	public void test() {
		User user = dao.retrieveUser(1);
		System.out.println(user);
		Assert.assertNotNull(user);
		List<User> users = dao.retrieveUsers();
		Assert.assertNotNull(users);
		Assert.assertEquals(2, users.size());
	}

	@After
	public void tearDown() {
		em.clear();
	}
}
