package selenide_tests;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomePageTest {
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
