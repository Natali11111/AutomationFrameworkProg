package selenide_ui_tests;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import ui_tests.pages.HomePage;

public class HomePageTest extends BaseTestForRemoteDriver {
    HomePage homePage;


    @BeforeSuite
    public void before() {
        homePage = new HomePage();
    }

    @Test
    public void openHomePageTest() {
        homePage.openPage();
        homePage.verifyThatHomePageOpen();
    }
}
