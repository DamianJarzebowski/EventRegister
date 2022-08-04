package dj.eventregister.eventrecord_test;

import dj.eventregister.eventrecord.EventRecordReadDto;
import dj.eventregister.eventrecord.EventRecordWriteDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventRecordControllerTest {

    public static final String BASE_URL = "/api/event-record";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldCreateAndGetEventRecord() {

        var uri = URI.create((testRestTemplate.getRootUri()) + BASE_URL);

        var location = RestAssured
                .with()
                    .contentType(ContentType.JSON)
                .body(new EventRecordWriteDto()
                        .setEventId(1L)
                        .setParticipantId(1L))
                .when()
                    .post(uri)
                    .header("location");

        var actual = RestAssured
                .given().headers("Content-Type", ContentType.JSON)
                .get(location)
                .as(EventRecordReadDto.class);

        var expected = new EventRecordReadDto()
                .setId(actual.getId())
                .setEventId(1L)
                .setParticipantId(1L);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldCreateEventRecord() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new EventRecordWriteDto()
                            .setEventId(1L)
                            .setParticipantId(1L))
                .when()
                    .post(uri)
                .then()
                    .statusCode(201);
    }

    @Test
    void shouldCreateAndDeleteEventRecord() {

        shouldCreateEventRecord();

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured
                .when()
                    .delete(uri + "/1")
                .then()
                    .statusCode(204);
    }

}
