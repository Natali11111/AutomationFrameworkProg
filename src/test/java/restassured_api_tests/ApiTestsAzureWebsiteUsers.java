package restassured_api_tests;

import api_tests.pages.UserDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static api_tests.test_data.DataForTests.EXPECTEDUSERNAMES;
import static randomaizer.GenerateData.*;

public class ApiTestsAzureWebsiteUsers {


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
        requestSpecification.baseUri("https://fakerestapi.azurewebsites.net/");
        requestSpecification.basePath("/api/v1/Users");
        requestSpecification.body(mapWithUserData);
        requestSpecification.contentType("application/json");
        Response response = requestSpecification.post();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        UserDto actualResponse = response.as(UserDto.class);
        Assert.assertEquals(actualResponse.getId(), randomId);
        Assert.assertEquals(actualResponse.getUserName(), randomUsername);
        Assert.assertEquals(actualResponse.getPassword(), randomPassword);
    }

    @Test
    public void getUserByIdCheckThatExpectedUserReturned() {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://fakerestapi.azurewebsites.net/");
        requestSpecification.basePath("/api/v1/Users/1");
        Response response = requestSpecification.get();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        UserDto actualResponse = response.as(UserDto.class);
        Assert.assertEquals(actualResponse.getId(), 1);
        Assert.assertEquals(actualResponse.getUserName(), "User 1");
        Assert.assertEquals(actualResponse.getPassword(), "Password1");
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
        requestSpecification.baseUri("https://fakerestapi.azurewebsites.net/");
        requestSpecification.basePath("/api/v1/Users/1");
        requestSpecification.contentType("application/json");
        requestSpecification.body(mapWithUserData);
        Response response = requestSpecification.put();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        UserDto actualResponse = response.as(UserDto.class);
        Assert.assertEquals(actualResponse.getId(), randomId);
        Assert.assertEquals(actualResponse.getUserName(), randomUsername);
        Assert.assertEquals(actualResponse.getPassword(), randomPassword);
    }

    @Test
    public void deleteAccountCheckThatAccountDeleted() {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://fakerestapi.azurewebsites.net/");
        requestSpecification.basePath("/api/v1/Users/4");
        Response response = requestSpecification.delete();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void getAllUsersAndCheckThatCorrectUsernamesReturned() {
        verifyThatAllExpectedUsernamesPresent(EXPECTEDUSERNAMES);
    }


    private UserDto[] getUsersWithAllInfo() {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://fakerestapi.azurewebsites.net/");
        requestSpecification.basePath("/api/v1/Users");
        Response response = requestSpecification.get();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        return response.as(UserDto[].class);
    }

    private String[] getUsernames(UserDto[] usersWithAllInfo) {
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
