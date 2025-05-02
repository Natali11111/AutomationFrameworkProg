package restassured_api_tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static randomaizer.GenerateData.getRandomNumber;
import static randomaizer.GenerateData.getRandomWord;

import api_tests.bodies.BodyForAuthorEndpoint;
import api_tests.endpoints.AuthorEndpoint;
import api_tests.responses.AuthorsDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class ApiTestsForAuthors {
    SoftAssert softAssert = new SoftAssert();
    AuthorEndpoint authorEndpoint = new AuthorEndpoint();
    Integer randomId = getRandomNumber();


    @Test
    public void createAuthorAndVerifyItExists() {
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
                .header("Content-Type", "application/json; charset=utf-8; v=1.0")
                .body(matchesJsonSchemaInClasspath("shemas/author_shema.json"));
        AuthorsDto actualPostResponse = postResponse.as(AuthorsDto.class);
        verifyValuesFromBody(actualPostResponse, randomId, randomBookId, randomFirstName, randomLastName);
        getAuthorById(randomId, randomBookId, randomFirstName, randomLastName);
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
        successfulResponse.then().statusCode(200).time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0");
        Response failedResponse = authorEndpoint.createAuthor(bodyForCreatingAuthor);
        failedResponse.then().statusCode(400).body("error", equalTo("The id is already exist"))
                .time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0");
    }

    @Test(dataProvider = "invalidDataForBody")
    public void negativeTestCreateAuthorWithInvalidBody(Integer id, Integer bookId, String firstName,
                                                        String lastName, String errorMessage) {
        BodyForAuthorEndpoint bodyForCreatingAuthor = new BodyForAuthorEndpoint();
        bodyForCreatingAuthor.setId(id).setBookId(bookId)
                .setFirstName(firstName).setLastName(lastName);
        Response response = authorEndpoint.createAuthor(bodyForCreatingAuthor);
        response.then().statusCode(400).body("error", equalTo(errorMessage))
                .time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0");
    }

    @Test
    public void updateAuthorDataCheckThatAuthorDataUpdated() {
        Integer newRandomId = getRandomNumber();
        Integer randomBookId = getRandomNumber();
        String randomFirstName = getRandomWord();
        String randomLastName = getRandomWord();
        BodyForAuthorEndpoint bodyForAuthor = new BodyForAuthorEndpoint();
        bodyForAuthor.setId(newRandomId).setBookId(randomBookId)
                .setFirstName(randomFirstName).setLastName(randomLastName);
        Response response = authorEndpoint.updateAuthor(bodyForAuthor, randomId);
        response.then().statusCode(200).time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0")
                .body(matchesJsonSchemaInClasspath("shemas/author_shema.json"));
        AuthorsDto actualResponse = response.as(AuthorsDto.class);
        verifyValuesFromBody(actualResponse, newRandomId, randomBookId, randomFirstName,
                randomLastName);
        getAuthorById(newRandomId, randomBookId, randomFirstName, randomLastName);
    }

    @Test(dataProvider = "invalidDataForBody")
    public void negativeTestUpdateAuthorDataWithInvalidBody(Integer id, Integer bookId,
                                                            String firstName, String lastName,
                                                            String errorMessage) {
        BodyForAuthorEndpoint bodyForAuthor = new BodyForAuthorEndpoint();
        bodyForAuthor.setId(id).setBookId(bookId)
                .setFirstName(firstName).setLastName(lastName);
        Response response = authorEndpoint.updateAuthor(bodyForAuthor, randomId);
        response.then().statusCode(404).body("error", equalTo(errorMessage))
                .time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0");
    }

    @Test
    public void deleteAuthorCheckThatAuthorDeleted() {
        Response deleteResponse = authorEndpoint.deleteAuthor(randomId);
        deleteResponse.then().statusCode(200).time(lessThan(2000L));
        Response getResponse = authorEndpoint.getAuthor(randomId);
        getResponse.then().statusCode(404).body("error", equalTo("Author" +
                        "doesn't exist")).time(lessThan(2000L))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0");
    }

    @Test
    public void getAllAuthorsCheckThatResponseIsNotEmpty() {
        Response response = authorEndpoint.getAuthors();
        response.then().statusCode(200).time(lessThan(2000L))
                .body(matchesJsonSchemaInClasspath("shemas/author_shema.json"))
                .header("Content-Type", "application/json; charset=utf-8; v=1.0");
        AuthorsDto[] authors = response.as(AuthorsDto[].class);
        Assert.assertNotEquals(authors.length, 0);
    }

    private void getAuthorById(Integer id, Integer bookId, String firstName, String
            lastName) {
        Response getResponse = authorEndpoint.getAuthor(randomId);
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

    @DataProvider
    public Object[][] invalidDataForBody() {
        return new Object[][]{
                {-1, 3950, "Alan", "Wake", "Id can't be negative"},
                {0, 3950, "Alan", "Wake", "Id can't be zero"},
                {192, 0, "Alan", "Wake", "BookId can't be zero"},
                {192, -1, "Alan", "Wake", "BookId can't be negative"},
                {293, 123405, "", "Wake", "Name can't be empty"},
                {293, 123405, "Alan", "", "Lastname can't be empty"},
                {293, 123405, "", "", "Name and lastname can't be empty"},
                {293, 123405, "*%)(#", "Wake", "Name can't has special symbols"},
                {293, 123405, "Alan", "+_+_+_", "Lastname can't has special symbols"},
                {293, 123405, "111111", "Wake", "Name can't has numbers"},
                {293, 123405, "Alan", "000000", "Lastname can't has numbers"},
                {293, 123405, "Alan", "000000", "Lastname can't has numbers"},
                //don't send required fields// send required fields + not required fields
                // (it should be empty) to make sure they are really optional
                // business logic for example (we can't make age negative, or too big 150)
                // we can't hand over special symbol, numbers when we work with string
                //we can't hand over empty body(we get java error)
                //we can't hand over additional non-existent fields(get java error)
                //we can't check validation of data(java won't let you do it)
                //if we send body as a java object
                //also we shouldn't check body parameters if we work with body as
                //a java object
        };
    }

}
