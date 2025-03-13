package selenide_tests;

import builder.CreateAccountForm;
import data_provider.DataProviderClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.CreateAccountPage;
import pages.DeleteAccountPage;
import pages.HomePage;
import pages.SignUpUsersPage;

import static randomaizer.GenerateData.getRandomEmail;
import static randomaizer.GenerateData.getRandomWord;

public class DeleteAccountPageTest extends BaseTest {
    CreateAccountPage createAccountPage;
    SignUpUsersPage signUpUsersPage;
    DeleteAccountPage deleteAccountPage;
    HomePage homePage;

    @BeforeTest
    public void before() {
        createAccountPage = new CreateAccountPage();
        signUpUsersPage = new SignUpUsersPage();
        deleteAccountPage = new DeleteAccountPage();
        homePage = new HomePage();
        homePage.openPage();
    }


    @Test(dataProvider = "dataForRequiredFields", dataProviderClass = DataProviderClass.class)
    public void deleteAccount(CreateAccountForm createAccountForm) {
        signUpUsersPage.registerUser(getRandomWord(), getRandomEmail());
        createAccountPage.createAccountFillRequiredAllFields(createAccountForm);
        homePage.clickOnDeleteAccountButton();
        deleteAccountPage.verifyTitle();
        deleteAccountPage.clickOnContinueButton();
        homePage.verifyThatNewButtonsDisappearedFromNavbar();
    }
}
