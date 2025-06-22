package restassured_api_tests;

import bodies_dto.BodyForAuthorEndpoint;
import endpoints.AuthorEndpoint;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import responses_dto.AuthorsDto;
import test_data.DataProviderClass;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static utils.GenerateData.getRandomNumber;
import static utils.GenerateData.getRandomWord;

public class ApiTestsForAuthorsEndpoint {
    SoftAssert softAssert = new SoftAssert();
    AuthorEndpoint authorEndpoint = new AuthorEndpoint();

    @Test
    public void createAuthorAndVerifyItExists() {
        Integer randomId = getRandomNumber();
        Integer randomBookId = getRandomNumber();
        String randomFirstName = getRandomWord();
        String randomLastName = getRandomWord();
        BodyForAuthorEndpoint bodyForCreatingAuthor = new BodyForAuthorEndpoint();
        bodyForCreatingAuthor.setId(randomId).setBookId(randomBookId)
                .setFirstName(randomFirstName).setLastName(randomLastName);
        Response postResponse = authorEndpoint.createAuthor(bodyForCreatingAuthor);
        postResponse.then()
                .statusCode(200)
                .time(lessThan(2000L))
                .body(matchesJsonSchemaInClasspath("shemas/author_shema.json"));
        getAuthorByIdCheckValueOfFields(randomId, randomBookId, randomFirstName, randomLastName);
        authorEndpoint.deleteAuthor(randomId);
    }

    @Test
    public void createTwoAuthorWithIdenticalIdVerifyThatItIsImpossible() {
        Integer randomId = getRandomNumber();
        BodyForAuthorEndpoint bodyForCreatingAuthor = new BodyForAuthorEndpoint();
        bodyForCreatingAuthor.setId(randomId).setBookId(getRandomNumber())
                .setFirstName(getRandomWord()).setLastName(getRandomWord());
        Response successfulResponse = authorEndpoint.createAuthor(bodyForCreatingAuthor);
        successfulResponse.then().statusCode(200).time(lessThan(2000L));
        Response failedResponse = authorEndpoint.createAuthor(bodyForCreatingAuthor);
        failedResponse.then().statusCode(400).body("error", equalTo("The id is already exist"))
                .time(lessThan(2000L));
        authorEndpoint.deleteAuthor(randomId);
    }

    @Test(dataProvider = "invalidDataForBodyAuthorsEndpointPost", dataProviderClass = DataProviderClass.class)
    public void negativeTestCreateAuthorWithInvalidBody(Integer id, Integer bookId, String firstName,
                                                        String lastName, String errorMessage) {
        BodyForAuthorEndpoint bodyForCreatingAuthor = new BodyForAuthorEndpoint();
        bodyForCreatingAuthor.setId(id).setBookId(bookId)
                .setFirstName(firstName).setLastName(lastName);
        Response response = authorEndpoint.createAuthor(bodyForCreatingAuthor);
        response.then().statusCode(400).body("error", equalTo(errorMessage))
                .time(lessThan(2000L));
    }

    @Test
    public void updateAuthorDataCheckThatAuthorDataUpdated() {
        Integer randomId = getRandomNumber();
        Integer randomBookId = getRandomNumber();
        String randomFirstName = getRandomWord();
        String randomLastName = getRandomWord();
        String updatedFirstName = getRandomWord();
        String updatedLastName = getRandomWord();
        BodyForAuthorEndpoint bodyForAuthor = new BodyForAuthorEndpoint();
        bodyForAuthor.setId(randomId).setBookId(getRandomNumber())
                .setFirstName(randomFirstName).setLastName(randomLastName);
        authorEndpoint.createAuthor(bodyForAuthor);
        bodyForAuthor.setFirstName(updatedFirstName).setLastName(updatedLastName);
        Response response = authorEndpoint.updateAuthor(bodyForAuthor, randomId);
        response.then().statusCode(200).time(lessThan(2000L))
                .body(matchesJsonSchemaInClasspath("shemas/author_shema.json"));
        getAuthorByIdCheckValueOfFields(randomId, randomBookId, updatedFirstName, updatedLastName);
        authorEndpoint.deleteAuthor(randomId);
    }

    @Test(dataProvider = "invalidDataForBody")
    public void negativeTestUpdateAuthorDataWithInvalidBody(Integer bookId,
                                                            String firstName, String lastName,
                                                            String errorMessage) {
        Integer randomId = getRandomNumber();
        BodyForAuthorEndpoint bodyForAuthorPost = new BodyForAuthorEndpoint();
        bodyForAuthorPost.setId(randomId).setBookId(bookId)
                .setFirstName(firstName).setLastName(lastName);
        authorEndpoint.createAuthor(bodyForAuthorPost);
        BodyForAuthorEndpoint bodyForAuthorPut = new BodyForAuthorEndpoint();
        bodyForAuthorPut.setId(randomId).setBookId(bookId)
                .setFirstName(firstName).setLastName(lastName);
        Response response = authorEndpoint.updateAuthor(bodyForAuthorPut, randomId);
        response.then().statusCode(404).body("error", equalTo(errorMessage))
                .time(lessThan(2000L));
        authorEndpoint.deleteAuthor(randomId);
    }

    @Test
    public void deleteAuthorCheckThatAuthorDeleted() {
        Integer randomId = getRandomNumber();
        BodyForAuthorEndpoint bodyForCreatingAuthor = new BodyForAuthorEndpoint();
        bodyForCreatingAuthor.setId(randomId).setBookId(getRandomNumber())
                .setFirstName(getRandomWord()).setLastName(getRandomWord());
        authorEndpoint.createAuthor(bodyForCreatingAuthor);
        Response deleteResponse = authorEndpoint.deleteAuthor(randomId);
        deleteResponse.then().statusCode(200).time(lessThan(2000L));
        Response getResponse = authorEndpoint.getAuthor(randomId);
        getResponse.then().statusCode(404).body("error", equalTo("Author" +
                "doesn't exist")).time(lessThan(2000L));
    }

    @Test
    public void getAllAuthorsCheckThatResponseIsNotEmpty() {
        Integer randomId = getRandomNumber();
        BodyForAuthorEndpoint bodyForCreatingAuthor = new BodyForAuthorEndpoint();
        bodyForCreatingAuthor.setId(randomId).setBookId(getRandomNumber())
                .setFirstName(getRandomWord()).setLastName(getRandomWord());
        authorEndpoint.createAuthor(bodyForCreatingAuthor);
        Integer randomIdForSecondAuthor = getRandomNumber();
        BodyForAuthorEndpoint bodyForCreatingSecondAuthor = new BodyForAuthorEndpoint();
        bodyForCreatingSecondAuthor.setId(randomIdForSecondAuthor).setBookId(getRandomNumber())
                .setFirstName(getRandomWord()).setLastName(getRandomWord());
        authorEndpoint.createAuthor(bodyForCreatingSecondAuthor);
        Response response = authorEndpoint.getAuthors();
        response.then().statusCode(200).time(lessThan(2000L))
                .body(matchesJsonSchemaInClasspath("shemas/author_shema.json"));
        AuthorsDto[] authors = response.as(AuthorsDto[].class);
        Assert.assertNotEquals(authors.length, 0);
        authorEndpoint.deleteAuthor(randomId);
        authorEndpoint.deleteAuthor(randomIdForSecondAuthor);
    }

    private void getAuthorByIdCheckValueOfFields(Integer id, Integer bookId, String firstName, String
            lastName) {
        Response getResponse = authorEndpoint.getAuthor(id);
        getResponse.then().statusCode(200).time(lessThan(2000L));
        AuthorsDto actualResponse = getResponse.as(AuthorsDto.class);
        verifyValuesFromBody(actualResponse, id, bookId, firstName, lastName);
    }

    public void verifyValuesFromBody(AuthorsDto actualResponse, Integer id,
                                     Integer bookId, String firstName, String
                                             lastName) {
        softAssert.assertEquals(actualResponse.getId(), id);
        softAssert.assertEquals(actualResponse.getIdBook(), bookId);
        softAssert.assertEquals(actualResponse.getFirstName(), firstName);
        softAssert.assertEquals(actualResponse.getLastName(), lastName);
    }
}
