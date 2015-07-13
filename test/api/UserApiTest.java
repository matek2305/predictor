package api;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.CREATED;
import static play.mvc.Http.Status.OK;

/**
 * User API tests.
 *
 * @author Mateusz Urbanski <matek2305@gmail.com>
 */
public class UserApiTest extends PredictorApiTestCase {

    /**
     * Test case for registering new user and log in as him scenario.
     */
    @Test
    public void testRegisterUserAndLogin() {

        // register user
        given()
                .contentType(JSON)
                .body("{\"login\":\"arthas\",\"password\":\"frostmourne\"}")
        .expect()
                .statusCode(CREATED)
                .body("id", notNullValue(Long.class))
                .body("login", equalTo("arthas"))
        .when()
                .post("/api/user/register");

        // login and save token
        String token = given()
                .contentType(JSON)
                .body("{\"login\":\"arthas\",\"password\":\"frostmourne\"}")
        .expect()
                .statusCode(CREATED)
                .body("name", equalTo("arthas"))
                .body("authenticationToken", notNullValue(String.class))
        .when()
                .post("/api/user/login")
        .then()
                .extract().path("authenticationToken");

        // logout
        given().header("Predictor-Authentication-Token", token).expect().statusCode(OK).when().post("/api/user/logout");
    }

    /**
     * Test case for registering user with already existed username scenario.
     */
    @Test
    public void testRegisterUserWithExisitingUsername() {
        given()
                .contentType(JSON)
                .body("{\"login\":\"arthas\",\"password\":\"frostmourne\"}")
        .expect()
                .statusCode(CREATED)
                .body("id", notNullValue(Long.class))
                .body("login", equalTo("arthas"))
        .when()
                .post("/api/user/register");

        given()
                .contentType(JSON)
                .body("{\"login\":\"arthas\",\"password\":\"azeroth\"}")
        .expect()
                .statusCode(BAD_REQUEST)
                .header("Predictor-Status-Reason", "Validation failed")
//                .body("messages", hasItem("nazwa użytkownika jest już zajęta"))
        .when()
                .post("/api/user/register");
    }
}
