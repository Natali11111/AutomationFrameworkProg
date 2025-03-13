package selenide_tests;

import builder.CreateAccountForm;
import data_provider.DataProviderClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.CreateAccountPage;
import pages.LoginPage;
import pages.SignUpUsersPage;

import static randomaizer.GenerateData.getRandomEmail;
import static randomaizer.GenerateData.getRandomWord;

public class CreateAccountPageTest extends BaseTest {
    CreateAccountPage createAccountPage;
    SignUpUsersPage signUpUsersPage;
    LoginPage loginPage;

    @BeforeTest
    public void before() {
        createAccountPage = new CreateAccountPage();
        signUpUsersPage = new SignUpUsersPage();
        loginPage = new LoginPage();
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
