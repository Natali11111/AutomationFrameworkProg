package selenide_ui_tests;

import builder.CreateAccountForm;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.CreateAccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SignUpUsersPage;
import test_data.DataProviderClass;


import static utils.GenerateData.getRandomEmail;
import static utils.GenerateData.getRandomWord;

public class CreateAccountPageTest extends BaseTestForRemoteDriver {
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
