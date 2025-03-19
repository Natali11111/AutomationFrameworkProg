package restassured_api_tests;

import api_tests.pages.UsersDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

import static api_tests.test_data.DataForTests.EXPECTEDUSERNAMES;
import static constants.Constants.*;
import static randomaizer.GenerateData.*;

public class ApiTestsForUsers {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void createAccountCheckThatUserExisted() {
        Map<String, Object> mapWithUserData = new HashMap<>();
        int randomId = getRandomNumber();
        String randomUsername = getRandomWord();
        String randomPassword = getRandomWordWithNumbers();
        mapWithUserData.put("id", randomId);
        mapWithUserData.put("username", randomUsername);
        mapWithUserData.put("password", randomPassword);
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_USERS);
        requestSpecification.body(mapWithUserData);
        requestSpecification.contentType(CONTENT_TYPE);
        Response response = requestSpecification.post();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        UsersDto actualResponse = response.as(UsersDto.class);
        softAssert.assertEquals(actualResponse.getId(), randomId);
        softAssert.assertEquals(actualResponse.getUserName(), randomUsername);
        softAssert.assertEquals(actualResponse.getPassword(), randomPassword);
    }

    @Test
    public void getUserByIdCheckThatExpectedUserReturned() {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_USERS + 1);
        Response response = requestSpecification.get();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        UsersDto actualResponse = response.as(UsersDto.class);
        softAssert.assertEquals(actualResponse.getId(), 1);
        softAssert.assertEquals(actualResponse.getUserName(), "User 1");
        softAssert.assertEquals(actualResponse.getPassword(), "Password1");
    }

    @Test
    public void updateAccountCheckThatUserDataUpdated() {
        Map<String, Object> mapWithUserData = new HashMap<>();
        int randomId = getRandomNumber();
        String randomUsername = getRandomWord();
        String randomPassword = getRandomWordWithNumbers();
        mapWithUserData.put("id", randomId);
        mapWithUserData.put("username", randomUsername);
        mapWithUserData.put("password", randomPassword);
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_USERS + 1);
        requestSpecification.contentType(CONTENT_TYPE);
        requestSpecification.body(mapWithUserData);
        Response response = requestSpecification.put();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        UsersDto actualResponse = response.as(UsersDto.class);
        softAssert.assertEquals(actualResponse.getId(), randomId);
        softAssert.assertEquals(actualResponse.getUserName(), randomUsername);
        softAssert.assertEquals(actualResponse.getPassword(), randomPassword);
    }

    @Test
    public void deleteAccountCheckThatAccountDeleted() {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_USERS + 4);
        Response response = requestSpecification.delete();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
    }

    @Test
    public void getAllUsersAndCheckThatCorrectUsernamesReturned() {
        verifyThatAllExpectedUsernamesPresent(EXPECTEDUSERNAMES);
    }

    private UsersDto[] getUsersWithAllInfo() {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH_FOR_USERS);
        Response response = requestSpecification.get();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        return response.as(UsersDto[].class);
    }

    private String[] getUsernames(UsersDto[] usersWithAllInfo) {
        String[] usernames = new String[usersWithAllInfo.length];
        for (int i = 0; i < usersWithAllInfo.length; i++) {
            usernames[i] = usersWithAllInfo[i].getUserName();
        }
        return usernames;
    }

    private void verifyThatAllExpectedUsernamesPresent(String[] expectedUsernames) {
        String[] actualUsernames = getUsernames(getUsersWithAllInfo());
        Assert.assertEquals(actualUsernames.length, expectedUsernames.length);
        for (int i = 0; i < actualUsernames.length; i++) {
            Assert.assertEquals(actualUsernames[i], expectedUsernames[i]);
        }
    }
}
