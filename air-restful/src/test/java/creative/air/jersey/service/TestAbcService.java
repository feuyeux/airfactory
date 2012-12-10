package creative.air.jersey.service;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
public class TestAbcService {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	AbcService abcService;
	SimpleDateFormat f = new SimpleDateFormat("yyMMdd.HHMMSS");

	@Test
	public void testtransferAll() {
		logger.debug("transferAll:");
		abcService.transferAll();
	}
}
