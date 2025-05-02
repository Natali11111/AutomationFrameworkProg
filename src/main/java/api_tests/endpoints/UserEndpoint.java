package api_tests.endpoints;

import api_tests.bodies.BodyForUserEndpoint;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;

public class UserEndpoint {

    public Response createUser(BodyForUserEndpoint bodyForUser) {
        return given()
                .contentType(ContentType.JSON)
                .body(bodyForUser)
                .when()
                .post(BASE_URL + BASE_PATH_FOR_USERS);
    }

    public Response getUser(Integer userId) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + BASE_PATH_FOR_USERS + userId);
    }

    public Response getUsers() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + BASE_PATH_FOR_USERS);
    }

    public Response updateUser(BodyForUserEndpoint bodyForUser, Integer userId) {
        return given()
                .contentType(ContentType.JSON)
                .body(bodyForUser)
                .when()
                .put(BASE_URL + BASE_PATH_FOR_USERS + userId);
    }

    public Response deleteUser(Integer userId) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete(BASE_URL + BASE_PATH_FOR_USERS + userId);
    }
}
