package creative.air.selenium2.preliminary;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

@RunWith(BlockJUnit4ClassRunner.class)
public class ChromeTest extends SeleniumTest {

	private ChromeDriverService service;
	private WebDriver driver;

	@Before
	public void createDriver() throws IOException {
		service = new ChromeDriverService.Builder().usingDriverExecutable(new File(getChromePath())).usingAnyFreePort().build();
		service.start();
		driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
	}

	@After
	public void quitDriver() {
		driver.quit();
		service.stop();
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
