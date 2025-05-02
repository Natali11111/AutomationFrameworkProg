package selenide_ui_tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ui_tests.builder.CreateAccountForm;
import ui_tests.data_provider.DataProviderClass;
import ui_tests.pages.CreateAccountPage;
import ui_tests.pages.HomePage;
import ui_tests.pages.LoginPage;
import ui_tests.pages.SignUpUsersPage;

import static randomaizer.GenerateData.*;

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
