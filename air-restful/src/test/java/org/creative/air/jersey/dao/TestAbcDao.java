package org.creative.air.jersey.dao;

import java.util.List;

import org.creative.air.jersey.model.AbcEntity;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 05/08/2012
 * @since  0.0.1
 * @version 0.0.1
 */
@Ignore
public class TestAbcDao {
	private AbcDao abcDao;
	private ClassPathXmlApplicationContext appContext;

	@Before
	public void tearUp() {
		final String prefix = "classpath:";
		final String file1 = prefix + "applicationContext.xml";
		final String file2 = prefix + "applicationContext-jpa.xml";
		final String[] contextFiles = { file1, file2 };
		appContext = new ClassPathXmlApplicationContext(contextFiles);
		abcDao = (AbcDao) appContext.getBean("abcDao");
	}

	@After
	public void tearDown() throws Exception {
		appContext.close();
		abcDao = null;
	}

	@Test
	@Rollback(false)
	public void testSave() {
		final AbcEntity abc = new AbcEntity();
		abc.setName("employee");
		abc.setValue("hanl");
		abcDao.save(abc);
	}

	@Test
	public void testFindAll() {
		final List<AbcEntity> abcs = abcDao.findAll();
		for (final AbcEntity abc : abcs) {
			Assert.assertNotNull("entity could not be null", abc);
			System.out.println(abc);
		}
	}

}
