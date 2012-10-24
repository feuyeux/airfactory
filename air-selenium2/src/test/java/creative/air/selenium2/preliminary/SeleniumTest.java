package creative.air.selenium2.preliminary;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SeleniumTest {
	Properties properties = new Properties();

	public SeleniumTest() {
		try {
			properties.load(new FileInputStream("src/test/resources/selenium_config.properties"));
		} catch (IOException e) {
		}
	}

	public String getFirefoxPath() {
		return properties.getProperty("seleniumTest.firefox.dir");
	}

	public String getChromePath() {
		return properties.getProperty("seleniumTest.chrome.dir");
	}

	public String getIEPath() {
		return properties.getProperty("seleniumTest.ie.dir");
	}

	public static void main(String[] args) {
		SeleniumTest test = new SeleniumTest();
		System.out.println(test.getFirefoxPath());
	}
}
