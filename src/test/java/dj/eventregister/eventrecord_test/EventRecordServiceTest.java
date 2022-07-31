package dj.eventregister.eventrecord_test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventRecordServiceTest {

    public static final String BASE_URL = "/api/event-record";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void getTest() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured.get(uri + "/1")
                .then()
            .body(Matchers.containsString("{\"id\":1,\"eventId\":1,\"participantId\":1}"));
    }

    @Test
    void testPost() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("{\"eventId\":1, \"participantId\":2}")
                .when()
                    .post(uri)
                .then()
                    .statusCode(201);
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
