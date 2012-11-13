package creative.air.selenium2.normal;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import creative.air.selenium2.drivers.ChromeFactory;
import creative.air.selenium2.drivers.FirefoxFactory;
import creative.air.selenium2.drivers.IEFactory;
import creative.air.selenium2.load.PropertiesLoad;

@RunWith(Parameterized.class)
public class BrowsersTest {
	private Logger logger = Logger.getLogger(getClass());
	private String explorer;
	WebDriver driver = null;
	static PropertiesLoad loader = PropertiesLoad.getInstance();
	final String url = "http://www.baidu.com";

	public BrowsersTest(String explorer) {
		this.explorer = explorer;
	}

	@Parameters
	public static Collection<Object[]> multipleValues() {
		String[] webBrowserArray = loader.getWebBrowsers();
		Object[][] testBrowser = new Object[webBrowserArray.length][1];
		for (int i = 0; i < webBrowserArray.length; i++) {
			testBrowser[i][0] = webBrowserArray[i];
		}
		return Arrays.asList(testBrowser);
	}

	@Before
	public void before() throws IOException {
		driver = createWebBrowser(explorer);
		logger.debug("Open web browser : " + explorer);
	}

	@After
	public void after() {
		driver.quit();
		logger.debug("Close web browser =" + explorer);
	}

	WebDriver createWebBrowser(String webBrowser) throws IOException {
		if (webBrowser.equals("ie")) {
			Properties props = System.getProperties();
			String osName = props.getProperty("os.name");
			if (osName.contains("Linux")) {
			} else {
				driver = IEFactory.createDriver(url);
			}
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} else if (webBrowser.equals("firefox")) {
			driver = FirefoxFactory.createDriver(url);
		} else if (webBrowser.equals("chrome")) {
			driver = ChromeFactory.createDriver(url);
		} else {
			logger.debug("Does not support the type of web browser");
		}
		return driver;
	}

	@Test()
	public void testSearch() {
		driver.get(url);
		WebElement searchBox = driver.findElement(By.id("kw"));
		searchBox.sendKeys("JSF2和RichFaces4使用指南");
		searchBox.submit();
		assertEquals("百度搜索_JSF2和RichFaces4使用指南", driver.getTitle());
	}
}