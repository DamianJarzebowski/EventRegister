package dj.eventregister.eventrecord_test;

import dj.eventregister.eventrecord.EventRecordReadDto;
import dj.eventregister.eventrecord.EventRecordWriteDto;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventRecordControllerTest {

    public static final String BASE_URL = "/api/event-record";

    @Autowired
    private TestRestTemplate testRestTemplate;

    String baseUri;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;
    }

    @Test
    void shouldCreateAndGetEventRecord() {

        var location = createEventRecordAndReturnLocation(baseUri);

        var actual = RestAssured
                .given()
                    .headers("Content-Type", ContentType.JSON)
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

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new EventRecordWriteDto()
                            .setEventId(1L)
                            .setParticipantId(1L))
                .when()
                    .post(baseUri)
                .then()
                    .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    void shouldCreateAndDeleteEventRecord() {

        var location = createEventRecordAndReturnLocation(baseUri);

        RestAssured
                .when()
                    .delete(location)
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    String createEventRecordAndReturnLocation(String uri) {
        return RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new EventRecordWriteDto()
                            .setEventId(1L)
                            .setParticipantId(1L))
                .when()
                    .post(uri)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                .extract()
                    .header("location");
    }

}
