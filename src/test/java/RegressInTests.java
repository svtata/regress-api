import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RegressInTests {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @DisplayName("Successful login")
    @Test
    void loginTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/login")
                .then()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @DisplayName("Unsuccessful login")
    @Test
    void negativeLoginTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";
        given()
                .when()
                .post("/login")
                .then()
                .log().body()
                .statusCode(415);
    }

    //Homework 18
    @DisplayName("Create user")
    @Test
    void createUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("id", notNullValue());
    }

    @DisplayName("Update user")
    @Test
    void updateUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .patch("/users/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("updatedAt", notNullValue());
    }

    @DisplayName("Delete user")
    @Test
    void deleteUserTest() {
        given()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .statusCode(204);
    }

    @DisplayName("Get users list")
    @Test
    void getUserListTest() {
        given()
                .when()
                .get("/users?pages=2")
                .then()
                .log().body()
                .statusCode(200)
                .body("total", is(12));
    }

    @DisplayName("Check page number of users list")
    @Test
    void checkPageNumberTest() {
        int page = 1;
        given()
                .when()
                .get("/users?pages=" + page)
                .then()
                .log().body()
                .statusCode(200)
                .body("page", is(page));
    }

}
