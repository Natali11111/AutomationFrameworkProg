package ui_tests.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DeleteAccountPage {
    private final SelenideElement title = $(byText("Account Deleted!"));
    private final SelenideElement continueButton = $(By.xpath("//a[@data-qa='continue-button']"));

    public void clickOnContinueButton() {
        continueButton.click();
    }

    public void verifyTitle() {
        title.shouldHave(text("Account Deleted!"));
    }

    public void test() {
        clickOnContinueButton();

    }
}
