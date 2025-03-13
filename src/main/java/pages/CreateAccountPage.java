package pages;

import builder.CreateAccountForm;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Data
public class CreateAccountPage extends BasePage {
    AccountCreatedPage accountCreatedPage = new AccountCreatedPage();
    HomePage homePage = new HomePage();
    private final ElementsCollection titleRadioButtons = $$(By.xpath("//div[@class='radio-inline']"));
    private final SelenideElement nameField = $(By.id("name"));
    private final SelenideElement emailField = $(By.id("email"));
    private final SelenideElement passwordField = $(By.id("password"));
    private final SelenideElement daysDropDownButton = $(By.id("days"));
    private final ElementsCollection daysDropDownItems = $$(By.xpath("//select[@id='days']/option"));
    private final SelenideElement monthsDropDownButton = $(By.id("months"));
    private final ElementsCollection monthsDropDownItems = $$(By.xpath("//select[@id='months']/option"));
    private final SelenideElement yearsDropDownButton = $(By.id("years"));
    private final ElementsCollection yearDropDownItems = $$(By.xpath("//select[@id='years']/option"));
    private final SelenideElement firstNameField = $(By.id("first_name"));
    private final SelenideElement lastNameField = $(By.id("last_name"));
    private final SelenideElement companyFiled = $(By.id("company"));
    private final SelenideElement addressOneFiled = $(By.id("address1"));
    private final SelenideElement addressTwoFiled = $(By.id("address2"));
    private final SelenideElement countryDropDownButton = $(By.id("country"));
    private final ElementsCollection countryDropDownItems = $$(By.xpath("//select[@data-qa='country']//option"));
    private final SelenideElement stateFiled = $(By.id("state"));
    private final SelenideElement cityFiled = $(By.id("city"));
    private final SelenideElement zipcodeFiled = $(By.id("zipcode"));
    private final SelenideElement mobileNumberFiled = $(By.id("mobile_number"));
    private final SelenideElement createAccountButton = $(By.xpath("//button[@data-qa ='create-account']"));
    private final SelenideElement loginForm = $(By.xpath("//div[@class='login-form']"));


    public void createAccountFillAllFields(CreateAccountForm createAccountForm) {
        passwordField.setValue(createAccountForm.getPasswordField());
        daysDropDownButton.scrollIntoView(true);
        chooseDayFromDropDown(createAccountForm.getDaysDropDown());
        chooseMonthFromDropDown(createAccountForm.getMonthsDropDown());
        chooseYearFromDropDown(createAccountForm.getYearsDropDown());
        firstNameField.setValue(createAccountForm.getFirstNameField());
        lastNameField.setValue(createAccountForm.getLastNameField());
        companyFiled.setValue(createAccountForm.getCompanyFiled());
        addressOneFiled.setValue(createAccountForm.getAddressOneFiled());
        addressTwoFiled.setValue(createAccountForm.getAddressTwoFiled());
        countryDropDownButton.scrollIntoView(true);
        chooseCountryFromDropDown(createAccountForm.getCountryDropDown());
        stateFiled.setValue(createAccountForm.getStateFiled());
        cityFiled.setValue(createAccountForm.getCityFiled());
        zipcodeFiled.setValue(createAccountForm.getZipcodeFiled());
        mobileNumberFiled.setValue(createAccountForm.getMobileNumberFiled());
        createAccountButton.click();
        accountCreatedPage.verifyTitle();
        accountCreatedPage.clickOnContinueButton();
        homePage.verifyThatNewButtonsPresentInNavbar();
    }

    public void createAccountFillRequiredAllFields(CreateAccountForm createAccountForm) {
        passwordField.setValue(createAccountForm.getPasswordField());
        firstNameField.setValue(createAccountForm.getFirstNameField());
        lastNameField.setValue(createAccountForm.getLastNameField());
        addressOneFiled.setValue(createAccountForm.getAddressOneFiled());
        countryDropDownButton.scrollIntoView(true);
        chooseCountryFromDropDown(createAccountForm.getCountryDropDown());
        stateFiled.setValue(createAccountForm.getStateFiled());
        cityFiled.setValue(createAccountForm.getCityFiled());
        zipcodeFiled.setValue(createAccountForm.getZipcodeFiled());
        mobileNumberFiled.setValue(createAccountForm.getMobileNumberFiled());
        createAccountButton.click();
        accountCreatedPage.verifyTitle();
        accountCreatedPage.clickOnContinueButton();
        homePage.verifyThatNewButtonsPresentInNavbar();
    }

    public void createAccountWithEmptyDataInRequiredAllFields(CreateAccountForm createAccountForm) {
        passwordField.setValue(createAccountForm.getPasswordField());
        firstNameField.setValue(createAccountForm.getFirstNameField());
        lastNameField.setValue(createAccountForm.getLastNameField());
        addressOneFiled.setValue(createAccountForm.getAddressOneFiled());
        countryDropDownButton.scrollIntoView(true);
        stateFiled.setValue(createAccountForm.getStateFiled());
        cityFiled.setValue(createAccountForm.getCityFiled());
        zipcodeFiled.setValue(createAccountForm.getZipcodeFiled());
        mobileNumberFiled.setValue(createAccountForm.getMobileNumberFiled());
        createAccountButton.click();
        accountCreatedPage.getTitle().shouldNotBe(visible);
    }

    public void chooseItemFromDropDown(String item, ElementsCollection listWithItems) {
        for (SelenideElement element : listWithItems) {
            if (element.text().equals(item)) {
                element.click();
            }
        }
    }

    public void clickOnDropDownWithDays() {
        daysDropDownButton.click();
    }

    public void clickOnDropDownWithMonths() {
        monthsDropDownButton.click();
    }

    public void clickOnDropDownWithYears() {
        yearsDropDownButton.click();
    }

    public void clickOnDropDownWithCountries() {
        countryDropDownButton.click();
    }

    public void chooseCountryFromDropDown(String country) {
        clickOnDropDownWithCountries();
        chooseItemFromDropDown(country, countryDropDownItems);
    }

    public void chooseDayFromDropDown(String day) {
        clickOnDropDownWithDays();
        chooseItemFromDropDown(day, daysDropDownItems);
    }

    public void chooseMonthFromDropDown(String month) {
        clickOnDropDownWithMonths();
        chooseItemFromDropDown(month, monthsDropDownItems);
    }

    public void chooseYearFromDropDown(String year) {
        clickOnDropDownWithYears();
        chooseItemFromDropDown(year, yearDropDownItems);
    }

}
