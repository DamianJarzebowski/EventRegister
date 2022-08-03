package dj.eventregister.event_test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;


import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerTest {

    public static final String BASE_URL = "/api/event";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void getTest() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured.get(uri + "/1")
                .then()
                .body(Matchers.containsString("{\"id\":1,\"name\":\"Test Event\",\"description\":\"Description Test Event\",\"maxParticipant\":3,\"minParticipant\":1,\"currentParticipants\":0,\"majority\":true,\"dateTime\":\"2999-11-11T14:00:00\",\"category\":\"TestCategory\"}"));
        RestAssured.get(uri)
                .then()
                .body("name", Matchers.hasItem("Test Event"))
                .body("description", Matchers.hasItem("Description Test Event"))
                .body("maxParticipant", Matchers.hasItem(3))
                .body("minParticipant", Matchers.hasItem(1));
    }

    @Test
     void testPost() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("{\"category\": \"Taniec\", \"dateTime\": \"2022-07-16T00:00:00.604Z\", \"description\": \"EventDescription\", \"majority\": true, \"maxParticipant\": 10, \"minParticipant\": 5, \"name\": \"EventName\"}")
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
                    .body("{\"id\": 1, \"category\": \"TestCategory\", \"dateTime\": \"2999-11-11T14:00:00.604Z\", \"description\": \"PUT Description Test Event\", \"majority\": true, \"maxParticipant\": 3, \"minParticipant\": 1, \"name\": \"Put Test Event\"}")
                .when()
                    .put(uri + "/1")
                .then()
                    .statusCode(200);
    }

    @Test
    void testDelete() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured
                .given()
                .when()
                    .delete(uri + "/1")
                .then()
                    .statusCode(204);
    }
}
