package creative.air.selenium2.preliminary;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ChromeFactory {
	public static WebDriver createDriver() throws IOException {
		ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File(SeleniumConfig.getChromePath())).usingAnyFreePort()
				.build();
		service.start();
		return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
	}
}
