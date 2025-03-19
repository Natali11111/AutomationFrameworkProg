package ui_tests.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class HomePage extends BasePage {
    private final SelenideElement slides = $(By.id("slider-carousel"));
    private final SelenideElement logoutButton = $(byText("Logout"));
    private final SelenideElement deleteAccountButton = $(byText("Delete Account"));
    private final SelenideElement loggedAsButton = $(byText("Logged in as"));
    private final SelenideElement singUpAndLoginButton = $(byText("Signup / Login"));

    public void openPage() {
        open("https://www.automationexercise.com/");
    }

    public void clickOnSingUpAndLoginButton() {
        singUpAndLoginButton.click();
    }

    public void clickOnLogoutButton() {
        logoutButton.click();
    }

    public void clickOnDeleteAccountButton() {
        deleteAccountButton.click();
    }

    public void verifyThatHomePageOpen() {
        slides.shouldBe(visible);
    }

    public void verifyThatNewButtonsPresentInNavbar() {
        isButtonVisible(loggedAsButton);
        isButtonVisible(deleteAccountButton);
        isButtonVisible(logoutButton);
    }

    public void verifyThatNewButtonsDisappearedFromNavbar() {
        isButtonDisappeared(logoutButton);
        isButtonDisappeared(deleteAccountButton);
        isButtonDisappeared(loggedAsButton);
    }

}
