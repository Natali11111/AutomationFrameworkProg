package selenide_ui_tests;

import ui_tests.builder.CreateAccountForm;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ui_tests.data_provider.DataProviderClass;
import ui_tests.pages.CreateAccountPage;
import ui_tests.pages.DeleteAccountPage;
import ui_tests.pages.HomePage;
import ui_tests.pages.SignUpUsersPage;

import static randomaizer.GenerateData.getRandomEmail;
import static randomaizer.GenerateData.getRandomWord;

public class DeleteAccountPageTest extends BaseTestForRemoteDriver {
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
