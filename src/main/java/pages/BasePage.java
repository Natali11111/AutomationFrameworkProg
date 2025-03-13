package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;

public class BasePage {

    public void isButtonVisible(SelenideElement element) {
        element.shouldBe(visible);
    }

    public void isButtonDisappeared(SelenideElement element) {
        element.shouldBe(hidden);
    }

}
