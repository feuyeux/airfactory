package creative.air.jersey.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import creative.air.jersey.model.AbcDto;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 10/12/2012
 * @since  0.0.1
 * @version 0.0.1
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-persistence.xml",
		"classpath:applicationContext-persistence.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAbcDao {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	protected AbcDao abcDao;

	@Test
	public void testFindAll() {
		List<AbcDto> abcs = abcDao.findAll1();
		for (AbcDto abc : abcs) {
			assertNotNull("entity could not be null", abc);
			logger.debug(abc);
		}
	}

	@Test
	public void testCreateAll() {
		List<AbcDto> abcs = abcDao.findAll1();
		for (AbcDto abc : abcs) {
			assertNotNull("entity could not be null", abc);
			logger.debug(abc);
		}

		int[] count = abcDao.insertAll2(abcs);

		for (int i : count) {
			logger.debug(i);
		}
	}
}
