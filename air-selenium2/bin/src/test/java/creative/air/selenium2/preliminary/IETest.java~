package creative.air.selenium2.preliminary;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;

@RunWith(BlockJUnit4ClassRunner.class)
public class IETest extends SeleniumTest {
	private WebDriver driver;

	@Before
	public void createDriver() throws IOException {
		System.setProperty("webdriver.ie.driver", getIEPath());
		driver = new InternetExplorerDriver();
	}

	@After
	public void quitDriver() {
		driver.quit();
	}

	@Test
	public void testGoogleSearch() {
		driver.get("http://www.baidu.com");
		WebElement searchBox = driver.findElement(By.id("kw"));
		searchBox.sendKeys("JSF2和RichFaces4使用指南");
		searchBox.submit();
		assertEquals("百度搜索_JSF2和RichFaces4使用指南", driver.getTitle());
	}
}
