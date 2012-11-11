package creative.air.jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import creative.air.jpa.AirJPAFactory;
import creative.air.jpa.dao.criteria.UserCriteriaDao;
import creative.air.jpa.model.Role;
import creative.air.jpa.model.User;
/**
 * @author feuyeux@gmail.com
 * 2012-11-11
 */
public class TestCriteria {
	//	EntityManager em = AirJPAFactory.getInstance("air-jpa2-mysql").getEntityManager();
	EntityManager em = AirJPAFactory.getInstance("air-jpa2-h2").getEntityManager();
	UserCriteriaDao dao = new UserCriteriaDao(em);
	Role role1 = new Role("Admin");
	Role role2 = new Role("Guest");
	Role role3 = new Role("Git");
	User user1 = new User("Eric");
	User user2 = new User("Peter");

	@Before
	public void tearUp() {
		em.getTransaction().begin();
		role1 = em.merge(role1);
		role2 = em.merge(role2);
		role3 = em.merge(role3);

		List<Role> admin = new ArrayList<Role>();
		admin.add(role1);
		admin.add(role3);
		List<Role> guest = new ArrayList<Role>();
		guest.add(role2);

		user1 = em.merge(user1);
		user1.setRole(admin);
		user1 = em.merge(user1);

		user2 = em.merge(user2);
		user2.setRole(guest);
		user2 = em.merge(user2);
		em.getTransaction().commit();
	}

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
