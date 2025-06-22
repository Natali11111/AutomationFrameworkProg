package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {
    private final SelenideElement loginForm = $(By.xpath("//div[@class='login-form']"));
    private final SelenideElement emailField = $(By.xpath("//input[@data-qa='login-email']"));
    private final SelenideElement passwordFiled = $(By.xpath("//input[@data-qa='login-password']"));
    private final SelenideElement loginButton = $(By.xpath("//button[@data-qa='login-button']"));
    private final SelenideElement notification = $(byText("Your email or password is incorrect!"));

    public void verifyLoginFormPresent() {
        loginForm.shouldBe(visible);
    }

    public void loginToAccount(String email, String password) {
        emailField.setValue(email);
        passwordFiled.setValue(password);
        loginButton.click();
    }

    public void verifyNotificationPresent() {
        notification.shouldHave(text("Your email or password is incorrect!"));
    }

}
