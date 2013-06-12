package creative.air.jersey.service;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import creative.air.jersey.model.AbcDto;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 05/08/2012
 * @since  0.0.1
 * @version 0.0.1
 */
@Ignore
@Component
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-jpa.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAbcService {
	private Logger _log = Logger.getLogger(this.getClass());
	@Autowired
	AbcService abcService;
	SimpleDateFormat f = new SimpleDateFormat("yyMMdd.HHMMSS");

	@Test
	public void init() {
		System.out.println(this.getClass().getResource("/").getPath());
		System.out.println(System.getProperty("user.dir"));
		String meta_inf_config = "classpath:applicationContext.xml";
		ApplicationContext appContext = new ClassPathXmlApplicationContext(meta_inf_config);
		String[] beanNames = appContext.getBeanDefinitionNames();
		for (String beanname : beanNames) {
			_log.debug(beanname);
		}
	}

	@Test
	public void testGetAll() {
		List<AbcDto> abcs = abcService.getAll();
		for (AbcDto abc : abcs) {
			org.junit.Assert.assertNotNull("entity could not be null", abc);
		}
	}

	@Test
	public void testSaveAbc() {
		AbcDto abcDto = new AbcDto();
		abcDto.setName("service_test");
		Calendar calendar = Calendar.getInstance();
		Date d = calendar.getTime();
		abcDto.setValue(f.format(d));
		abcService.saveABC(abcDto);
	}
}
