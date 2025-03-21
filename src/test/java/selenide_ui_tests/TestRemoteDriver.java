package selenide_ui_tests;

import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class TestRemoteDriver extends BaseTestForRemoteDriver {

//    public WebDriver getRemoteDriver() {
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setBrowserName("chrome");
//        capabilities.setVersion("128.0");
//        capabilities.setPlatform(Platform.WIN11);
//        capabilities.setCapability("enableVNC", true);
//        try {
//            RemoteWebDriver driver = new RemoteWebDriver(
//                    URI.create("http://localhost:4444/wd/hub").toURL(),
//                    capabilities
//            );
//            return driver;
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//    }


    @Test
    public void testAllo() throws MalformedURLException {
        open("https://allo.ua/");
        ElementsCollection logos = $$(By.className("v-logo__img"));
        logos.shouldHave(size(2));
    }


}
