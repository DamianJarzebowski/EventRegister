package dj.eventregister.event_test;

import dj.eventregister.models.event.dto.EventWriteDto;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventServiceImplTest {

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
                .body(new EventWriteDto()
                        .setName("TestEventName")
                        .setDescription("TestDescription")
                        .setCategory("NoExistingCategory")
                        .setMajority(true)
                        .setMaxParticipant(3)
                        .setMinParticipant(1)
                        .setDateTime(LocalDateTime.of(2222, 12, 31, 23, 59, 59)))
                .when()
                .post(baseUri)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
