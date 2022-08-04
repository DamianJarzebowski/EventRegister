package dj.eventregister.event_test;

import dj.eventregister.event.EventReadDto;
import dj.eventregister.event.EventWriteDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;


import java.net.URI;
import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerTest {

    public static final String BASE_URL = "/api/event";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldCreateAndGetEvent() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        var location = createEventAndReturnLocation(uri);

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
     void shouldCreateEvent() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                .body(new EventWriteDto()
                        .setName("TestEventName")
                        .setDescription("TestDescription")
                        .setCategory("Taniec")
                        .setMajority(true)
                        .setMaxParticipant(3)
                        .setMinParticipant(1)
                        .setDateTime(LocalDateTime.now()))
                .when()
                    .post(uri)
                .then()
                    .statusCode(201);
    }

    @Test
    void shouldCreateAndUpdateEvent() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        var location = createEventAndReturnLocation(uri);

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
                    .statusCode(200);
    }

    @Test
    void shouldCreateAndDeleteEvent() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        String location = createEventAndReturnLocation(uri);

        RestAssured
                .given()
                .when()
                    .delete(location)
                .then()
                    .statusCode(204);
    }

    private String createEventAndReturnLocation(String uri) {

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
                        .post(uri)
                        .header("location");
    }
}
