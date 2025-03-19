package ui_tests.data_provider;

import ui_tests.builder.CreateAccountBuilder;
import org.testng.annotations.DataProvider;

import static randomaizer.GenerateData.*;

public class DataProviderClass {

    @DataProvider(name = "dataForAllFields")
    public static Object[][] dataForAllFields() {
        return new Object[][]{
                {new CreateAccountBuilder()
                        .withPassword(getRandomWordWithNumbers())
                        .withDay("5")
                        .withMonth("May")
                        .withYear("2020")
                        .withFirstName(getRandomWord())
                        .withLastName(getRandomWord())
                        .withCompany(getRandomWord())
                        .withAddressOne(getRandomWord())
                        .withAddressTwo(getRandomWord())
                        .withCountry("Canada")
                        .withState(getRandomWord())
                        .withCity(getRandomWord())
                        .withZipcode(String.valueOf(getRandomNumber()))
                        .withMobilePhone(String.valueOf(getRandomNumber()))
                        .build()
                }
        };
    }
    @DataProvider(name = "dataForRequiredFields")
    public static Object[][] dataForRequiredFields() {
        return new Object[][]{
                {new CreateAccountBuilder()
                        .withPassword(getRandomWordWithNumbers())
                        .withFirstName(getRandomWord())
                        .withLastName(getRandomWord())
                        .withAddressOne(getRandomWord())
                        .withCountry("Singapore")
                        .withState(getRandomWord())
                        .withCity(getRandomWord())
                        .withZipcode(String.valueOf(getRandomNumber()))
                        .withMobilePhone(String.valueOf(getRandomNumber()))
                        .build()
                }
        };
    }

    @DataProvider(name = "emptyDataForRequiredFields")
    public static Object[][] emptyDataForRequiredFields() {
        return new Object[][]{
                {new CreateAccountBuilder()
                        .withPassword("")
                        .withFirstName("")
                        .withLastName("")
                        .withAddressOne("")
                        .withState("")
                        .withCity("")
                        .withZipcode("")
                        .withMobilePhone("")
                        .build()
                }
        };
    }
}
