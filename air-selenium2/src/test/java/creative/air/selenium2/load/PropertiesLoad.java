package creative.air.selenium2.load;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class PropertiesLoad {
	private PropertiesConfiguration conf;
	private final static String configurationFile = "air-selenium.properties";
	private static PropertiesLoad instance;

	private PropertiesLoad() {
		conf = new PropertiesConfiguration();
		conf.setDelimiterParsingDisabled(true);
		conf.setReloadingStrategy(new FileChangedReloadingStrategy());
		try {
			conf.load(configurationFile);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static PropertiesLoad getInstance() {
		if (instance == null) {
			instance = new PropertiesLoad();
		}
		return instance;
	}

	public String getFirefoxPath() {
		return conf.getString("seleniumTest.firefox.dir");
	}

	public String getChromePath() {
		return conf.getString("seleniumTest.chrome.dir");
	}

	public String getIEPath() {
		return conf.getString("seleniumTest.ie.dir");
	}

	public String[] getWebBrowsers() {
		try {
			String webBrowsers = conf.getString("seleniumTest.explore");
			String[] webBrowserArray = webBrowsers.toLowerCase().replaceAll(" ", "").split(",");
			return webBrowserArray;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		PropertiesLoad loader = new PropertiesLoad();
		System.out.println(loader.getFirefoxPath());
		String[] browsers = loader.getWebBrowsers();
		for (int i = 0; i < browsers.length; i++) {
			System.out.println(browsers[i]);
		}
	}
}
