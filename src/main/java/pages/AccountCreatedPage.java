package pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

@Data
public class AccountCreatedPage {
    private final SelenideElement title = $(byText("Account Created!"));
    private final SelenideElement continueButton = $(By.xpath("//a[@data-qa='continue-button']"));

    public void clickOnContinueButton() {
        continueButton.click();
    }

    public void verifyTitle() {
        title.shouldHave(text("Account Created!"));
    }

}
