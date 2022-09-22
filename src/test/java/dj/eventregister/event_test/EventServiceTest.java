package dj.eventregister.event_test;

import dj.eventregister.event.dto.EventWriteDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;
import java.time.LocalDateTime;

import static dj.eventregister.event_test.TestMethods.dateForCreateEvent;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventServiceTest {

    public static final String BASE_URL = "/api/event";
    String baseUri;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldValidateCategory() {

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(dateForCreateEvent
                        .setCategory("NoExistingCategory"))
                .when()
                .post(baseUri)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
