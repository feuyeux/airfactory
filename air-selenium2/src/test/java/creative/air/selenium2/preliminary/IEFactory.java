package creative.air.selenium2.preliminary;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class IEFactory   {
	public static  WebDriver createDriver() throws IOException {
		System.setProperty("webdriver.ie.driver", SeleniumConfig.getIEPath());
		return new InternetExplorerDriver();
	}
}
