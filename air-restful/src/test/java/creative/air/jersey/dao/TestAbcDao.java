package creative.air.jersey.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;

import creative.air.jersey.model.AbcEntity;
/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 05/08/2012
 * @since  0.0.1
 * @version 0.0.1
 */
public class TestAbcDao {
	protected AbcDao abcDao;
	protected ClassPathXmlApplicationContext appContext;

	@Before
	public void tearUp() {
		String prefix = "classpath:";
		String file1 = prefix + "applicationContext.xml";
		String file2 = prefix + "applicationContext-persistence.xml";
		String[] contextFiles = { file1, file2 };
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
		AbcEntity abc = new AbcEntity();
		abc.setName("employee");
		abc.setValue("hanl");
		abcDao.save(abc);
	}

	@Test
	public void testFindAll() {
		List<AbcEntity> abcs = abcDao.findAll();
		for (AbcEntity abc : abcs) {
			assertNotNull("entity could not be null", abc);
			System.out.println(abc);
		}
	}

}
