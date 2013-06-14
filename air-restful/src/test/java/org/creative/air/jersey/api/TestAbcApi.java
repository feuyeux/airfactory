package org.creative.air.jersey.api;

import java.util.List;

import org.apache.log4j.Logger;
import org.creative.air.jersey.model.AbcDto;
import org.creative.air.jersey.model.AbcReturnDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 05/08/2012
 * @since  0.0.1
 * @version 0.0.1
 */
@Ignore
public class TestAbcApi {
	private final Logger logger = Logger.getLogger(this.getClass());
	private ClassPathXmlApplicationContext appContext;
	private AbcApi abcApi;

	@Before
	public void tearUp() {
		final String prefix = "classpath:";
		final String file1 = prefix + "applicationContext.xml";
		final String file2 = prefix + "applicationContext-jpa.xml";
		final String[] contextFiles = { file1, file2 };
		appContext = new ClassPathXmlApplicationContext(contextFiles);
		final String[] beanNames = appContext.getBeanDefinitionNames();
		for (final String beanname : beanNames) {
			logger.debug(beanname);
		}
		abcApi = (AbcApi) appContext.getBean("abcApi");
	}

	@After
	public void tearDown() throws Exception {
		appContext.close();
		abcApi = null;
	}

	@Test(timeout = 5000)
	public void testPost() throws Exception {
		final AbcDto abcDto = new AbcDto();
		abcDto.setName("api_name");
		abcDto.setValue("api_123");
		final AbcReturnDto rdto = abcApi.addAbc(abcDto);
		Assert.assertNotNull("return must not be null", rdto.getElement());
	}

	public void testGetAndPut() throws Exception {
		final AbcReturnDto abcDto = abcApi.get(Integer.valueOf(1));
		Assert.assertNotNull("return must not be null", abcDto.getElement());
	}

	@Test
	public void testGetAll() throws Exception {
		final AbcReturnDto rdto = abcApi.getAll();
		final List<AbcDto> list = rdto.getElementList();
		for (final AbcDto abc : list) {
			Assert.assertNotNull("return must not be null", abc);
		}
	}
}
