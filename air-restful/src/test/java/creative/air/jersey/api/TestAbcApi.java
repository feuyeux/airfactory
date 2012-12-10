package creative.air.jersey.api;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import creative.air.jersey.model.AbcDto;
import creative.air.jersey.model.AbcReturnDto;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 05/08/2012
 * @since  0.0.1
 * @version 0.0.1
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-persistence.xml",
		"classpath:applicationContext-persistence.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAbcApi {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	protected AbcApi abcApi;

	@Test
	public void testGetAll() throws Exception {
		AbcReturnDto rdto = abcApi.getAll1();
		List<AbcDto> list = rdto.getElementList();
		for (AbcDto abc : list) {
			logger.debug(abc);
			assertNotNull("return must not be null", abc);
		}
	}
}
