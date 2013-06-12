package creative.air.jersey.api;

import creative.air.jersey.model.AbcDto;
import creative.air.jersey.model.AbcReturnDto;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
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
		String prefix = "classpath:";
		String file1 = prefix + "applicationContext.xml";
		String file2 = prefix + "applicationContext-jpa.xml";
		String[] contextFiles = { file1, file2 };
		appContext = new ClassPathXmlApplicationContext(contextFiles);
		String[] beanNames = appContext.getBeanDefinitionNames();
		for (String beanname : beanNames) {
			logger.debug(beanname);
		}
		abcApi = (AbcApi) appContext.getBean("abcApi");
	}

	@After
	public void tearDown() throws Exception {
		appContext.close();
		abcApi = null;
	}

	@Test(timeout=5000)
	public void testPost() throws Exception {
		AbcDto abcDto = new AbcDto();
		abcDto.setName("api_name");
		abcDto.setValue("api_123");
		AbcReturnDto rdto = abcApi.addAbc(abcDto);
		assertNotNull("return must not be null", rdto.getElement());
	}

	public void testGetAndPut() throws Exception {
		AbcReturnDto abcDto = abcApi.get(Integer.valueOf(1));
		assertNotNull("return must not be null", abcDto.getElement());
	}

	@Test
	public void testGetAll() throws Exception {
		AbcReturnDto rdto = abcApi.getAll();
		List<AbcDto> list = rdto.getElementList();
		for (AbcDto abc : list) {
			assertNotNull("return must not be null", abc);
		}
	}
}
