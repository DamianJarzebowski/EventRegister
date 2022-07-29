package dj.eventregister.participant_test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParticipantControllerTest {

    public static final String BASE_URL = "/api/participants";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void postTest() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("{\"name\": \"CreateTest\", \"last_name\": \"Participant\", \"age\": \"99\", \"email\": \"createTestEamil@o2.pl\"}")
                .when()
                    .post(uri)
                .then()
                    .statusCode(201);
    }

    @Test
    void testPut() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("{\"id\": 1, \"name\": \"Test\", \"last_name\": \"Participant\", \"age\": \"99\", \"email\": \"test@o2.pl\"}")
                .when()
                    .put(uri + "/1")
                .then()
                    .statusCode(200);
    }

    @Test
    void getTest() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured.get(uri + "/1")
                .then()
                .body(Matchers.containsString("{\"id\": 1,\"name\":\"Test\",\"lastName\":\"User\",\"age\":99,\"email\":\"test@o2.pl\"}"));

        RestAssured.get(uri)
                .then()
                .body("name", Matchers.hasItem("Test"))
                .body("lastName", Matchers.hasItem("Participant"))
                .body("age", Matchers.hasItem(99))
                .body("email", Matchers.hasItem("test@o2.pl"));
    }

    @Test
    void deleteTest() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured
                    .given()
                    .when()
                .delete(uri + "/1")
                    .then()
                .statusCode(204);
    }
}
