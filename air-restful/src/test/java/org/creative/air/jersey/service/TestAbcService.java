package org.creative.air.jersey.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.creative.air.jersey.model.AbcDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Eric Han feuyeux@gmail.com
 *         05/08/2012
 * @version 0.0.1
 * @since 0.0.1
 */
@Ignore
@Component
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-jpa.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAbcService {
	private final Logger _log = Logger.getLogger(this.getClass());
	@Autowired
	AbcService abcService;
	private final SimpleDateFormat f = new SimpleDateFormat("yyMMdd.HHMMSS");

	@Test
	public void init() {
		System.out.println(this.getClass().getResource("/").getPath());
		System.out.println(System.getProperty("user.dir"));
		final String meta_inf_config = "classpath:applicationContext.xml";
		final ApplicationContext appContext = new ClassPathXmlApplicationContext(meta_inf_config);
		final String[] beanNames = appContext.getBeanDefinitionNames();
		for (final String beanname : beanNames) {
			_log.debug(beanname);
		}
	}

	@Test
	public void testGetAll() {
		final List<AbcDto> abcs = abcService.getAll();
		for (final AbcDto abc : abcs) {
			org.junit.Assert.assertNotNull("entity could not be null", abc);
		}
	}

	@Test
	public void testSaveAbc() {
		final AbcDto abcDto = new AbcDto();
		abcDto.setName("service_test");
		final Calendar calendar = Calendar.getInstance();
		final Date d = calendar.getTime();
		abcDto.setValue(f.format(d));
		abcService.saveABC(abcDto);
	}
}
