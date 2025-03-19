package ui_tests.builder;

public class CreateAccountBuilder {
    private String titleRadioButtons;
    private String nameField;
    private String emailField;
    private String passwordField;
    private String daysDropDown;
    private String monthsDropDown;
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

    public CreateAccountBuilder withName(String name) {
        this.nameField = name;
        return this;
    }

    public CreateAccountBuilder withEmail(String email) {
        this.emailField = email;
        return this;
    }

    public CreateAccountBuilder withRadioButton(String radio) {
        this.titleRadioButtons = radio;
        return this;
    }

    public CreateAccountBuilder withPassword(String password) {
        this.passwordField = password;
        return this;
    }

    public CreateAccountBuilder withDay(String day) {
        this.daysDropDown = day;
        return this;
    }

    public CreateAccountBuilder withMonth(String month) {
        this.monthsDropDown = month;
        return this;
    }

    public CreateAccountBuilder withYear(String year) {
        this.yearsDropDown = year;
        return this;
    }

    public CreateAccountBuilder withFirstName(String firstName) {
        this.firstNameField = firstName;
        return this;
    }

    public CreateAccountBuilder withLastName(String lastName) {
        this.lastNameField = lastName;
        return this;
    }

    public CreateAccountBuilder withCompany(String companyName) {
        this.companyFiled = companyName;
        return this;
    }

    public CreateAccountBuilder withAddressOne(String addressOne) {
        this.addressOneFiled = addressOne;
        return this;
    }

    public CreateAccountBuilder withAddressTwo(String addressTwo) {
        this.addressTwoFiled = addressTwo;
        return this;
    }

    public CreateAccountBuilder withCountry(String country) {
        this.countryDropDown = country;
        return this;
    }

    public CreateAccountBuilder withState(String state) {
        this.stateFiled = state;
        return this;
    }

    public CreateAccountBuilder withCity(String city) {
        this.cityFiled = city;
        return this;
    }

    public CreateAccountBuilder withZipcode(String zipcode) {
        this.zipcodeFiled = zipcode;
        return this;
    }

    public CreateAccountBuilder withMobilePhone(String mobileNumber) {
        this.mobileNumberFiled = mobileNumber;
        return this;
    }

    public CreateAccountForm build() {
        return new CreateAccountForm(mobileNumberFiled, zipcodeFiled, cityFiled, stateFiled,
                countryDropDown, addressTwoFiled, addressOneFiled,
                companyFiled, lastNameField, yearsDropDown,
                firstNameField, monthsDropDown, daysDropDown,
                passwordField, emailField, nameField, titleRadioButtons);
    }

}
