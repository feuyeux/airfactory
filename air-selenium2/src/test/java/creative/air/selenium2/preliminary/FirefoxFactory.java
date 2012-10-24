package creative.air.selenium2.preliminary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxFactory   {
	public static WebDriver createDriver() {
		System.setProperty("webdriver.firefox.driver",SeleniumConfig.getFirefoxPath());
		return new FirefoxDriver();
	}
}
