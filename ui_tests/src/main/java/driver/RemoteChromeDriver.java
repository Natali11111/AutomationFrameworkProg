package driver;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;

public class RemoteChromeDriver {


    public WebDriver createRemoteDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("128.0");
        capabilities.setPlatform(Platform.WIN11);
        capabilities.setCapability("enableVNC", true);
        try {
            RemoteWebDriver remoteWebDriver = new RemoteWebDriver(
                    URI.create("http://localhost:4444/wd/hub").toURL(),
                    capabilities
            );
            return remoteWebDriver;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
