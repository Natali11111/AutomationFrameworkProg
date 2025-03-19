package restassured_api_tests;

import api_tests.pages.AuthorsDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import static constants.Constants.*;
import static randomaizer.GenerateData.*;

public class ApiTestsForAuthors {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void createAuthorCheckThatAuthorCreated() {
        Map<String, Object> mapWithUserData = new HashMap<>();
        int randomId = getRandomNumber();
        int randomBookId = getRandomNumber();
        String randomFirstName = getRandomWord();
        String randomLastName = getRandomWord();
        mapWithUserData.put("id", randomId);
        mapWithUserData.put("idBook", randomBookId);
        mapWithUserData.put("firstName", randomFirstName);
        mapWithUserData.put("lastName", randomLastName);
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_AUTHORS);
        requestSpecification.body(mapWithUserData);
        requestSpecification.contentType(CONTENT_TYPE);
        Response response = requestSpecification.post();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        AuthorsDto actualResponse = response.as(AuthorsDto.class);
        softAssert.assertEquals(actualResponse.getId(), randomId);
        softAssert.assertEquals(actualResponse.getIdBook(), randomBookId);
        softAssert.assertEquals(actualResponse.getFirstName(), randomFirstName);
        softAssert.assertEquals(actualResponse.getLastName(), randomLastName);
    }

    @Test
    public void getAuthorByIdCheckThatExpectedAuthorReturned() {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_AUTHORS + 28);
        Response response = requestSpecification.get();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        AuthorsDto actualResponse = response.as(AuthorsDto.class);
        softAssert.assertEquals(actualResponse.getId(), 28);
        softAssert.assertEquals(actualResponse.getIdBook(), 10);
        softAssert.assertEquals(actualResponse.getFirstName(), "First Name 28");
        softAssert.assertEquals(actualResponse.getLastName(), "Last Name 28");
    }

    @Test
    public void updateAuthorDataCheckThatAuthorDataUpdated() {
        Map<String, Object> mapWithUserData = new HashMap<>();
        int randomId = getRandomNumber();
        int randomBookId = getRandomNumber();
        String randomFirstName = getRandomWord();
        String randomLastName = getRandomWord();
        mapWithUserData.put("id", randomId);
        mapWithUserData.put("idBook", randomBookId);
        mapWithUserData.put("firstName", randomFirstName);
        mapWithUserData.put("lastName", randomLastName);
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_AUTHORS + 1);
        requestSpecification.contentType(CONTENT_TYPE);
        requestSpecification.body(mapWithUserData);
        Response response = requestSpecification.put();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        AuthorsDto actualResponse = response.as(AuthorsDto.class);
        softAssert.assertEquals(actualResponse.getId(), randomId);
        softAssert.assertEquals(actualResponse.getIdBook(), randomBookId);
        softAssert.assertEquals(actualResponse.getFirstName(), randomFirstName);
        softAssert.assertEquals(actualResponse.getLastName(), randomLastName);
    }

    @Test
    public void deleteAuthorCheckThatAuthorDeleted() {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_AUTHORS + 4);
        Response response = requestSpecification.delete();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
    }

    @Test
    public void getAllAuthorsCheckThatResponseIsNotEmpty() {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_AUTHORS);
        Response response = requestSpecification.get();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        AuthorsDto[] authors = response.as(AuthorsDto[].class);
        Assert.assertNotEquals(authors.length, 0);
    }

    @Test(dataProvider = "invalidDataForNumberField")
    public void createAuthorWithInvalidIdCheckThatAuthorNotCreated(Object id) {
        Map<String, Object> mapWithUserData = new HashMap<>();
        mapWithUserData.put("id", id);
        mapWithUserData.put("idBook", getRandomNumber());
        mapWithUserData.put("firstName", getRandomWord());
        mapWithUserData.put("lastName", getRandomWord());
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_AUTHORS);
        requestSpecification.body(mapWithUserData);
        requestSpecification.contentType(CONTENT_TYPE);
        Response response = requestSpecification.post();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(400);
    }

    @Test(dataProvider = "invalidDataForNumberField")
    public void createAuthorWithInvalidBookIdCheckThatAuthorNotCreated(Object id) {
        Map<String, Object> mapWithUserData = new HashMap<>();
        mapWithUserData.put("id", getRandomNumber());
        mapWithUserData.put("idBook", id);
        mapWithUserData.put("firstName", getRandomWord());
        mapWithUserData.put("lastName", getRandomWord());
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_AUTHORS);
        requestSpecification.body(mapWithUserData);
        requestSpecification.contentType(CONTENT_TYPE);
        Response response = requestSpecification.post();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(400);
    }

    @Test(dataProvider = "invalidDataForStringFiled")
    public void createAuthorWithInvalidFirstNameCheckThatAuthorNotCreated(Object firstName) {
        Map<String, Object> mapWithUserData = new HashMap<>();
        mapWithUserData.put("id", getRandomNumber());
        mapWithUserData.put("idBook", getRandomNumber());
        mapWithUserData.put("firstName", firstName);
        mapWithUserData.put("lastName", getRandomWord());
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_AUTHORS);
        requestSpecification.body(mapWithUserData);
        requestSpecification.contentType(CONTENT_TYPE);
        Response response = requestSpecification.post();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(400);
    }

    @Test(dataProvider = "invalidDataForStringFiled")
    public void createAuthorWithInvalidLastNameCheckThatAuthorNotCreated(Object lastName) {
        Map<String, Object> mapWithUserData = new HashMap<>();
        mapWithUserData.put("id", getRandomNumber());
        mapWithUserData.put("idBook", getRandomNumber());
        mapWithUserData.put("firstName", getRandomWord());
        mapWithUserData.put("lastName", lastName);
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_AUTHORS);
        requestSpecification.body(mapWithUserData);
        requestSpecification.contentType(CONTENT_TYPE);
        Response response = requestSpecification.post();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(400);
    }

    @Test(dataProvider = "invalidBasePath")
    public void createAuthorWithInvalidBasePathCheckThatAuthorNotCreated(String basePath) {
        Map<String, Object> mapWithUserData = new HashMap<>();
        mapWithUserData.put("id", getRandomNumber());
        mapWithUserData.put("idBook", getRandomNumber());
        mapWithUserData.put("firstName", getRandomWord());
        mapWithUserData.put("lastName", getRandomWord());
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(basePath);
        requestSpecification.body(mapWithUserData);
        requestSpecification.contentType(CONTENT_TYPE);
        Response response = requestSpecification.post();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(404);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Base Path cannot be null")
    public void createAuthorWithBaseUrlNullCheckThatAuthorNotCreated() {
        Map<String, Object> mapWithUserData = new HashMap<>();
        mapWithUserData.put("id", getRandomNumber());
        mapWithUserData.put("idBook", getRandomNumber());
        mapWithUserData.put("firstName", getRandomWord());
        mapWithUserData.put("lastName", getRandomWord());
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(null);
        requestSpecification.body(mapWithUserData);
        requestSpecification.contentType(CONTENT_TYPE);
        Response response = requestSpecification.post();
    }

    @Test(dataProvider = "invalidDataForContentType",
            expectedExceptions = IllegalArgumentException.class)
    public void createAuthorWithWrongContentTypeCheckThatAuthorNotCreated(String contentType) {
        Map<String, Object> mapWithUserData = new HashMap<>();
        mapWithUserData.put("id", getRandomNumber());
        mapWithUserData.put("idBook", getRandomNumber());
        mapWithUserData.put("firstName", getRandomWord());
        mapWithUserData.put("lastName", getRandomWord());
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_AUTHORS);
        requestSpecification.body(mapWithUserData);
        requestSpecification.contentType(contentType);
        Response response = requestSpecification.post();
    }

    @Test(expectedExceptions = UnknownHostException.class)
    public void createAuthorWithWrongBaseUrlCheckThatAuthorNotCreated() {
        Map<String, Object> mapWithUserData = new HashMap<>();
        mapWithUserData.put("id", getRandomNumber());
        mapWithUserData.put("idBook", getRandomNumber());
        mapWithUserData.put("firstName", getRandomWord());
        mapWithUserData.put("lastName", getRandomWord());
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_AUTHORS);
        requestSpecification.body(mapWithUserData);
        requestSpecification.contentType(CONTENT_TYPE);
        Response response = requestSpecification.post();
    }

    @DataProvider
    public static Object[][] invalidDataForContentType() {
        return new Object[][]{
                {""},
                {"jfjkngdfjkngd"},
                {null}
        };
    }

    @DataProvider
    public static Object[][] invalidDataForNumberField() {
        return new Object[][]{
                {"&&&$$!!"},
                {"jfjkngdfjkngd"},
                {""},
                {-123},
                {0},
                {"1111111111111111111111111111111111111"}
        };
    }

    @DataProvider
    public static Object[][] invalidDataForStringFiled() {
        return new Object[][]{
                {"&&&$$!!"},
                {"1224435563456"},
                {""},
                {"58459dfjdj8589"},
                {"f g o d t t h f"},
                {"уоккошоаплвпщ"},
                {null}
        };
    }

    @DataProvider
    public static Object[][] invalidBasePath() {
        return new Object[][]{
                {"/notApi/v1/Authors"},
                {"//v1/Authors"},
                {"/api/v10000000/Authors"},
                {"/api//Authors"},
                {"/api/v1/Banana"},
                {"/api//Author"},
                {"/api/v1/"},
                {""}
        };
    }
}
