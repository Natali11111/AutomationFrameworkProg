package restassured_api_tests;

import bodies_dto.BodyForAuthorEndpoint;
import endpoints.AuthorEndpoint;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import responses_dto.AuthorsDto;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static utils.GenerateData.getRandomNumber;
import static utils.GenerateData.getRandomWord;

public class ApiTestsForAuthors {
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
        Integer randomBookId = getRandomNumber();
        String randomFirstName = getRandomWord();
        String randomLastName = getRandomWord();
        BodyForAuthorEndpoint bodyForCreatingAuthor = new BodyForAuthorEndpoint();
        bodyForCreatingAuthor.setId(randomId).setBookId(randomBookId)
                .setFirstName(randomFirstName).setLastName(randomLastName);
        Response successfulResponse = authorEndpoint.createAuthor(bodyForCreatingAuthor);
        successfulResponse.then().statusCode(200).time(lessThan(2000L));
        Response failedResponse = authorEndpoint.createAuthor(bodyForCreatingAuthor);
        failedResponse.then().statusCode(400).body("error", equalTo("The id is already exist"))
                .time(lessThan(2000L));
        authorEndpoint.deleteAuthor(randomId);
    }

    @Test(dataProvider = "invalidDataForBody")
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
        BodyForAuthorEndpoint bodyForAuthor = new BodyForAuthorEndpoint();
        bodyForAuthor.setId(randomId).setBookId(randomBookId)
                .setFirstName(randomFirstName).setLastName(randomLastName);
        authorEndpoint.createAuthor(bodyForAuthor);
        bodyForAuthor.setFirstName(updatedFirstName);
        Response response = authorEndpoint.updateAuthor(bodyForAuthor, randomId);
        response.then().statusCode(200).time(lessThan(2000L))
                .body(matchesJsonSchemaInClasspath("shemas/author_shema.json"));
        getAuthorByIdCheckValueOfFields(randomId, randomBookId, updatedFirstName, randomLastName);
        authorEndpoint.deleteAuthor(randomId);
    }

    @Test(dataProvider = "invalidDataForBody")
    public void negativeTestUpdateAuthorDataWithInvalidBody(Integer id, Integer bookId,
                                                            String firstName, String lastName,
                                                            String errorMessage) {
        BodyForAuthorEndpoint bodyForAuthor = new BodyForAuthorEndpoint();
        bodyForAuthor.setId(id).setBookId(bookId)
                .setFirstName(firstName).setLastName(lastName);
        Response response = authorEndpoint.updateAuthor(bodyForAuthor, id);
        response.then().statusCode(404).body("error", equalTo(errorMessage))
                .time(lessThan(2000L));
    }

    @Test
    public void deleteAuthorCheckThatAuthorDeleted() {
        Integer randomId = getRandomNumber();
        Integer randomBookId = getRandomNumber();
        String randomFirstName = getRandomWord();
        String randomLastName = getRandomWord();
        BodyForAuthorEndpoint bodyForCreatingAuthor = new BodyForAuthorEndpoint();
        bodyForCreatingAuthor.setId(randomId).setBookId(randomBookId)
                .setFirstName(randomFirstName).setLastName(randomLastName);
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
        Integer randomBookId = getRandomNumber();
        String randomFirstName = getRandomWord();
        String randomLastName = getRandomWord();
        BodyForAuthorEndpoint bodyForCreatingAuthor = new BodyForAuthorEndpoint();
        bodyForCreatingAuthor.setId(randomId).setBookId(randomBookId)
                .setFirstName(randomFirstName).setLastName(randomLastName);
        authorEndpoint.createAuthor(bodyForCreatingAuthor);
        Integer randomIdForSecondAuthor = getRandomNumber();
        Integer randomBookIdForSecondAuthor = getRandomNumber();
        String randomFirstNameForSecondAuthor = getRandomWord();
        String randomLastNameForSecondAuthor = getRandomWord();
        BodyForAuthorEndpoint bodyForCreatingSecondAuthor = new BodyForAuthorEndpoint();
        bodyForCreatingSecondAuthor.setId(randomIdForSecondAuthor).setBookId(randomBookIdForSecondAuthor)
                .setFirstName(randomFirstNameForSecondAuthor).setLastName(randomLastNameForSecondAuthor);
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
