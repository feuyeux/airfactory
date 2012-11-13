package creative.air.selenium2.drivers;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.thoughtworks.selenium.Selenium;

import creative.air.selenium2.load.PropertiesLoad;
import creative.air.selenium2.load.SeleniumConfig;

public class IEFactory {
	static Selenium selenium;

	public static WebDriver createDriver() throws IOException {
		System.setProperty("webdriver.ie.driver", SeleniumConfig.getIEPath());
		return new InternetExplorerDriver();
	}

	public static WebDriver createDriver(String baseUrl) {
		String specialPath = PropertiesLoad.getInstance().getIEPath();
		if (specialPath != null && !specialPath.isEmpty()) {
			System.setProperty("webdriver.ie.driver", specialPath);
		}
		WebDriver driver = new InternetExplorerDriver();
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		selenium.windowMaximize();
		return driver;
	}
}
