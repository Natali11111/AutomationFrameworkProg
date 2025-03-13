package selenide_tests;

import builder.CreateAccountForm;
import data_provider.DataProviderClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.CreateAccountPage;
import pages.LoginPage;
import pages.SignUpUsersPage;

import static randomaizer.GenerateData.*;

public class LoginPageTest extends BaseTest {
    CreateAccountPage createAccountPage;
    SignUpUsersPage signUpUsersPage;
    LoginPage loginPage;
    String email = getRandomEmail();
    String password = "";

    @BeforeTest
    public void before() {
        createAccountPage = new CreateAccountPage();
        signUpUsersPage = new SignUpUsersPage();
        loginPage = new LoginPage();
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
        loginPage.verifyLoginFormPresent();
        loginPage.loginToAccount(getRandomEmail(), getRandomWordWithNumbers());
        loginPage.verifyNotificationPresent();
    }
}
