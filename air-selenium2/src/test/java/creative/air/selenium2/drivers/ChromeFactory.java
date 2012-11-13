package creative.air.selenium2.drivers;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.thoughtworks.selenium.Selenium;

import creative.air.selenium2.load.PropertiesLoad;
import creative.air.selenium2.load.SeleniumConfig;

public class ChromeFactory {
	static Selenium selenium;

	public static WebDriver createDriver() throws IOException {
		ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File(SeleniumConfig.getChromePath())).usingAnyFreePort()
				.build();
		service.start();
		return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
	}

	public static WebDriver createDriver(String baseUrl) {
		String specialPath = PropertiesLoad.getInstance().getChromePath();
		if (specialPath != null && !specialPath.isEmpty()) {
			System.setProperty("webdriver.chrome.driver", specialPath);
		}
		WebDriver driver = new ChromeDriver();
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		selenium.windowMaximize();
		return driver;
	}
}
