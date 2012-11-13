package creative.air.selenium2.load;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SeleniumConfig {
	static Properties properties = new Properties();

	static {
		try {
			properties.load(new FileInputStream("src/test/resources/selenium_config.properties"));
		} catch (IOException e) {
		}
	}

	public static String getFirefoxPath() {
		return properties.getProperty("seleniumTest.firefox.dir");
	}

	public static String getChromePath() {
		return properties.getProperty("seleniumTest.chrome.dir");
	}

	public static String getIEPath() {
		return properties.getProperty("seleniumTest.ie.dir");
	}

	public static void main(String[] args) {
		Properties props = System.getProperties();
		String osName = props.getProperty("os.name");
		System.out.println(osName);
		System.out.println(getFirefoxPath());
	}
}
