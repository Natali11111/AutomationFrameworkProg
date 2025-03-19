package selenide_ui_tests;

import ui_tests.builder.CreateAccountForm;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ui_tests.data_provider.DataProviderClass;
import ui_tests.pages.CreateAccountPage;
import ui_tests.pages.HomePage;
import ui_tests.pages.LoginPage;
import ui_tests.pages.SignUpUsersPage;

import static randomaizer.GenerateData.getRandomEmail;
import static randomaizer.GenerateData.getRandomWord;

public class CreateAccountPageTest extends BaseTest {
    CreateAccountPage createAccountPage;
    SignUpUsersPage signUpUsersPage;
    LoginPage loginPage;
    HomePage homePage;

    @BeforeTest
    public void before() {
        createAccountPage = new CreateAccountPage();
        signUpUsersPage = new SignUpUsersPage();
        loginPage = new LoginPage();
        homePage = new HomePage();
        homePage.openPage();
    }

    @Test(dataProvider = "dataForRequiredFields", dataProviderClass = DataProviderClass.class)
    public void createAccountFillRequiredFields(CreateAccountForm createAccountForm) {
        signUpUsersPage.registerUser(getRandomWord(), getRandomEmail());
        createAccountPage.createAccountFillRequiredAllFields(createAccountForm);
        homePage.clickOnLogoutButton();
        signUpUsersPage.verifySingUpFormPresent();
        loginPage.verifyLoginFormPresent();
    }

    @Test(dataProvider = "emptyDataForRequiredFields", dataProviderClass = DataProviderClass.class)
    public void createAccountNotFillRequiredFields(CreateAccountForm createAccountForm) {
        signUpUsersPage.registerUser(getRandomWord(), getRandomEmail());
        createAccountPage.createAccountWithEmptyDataInRequiredAllFields(createAccountForm);
    }

    @Test(dataProvider = "dataForAllFields", dataProviderClass = DataProviderClass.class)
    public void createAccountFillAllFields(CreateAccountForm createAccountForm) {
        signUpUsersPage.registerUser(getRandomWord(), getRandomEmail());
        createAccountPage.createAccountFillAllFields(createAccountForm);
        homePage.clickOnLogoutButton();
        signUpUsersPage.verifySingUpFormPresent();
        loginPage.verifyLoginFormPresent();
    }
}
