package creative.air.selenium2.preliminary;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import creative.air.selenium2.drivers.ChromeFactory;
import creative.air.selenium2.drivers.FirefoxFactory;
import creative.air.selenium2.drivers.IEFactory;

@RunWith(BlockJUnit4ClassRunner.class)
public class BrowsersTest {
	@Test
	public void testGoogleSearch() {
		WebDriver driver = null;
		try {
			driver = ChromeFactory.createDriver();
			testSearch(driver);

			driver = FirefoxFactory.createDriver();
			testSearch(driver);

			Properties props = System.getProperties();
			String osName = props.getProperty("os.name");
			if (osName.contains("Linux")) {
			} else {
				driver = IEFactory.createDriver();
				testSearch(driver);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}

	private void testSearch(WebDriver driver) {
		driver.get("http://www.baidu.com");
		WebElement searchBox = driver.findElement(By.id("kw"));
		searchBox.sendKeys("JSF2和RichFaces4使用指南");
		searchBox.submit();
		assertEquals("百度搜索_JSF2和RichFaces4使用指南", driver.getTitle());
	}
}
