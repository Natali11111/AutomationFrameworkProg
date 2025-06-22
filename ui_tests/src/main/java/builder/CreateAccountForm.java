package builder;

import lombok.Data;

@Data
public class CreateAccountForm {
    private String titleRadioButtons;
    private String nameField;
    private String emailField;
    private String passwordField;
    private  String daysDropDown;
    private String monthsDropDown ;
    private String yearsDropDown;
    private String firstNameField;
    private String lastNameField;
    private String companyFiled;
    private String addressOneFiled;
    private String addressTwoFiled;
    private String countryDropDown;
    private String stateFiled;
    private String cityFiled;
    private String zipcodeFiled;
    private String mobileNumberFiled;

    public CreateAccountForm(String mobileNumberFiled, String zipcodeFiled, String cityFiled, String stateFiled,
                             String countryDropDown, String addressTwoFiled, String addressOneFiled,
                             String companyFiled, String lastNameField, String yearsDropDown,
                             String firstNameField, String monthsDropDown, String daysDropDown,
                             String passwordField, String emailField, String nameField, String radioButton) {
        this.mobileNumberFiled = mobileNumberFiled;
        this.zipcodeFiled = zipcodeFiled;
        this.cityFiled = cityFiled;
        this.stateFiled = stateFiled;
        this.countryDropDown = countryDropDown;
        this.addressTwoFiled = addressTwoFiled;
        this.addressOneFiled = addressOneFiled;
        this.companyFiled = companyFiled;
        this.lastNameField = lastNameField;
        this.yearsDropDown = yearsDropDown;
        this.firstNameField = firstNameField;
        this.monthsDropDown = monthsDropDown;
        this.daysDropDown = daysDropDown;
        this.passwordField = passwordField;
        this.emailField = emailField;
        this.nameField = nameField;
        this.titleRadioButtons = titleRadioButtons;
    }
}
