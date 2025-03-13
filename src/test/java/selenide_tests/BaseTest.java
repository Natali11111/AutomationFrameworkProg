package selenide_tests;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeSuite;
import pages.HomePage;

public class BaseTest {
    HomePage homePage;

    @BeforeSuite
    public void beforeRunningSuite() {
        homePage = new HomePage();
        Configuration.timeout = 20000;
        Configuration.browserSize = "1920x1080";
        homePage.openPage();

    }
}
