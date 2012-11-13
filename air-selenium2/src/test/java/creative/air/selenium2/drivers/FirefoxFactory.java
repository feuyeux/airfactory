package creative.air.selenium2.drivers;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.thoughtworks.selenium.Selenium;

import creative.air.selenium2.load.PropertiesLoad;
import creative.air.selenium2.load.SeleniumConfig;

public class FirefoxFactory {
	static Selenium selenium;

	public static WebDriver createDriver() {
		System.setProperty("webdriver.firefox.driver", SeleniumConfig.getFirefoxPath());
		return new FirefoxDriver();
	}

	public static WebDriver createDriver(String baseUrl) {
		String specialPath = PropertiesLoad.getInstance().getFirefoxPath();
		if (specialPath != null && !specialPath.isEmpty()) {
			System.setProperty("webdriver.firefox.driver", specialPath);
		}
		FirefoxProfile profile = new FirefoxProfile();
		Proxy proxy = new Proxy();
		proxy.setProxyType(ProxyType.DIRECT);
		profile.setProxyPreferences(proxy);
		WebDriver driver = new FirefoxDriver(profile);

		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		selenium.windowMaximize();
		return driver;
	}
}
