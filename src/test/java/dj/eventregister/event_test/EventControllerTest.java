package dj.eventregister.event_test;

import dj.eventregister.event.EventReadDto;
import dj.eventregister.event.EventWriteDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;


import java.net.URI;
import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerTest {

    public static final String BASE_URL = "/api/event";
    String baseUri;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldCreateAndGetEvent() {

        var location = createEventAndReturnLocation(baseUri);

        var actual = RestAssured
                .given()
                .headers("Content-Type", ContentType.JSON)
                .get(location)
                .as(EventReadDto.class);

        var expected = new EventReadDto()
                .setId(actual.getId())
                .setName("TestEventName")
                .setDescription("TestDescription")
                .setCategory("Taniec")
                .setMajority(true)
                .setMaxParticipant(3)
                .setMinParticipant(1)
                .setDateTime(LocalDateTime.of(2222, 12, 31, 23, 59, 59));

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldCreateAndUpdateEvent() {

        var location = createEventAndReturnLocation(baseUri);

        var actual = RestAssured
                .given()
                .headers("Content-Type", ContentType.JSON)
                .get(location)
                .as(EventReadDto.class);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new EventReadDto()
                            .setId(actual.getId())
                            .setName("UpdateName")
                            .setDescription("UpdateDescription")
                            .setCategory("Pływanie")
                            .setMajority(false)
                            .setMaxParticipant(4)
                            .setMinParticipant(2)
                            .setDateTime(LocalDateTime.now()))
                .when()
                    .put(location)
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void shouldCreateAndDeleteEvent() {

        var location = createEventAndReturnLocation(baseUri);

        deleteEvent(location);

        RestAssured
                .given()
                    .get(location)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
    }

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

    String createEventAndReturnLocation(String baseUri) {

            return RestAssured
                    .given()
                        .contentType(ContentType.JSON)
                        .body(new EventWriteDto()
                            .setName("TestEventName")
                            .setDescription("TestDescription")
                            .setCategory("Taniec")
                            .setMajority(true)
                            .setMaxParticipant(3)
                            .setMinParticipant(1)
                            .setDateTime(LocalDateTime.of(2222, 12, 31, 23, 59, 59)))
                    .when()
                        .post(baseUri)
                    .then()
                        .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                        .header("location");
    }

    void deleteEvent(String location) {

        RestAssured
                .when()
                    .delete(location)
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
