package endpoints;

import bodies_dto.BodyForAuthorEndpoint;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.Constants.BASE_PATH_FOR_AUTHORS;
import static constants.Constants.BASE_URL;
import static io.restassured.RestAssured.given;

public class AuthorEndpoint {

    public Response createAuthor(BodyForAuthorEndpoint bodyForAuthor) {
        return given()
                .contentType(ContentType.JSON)
                .body(bodyForAuthor)
                .when()
                .post(BASE_URL + BASE_PATH_FOR_AUTHORS);
    }

    public Response getAuthor(Integer authorId) {
        return given()
                .when()
                .get(BASE_URL + BASE_PATH_FOR_AUTHORS + authorId);
    }

    public Response getAuthors() {
        return given()
                .when()
                .get(BASE_URL + BASE_PATH_FOR_AUTHORS);
    }

    public Response updateAuthor(BodyForAuthorEndpoint bodyForAuthor, Integer authorId) {
        return given()
                .contentType(ContentType.JSON)
                .body(bodyForAuthor)
                .when()
                .put(BASE_URL + BASE_PATH_FOR_AUTHORS + authorId);
    }

    public Response deleteAuthor(Integer authorId) {
        return given()
                .when()
                .delete(BASE_URL + BASE_PATH_FOR_AUTHORS + authorId);
    }
}
