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

    static final String BASE_URL = "/api/event-record";
    String baseUri;

    @Autowired
    private TestRestTemplate testRestTemplate;

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

        var location = createEventRecordAndReturnLocation(baseUri);

        deleteEventRecord(location);
    }

    @Test
    void shouldCreateAndDeleteEventRecord() {

        var location = createEventRecordAndReturnLocation(baseUri);

        deleteEventRecord(location);
    }

    String createEventRecordAndReturnLocation(String baseUri) {
        return RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new EventRecordWriteDto()
                            .setEventId(1L)
                            .setParticipantId(1L))
                .when()
                    .post(baseUri)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                .extract()
                    .header("location");
    }

    void deleteEventRecord(String location) {

        RestAssured
                .when()
                .delete(location)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
