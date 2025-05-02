package restassured_api_tests;

import api_tests.bodies.BodyForAuthorEndpoint;
import api_tests.bodies.BodyForUserEndpoint;
import api_tests.endpoints.UserEndpoint;
import api_tests.responses.AuthorsDto;
import api_tests.responses.UsersDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

import static api_tests.test_data.DataForTests.EXPECTEDUSERNAMES;
import static constants.Constants.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static randomaizer.GenerateData.*;

public class ApiTestsForUsers {
    SoftAssert softAssert = new SoftAssert();
    UserEndpoint userEndpoint = new UserEndpoint();
    Integer randomId = getRandomNumber();

    @Test
    public void createAccountCheckThatUserExisted() {
        String randomUsername = getRandomWord();
        String randomPassword = getRandomWordWithNumbers();
        BodyForUserEndpoint bodyForUser = new BodyForUserEndpoint();
        bodyForUser.setId(randomId).setUserName(randomUsername).setPassword(randomPassword);
        Response postResponse = userEndpoint.createUser(bodyForUser);
        postResponse.prettyPrint();
        postResponse.then().statusCode(200).time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0")
                .body(matchesJsonSchemaInClasspath("shemas/user_shema.json"));
        UsersDto actualResponse = postResponse.as(UsersDto.class);
        verifyValuesFromBody(actualResponse, randomId, randomUsername, randomPassword);
        getUserById(randomId, randomUsername, randomPassword);
    }

    @Test
    public void createTwoAccountsWithIdenticalIdVerifyThatItIsImpossible() {
        Integer randomId = getRandomNumber();
        String randomUserName = getRandomWord();
        String randomPassword = getRandomWordWithNumbers();
        BodyForUserEndpoint bodyForUser = new BodyForUserEndpoint();
        bodyForUser.setId(randomId).setUserName(randomUserName)
                .setPassword(randomPassword);
        Response successfulResponse = userEndpoint.createUser(bodyForUser);
        successfulResponse.then().statusCode(200).time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0");
        Response failedResponse = userEndpoint.createUser(bodyForUser);
        failedResponse.then()
                .statusCode(400)
                .header("Content-Type", "application/json; charset=utf-8; v=1.0")
                .body("error", equalTo("The id is already exist"))
                .time(lessThan(2000L));
    }

    @Test(dataProvider = "invalidDataForBody")
    public void negativeTestCreateAccountWithInvalidBody(Integer id, String userName,
                                                         String password, String errorMessage) {
        BodyForUserEndpoint bodyForUser = new BodyForUserEndpoint();
        bodyForUser.setId(id).setUserName(userName).setPassword(password);
        Response response = userEndpoint.createUser(bodyForUser);
        response.then()
                .statusCode(400)
                .time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0")
                .body("error", equalTo(errorMessage));
    }

//    @Test
//    public void getUserByIdCheckThatExpectedUserReturned() {
//        RequestSpecification requestSpecification = RestAssured.given();
//        requestSpecification.baseUri(BASE_URL);
//        requestSpecification.basePath(BASE_PATH_FOR_USERS + 1);
//        Response response = requestSpecification.get();
//        ValidatableResponse validatableResponse = response.then();
//        validatableResponse.statusCode(200);
//        UsersDto actualResponse = response.as(UsersDto.class);
//        softAssert.assertEquals(actualResponse.getId(), 1);
//        softAssert.assertEquals(actualResponse.getUserName(), "User 1");
//        softAssert.assertEquals(actualResponse.getPassword(), "Password1");
//    }

    @Test
    public void updateAccountCheckThatUserDataUpdated() {
        Integer newRandomId = getRandomNumber();
        String randomUsername = getRandomWord();
        String randomPassword = getRandomWordWithNumbers();
        BodyForUserEndpoint bodyForUser = new BodyForUserEndpoint();
        bodyForUser.setId(newRandomId).setUserName(randomUsername).setPassword(randomPassword);
        Response response = userEndpoint.updateUser(bodyForUser, randomId);
        response.then()
                .statusCode(200)
                .time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0")
                .body(matchesJsonSchemaInClasspath("shemas/user_shema.json"));;
        UsersDto actualResponse = response.as(UsersDto.class);
        verifyValuesFromBody(actualResponse, newRandomId, randomUsername, randomPassword);
        getUserById(newRandomId, randomUsername, randomPassword);
    }

    @Test(dataProvider = "invalidDataForBody")
    public void negativeTestUpdateAccountDataWithInvalidBody(Integer id,
                                                             String userName, String password,
                                                             String errorMessage) {
        BodyForUserEndpoint bodyForUser = new BodyForUserEndpoint();
        bodyForUser.setId(id).setUserName(userName).setPassword(password);
        Response response = userEndpoint.updateUser(bodyForUser, randomId);
        response.then()
                .statusCode(404)
                .time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0")
                .body("error", equalTo(errorMessage));
    }

    @Test
    public void deleteAccountCheckThatAccountDeleted() {
        Response deleteResponse = userEndpoint.deleteUser(randomId);
        deleteResponse.then()
                .statusCode(200)
                .time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0");
        Response getResponse = userEndpoint.getUser(randomId);
        getResponse.then()
                .statusCode(402)
                .time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0")
                .body("error", equalTo("The account doesn't exist"));
    }

    @Test
    public void negativeTestDeleteDeletedAccount() {
        Response response = userEndpoint.deleteUser(randomId);
        response.then()
                .statusCode(405)
                .time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0")
                .body("error", equalTo("The account doesn't exist"));
    }

    @Test(dataProvider = "invalidId")
    public void negativeTestDeleteAccountWithInvalidId(Integer id, String errorMessage) {
        Response response = userEndpoint.deleteUser(id);
        response.then()
                .statusCode(415)
                .time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0")
                .body("error", equalTo(errorMessage));
    }

    @Test
    public void getAllUsersAndCheckThatResponseNotEmpty() {
        Response response = userEndpoint.getUsers();
        response.then()
                .statusCode(200)
                .time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0")
                .body(matchesJsonSchemaInClasspath("shemas/user_shema.json"));;
        UsersDto[] actualResponse = response.as(UsersDto[].class);
        Assert.assertNotEquals(actualResponse.length, 0);
    }

//    private UsersDto[] getUsersWithAllInfo() {
//        RequestSpecification requestSpecification = RestAssured.given();
//        requestSpecification.baseUri(BASE_URL);
//        requestSpecification.basePath(BASE_PATH_FOR_USERS);
//        Response response = requestSpecification.get();
//        ValidatableResponse validatableResponse = response.then();
//        validatableResponse.statusCode(200);
//        return response.as(UsersDto[].class);
//    }
//
//    private String[] getUsernames(UsersDto[] usersWithAllInfo) {
//        String[] usernames = new String[usersWithAllInfo.length];
//        for (int i = 0; i < usersWithAllInfo.length; i++) {
//            usernames[i] = usersWithAllInfo[i].getUserName();
//        }
//        return usernames;
//    }
//
//    private void verifyThatAllExpectedUsernamesPresent(String[] expectedUsernames) {
//        String[] actualUsernames = getUsernames(getUsersWithAllInfo());
//        Assert.assertEquals(actualUsernames.length, expectedUsernames.length);
//        for (int i = 0; i < actualUsernames.length; i++) {
//            Assert.assertEquals(actualUsernames[i], expectedUsernames[i]);
//        }
//    }

    private void getUserById(Integer id, String username, String
            password) {
        Response getResponse = userEndpoint.getUser(id);
        getResponse.then().statusCode(200).time(lessThan(2000L));
        UsersDto actualGetResponse = getResponse.as(UsersDto.class);
        softAssert.assertEquals(actualGetResponse.getId(), id);
        softAssert.assertEquals(actualGetResponse.getUserName(), username);
        softAssert.assertEquals(actualGetResponse.getPassword(), password);
    }

    public void verifyValuesFromBody(UsersDto actualResponse, Integer id,
                                     String userName, String password) {
        softAssert.assertEquals(actualResponse.getId(), id);
        softAssert.assertEquals(actualResponse.getUserName(), userName);
        softAssert.assertEquals(actualResponse.getPassword(), password);
    }

    @DataProvider
    public Object[][] invalidDataForBody() {
        return new Object[][]{
                {-1, "Alan", "12kkk12", "Id can't be negative"},
                {0, "Alan", "12kkk12", "Id can't be zero"},
                {949459593, "Alan", "12kkk12", "Id doesn't exist"},
                {293, "", "Wake", "Username can't be empty"},
                {293, "Alan", "", "Password can't be empty"},
                {293, "", "", "Username and password can't be empty"},
                {293, "*%)(#", "Wake", "Username can't has special symbols"},
                {293, "111111", "Wake", "Name can't has numbers"},
                {293, "Alan", "0000000000", "Password contains more than 10 symbols"},
                {293, "Alan", "0", "Password should contains at least 5 symbols"},
                {293, "Alan", "0000-1", "Password can have a negative value"}
        };
    }

    @DataProvider
    public Object[][] invalidId() {
        return new Object[][]{
                {1039329434, "The id doesn't exist"}
        };

    }
}
