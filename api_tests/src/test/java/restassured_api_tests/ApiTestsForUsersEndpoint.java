package restassured_api_tests;

import bodies_dto.BodyForUserEndpoint;
import endpoints.UserEndpoint;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import responses_dto.UsersDto;
import test_data.DataProviderClass;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static utils.GenerateData.*;

public class ApiTestsForUsersEndpoint {
    SoftAssert softAssert = new SoftAssert();
    UserEndpoint userEndpoint = new UserEndpoint();

    @Test
    public void createAccountCheckThatUserExisted() {
        Integer randomId = getRandomNumber();
        String randomUsername = getRandomWord();
        String randomPassword = getRandomWordWithNumbers();
        BodyForUserEndpoint bodyForUser = new BodyForUserEndpoint();
        bodyForUser.setId(randomId).setUserName(randomUsername).setPassword(randomPassword);
        Response postResponse = userEndpoint.createUser(bodyForUser);
        postResponse.then().statusCode(200).time(lessThan(2000L))
                .body(matchesJsonSchemaInClasspath("shemas/user_shema.json"));
        getUserByIdAndVerifyValueOfFields(randomId, randomUsername, randomPassword);
        userEndpoint.deleteUser(randomId);
    }

    @Test
    public void createTwoAccountsWithIdenticalIdVerifyThatItIsImpossible() {
        Integer randomId = getRandomNumber();
        BodyForUserEndpoint bodyForUser = new BodyForUserEndpoint();
        bodyForUser.setId(randomId).setUserName(getRandomWord())
                .setPassword(getRandomWordWithNumbers());
        Response successfulResponse = userEndpoint.createUser(bodyForUser);
        successfulResponse.then().statusCode(200).time(lessThan(2000L));
        Response failedResponse = userEndpoint.createUser(bodyForUser);
        failedResponse.then()
                .statusCode(400)
                .body("error", equalTo("The id is already exist"))
                .time(lessThan(2000L));
        userEndpoint.deleteUser(randomId);
    }

    @Test(dataProvider = "invalidDataForBodyUsersEndpointPost", dataProviderClass = DataProviderClass.class)
    public void negativeTestCreateAccountWithInvalidBody(Integer id, String userName,
                                                         String password, String errorMessage) {
        BodyForUserEndpoint bodyForUser = new BodyForUserEndpoint();
        bodyForUser.setId(id).setUserName(userName).setPassword(password);
        Response response = userEndpoint.createUser(bodyForUser);
        response.then()
                .statusCode(400)
                .time(lessThan(2000L))
                .body("error", equalTo(errorMessage));
    }

    @Test
    public void updateAccountCheckThatUserDataUpdated() {
        Integer randomId = getRandomNumber();
        String randomUsername = getRandomWord();
        String randomPassword = getRandomWordWithNumbers();
        String updatedUsername = getRandomWord();
        String updatedPassword = getRandomWordWithNumbers();
        BodyForUserEndpoint bodyForUser = new BodyForUserEndpoint();
        bodyForUser.setId(randomId).setUserName(randomUsername).setPassword(randomPassword);
        userEndpoint.createUser(bodyForUser);
        bodyForUser.setUserName(updatedUsername);
        bodyForUser.setPassword(updatedPassword);
        Response response = userEndpoint.updateUser(bodyForUser, randomId);
        response.then()
                .statusCode(200)
                .time(lessThan(2000L))
                .body(matchesJsonSchemaInClasspath("shemas/user_shema.json"));
        getUserByIdAndVerifyValueOfFields(randomId, updatedUsername, updatedPassword);
        userEndpoint.deleteUser(randomId);
    }

    @Test(dataProvider = "invalidDataForBodyUsersEndpointPut", dataProviderClass = DataProviderClass.class)
    public void negativeTestUpdateAccountDataWithInvalidBody(String userName, String password,
                                                             String errorMessage) {
        Integer randomId = getRandomNumber();
        BodyForUserEndpoint bodyForUserPost = new BodyForUserEndpoint();
        bodyForUserPost.setId(randomId).setUserName(getRandomWord()).setPassword(getRandomWord());
        userEndpoint.createUser(bodyForUserPost);
        BodyForUserEndpoint bodyForUserPut = new BodyForUserEndpoint();
        bodyForUserPut.setId(randomId).setUserName(userName).setPassword(password);
        Response response = userEndpoint.updateUser(bodyForUserPut, randomId);
        response.then()
                .statusCode(404)
                .time(lessThan(2000L))
                .body("error", equalTo(errorMessage));
        userEndpoint.deleteUser(randomId);
    }

    @Test
    public void deleteAccountCheckThatAccountDeleted() {
        Integer randomId = getRandomNumber();
        BodyForUserEndpoint bodyForUser = new BodyForUserEndpoint();
        bodyForUser.setId(randomId).setUserName(getRandomWord()).setPassword(getRandomWordWithNumbers());
        userEndpoint.createUser(bodyForUser);
        Response deleteResponse = userEndpoint.deleteUser(randomId);
        deleteResponse.then()
                .statusCode(200)
                .time(lessThan(2000L));
        Response getResponse = userEndpoint.getUser(randomId);
        getResponse.then()
                .statusCode(402)
                .time(lessThan(2000L))
                .body("error", equalTo("The account doesn't exist"));
    }

    @Test
    public void negativeTestDeleteDeletedAccount() {
        Integer randomId = getRandomNumber();
        BodyForUserEndpoint bodyForUser = new BodyForUserEndpoint();
        bodyForUser.setId(randomId).setUserName(getRandomWord()).setPassword(getRandomWordWithNumbers());
        userEndpoint.createUser(bodyForUser);
        Response deleteAccountSuccess = userEndpoint.deleteUser(randomId);
        deleteAccountSuccess.then()
                .statusCode(200)
                .time(lessThan(2000L));
        Response deleteAccountFailed = userEndpoint.deleteUser(randomId);
        deleteAccountFailed.then()
                .statusCode(405)
                .time(lessThan(2000L));
        Response responseGet = userEndpoint.getUser(randomId);
        responseGet.then()
                .statusCode(405)
                .time(lessThan(2000L))
                .body("error", equalTo("The account doesn't exist"));
    }

    @Test(dataProvider = "invalidId", dataProviderClass = DataProviderClass.class)
    public void negativeTestDeleteAccountWithInvalidId(Integer id, String errorMessage) {
        Response response = userEndpoint.deleteUser(id);
        response.then()
                .statusCode(415)
                .time(lessThan(2000L))
                .body("error", equalTo(errorMessage));
    }

    @Test
    public void getAllUsersAndCheckThatResponseNotEmpty() {
        Integer randomIdOne = getRandomNumber();
        BodyForUserEndpoint bodyForFirstUser = new BodyForUserEndpoint();
        bodyForFirstUser.setId(randomIdOne).setUserName(getRandomWord()).setPassword(getRandomWord());
        userEndpoint.createUser(bodyForFirstUser);
        Integer randomIdTwo = getRandomNumber();
        BodyForUserEndpoint bodyForSecondUser = new BodyForUserEndpoint();
        bodyForSecondUser.setId(randomIdTwo).setUserName(getRandomWord()).setPassword(getRandomWord());
        userEndpoint.createUser(bodyForSecondUser);
        Response response = userEndpoint.getUsers();
        response.then()
                .statusCode(200)
                .time(lessThan(2000L))
                .body(matchesJsonSchemaInClasspath("shemas/user_shema.json"));
        UsersDto[] actualResponse = response.as(UsersDto[].class);
        Assert.assertNotEquals(actualResponse.length, 0);
        userEndpoint.deleteUser(randomIdOne);
        userEndpoint.deleteUser(randomIdTwo);
    }

    private void getUserByIdAndVerifyValueOfFields(Integer id, String username, String
            password) {
        Response getResponse = userEndpoint.getUser(id);
        getResponse.then().statusCode(200).time(lessThan(2000L));
        UsersDto actualGetResponse = getResponse.as(UsersDto.class);
        softAssert.assertEquals(actualGetResponse.getId(), id);
        softAssert.assertEquals(actualGetResponse.getUserName(), username);
        softAssert.assertEquals(actualGetResponse.getPassword(), password);
    }
}
