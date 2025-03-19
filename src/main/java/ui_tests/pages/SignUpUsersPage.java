package ui_tests.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SignUpUsersPage {
    CreateAccountPage createAccountPage = new CreateAccountPage();
    HomePage homePage = new HomePage();
    private final SelenideElement singUpButton = $(By.xpath("//button[@data-qa='signup-button']"));
    private final SelenideElement nameField = $(By.xpath("//input[@data-qa='signup-name']"));
    private final SelenideElement emailField = $(By.xpath("//input[@data-qa='signup-email']"));
    private final SelenideElement singUpForm = $(By.xpath("//div[@class='signup-form']"));

    public void registerUser(String name, String email) {
        homePage.clickOnSingUpAndLoginButton();
        verifySingUpFormPresent();
        nameField.setValue(name);
        emailField.setValue(email);
        singUpButton.click();
        createAccountPage.getLoginForm().shouldBe(visible);
    }

    public void verifySingUpFormPresent() {
        singUpForm.shouldBe(visible);
    }
}
