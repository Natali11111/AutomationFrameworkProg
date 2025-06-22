package selenide_ui_tests;

import builder.CreateAccountForm;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.CreateAccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SignUpUsersPage;
import test_data.DataProviderClass;

import static utils.GenerateData.*;

public class LoginPageTest extends BaseTestForRemoteDriver {
    CreateAccountPage createAccountPage;
    SignUpUsersPage signUpUsersPage;
    LoginPage loginPage;
    HomePage homePage;
    String email = getRandomEmail();
    String password = "";

    @BeforeTest
    public void before() {
        createAccountPage = new CreateAccountPage();
        signUpUsersPage = new SignUpUsersPage();
        loginPage = new LoginPage();
        homePage = new HomePage();
        homePage.openPage();
    }

    @Test(dataProvider = "dataForAllFields", dataProviderClass = DataProviderClass.class)
    public void loginToAccountWithExistingAccount(CreateAccountForm createAccountForm) {
        signUpUsersPage.registerUser(getRandomWord(), email);
        createAccountPage.createAccountFillAllFields(createAccountForm);
        password = createAccountForm.getPasswordField();
        homePage.clickOnLogoutButton();
        loginPage.verifyLoginFormPresent();
        loginPage.loginToAccount(email, password);
        homePage.verifyThatNewButtonsPresentInNavbar();
        homePage.clickOnLogoutButton();
        homePage.verifyThatNewButtonsDisappearedFromNavbar();
    }

    @Test
    public void loginToAccountWithNotExistingAccount() {
        homePage.clickOnSingUpAndLoginButton();
        loginPage.verifyLoginFormPresent();
        loginPage.loginToAccount(getRandomEmail(), getRandomWordWithNumbers());
        loginPage.verifyNotificationPresent();
    }
}
