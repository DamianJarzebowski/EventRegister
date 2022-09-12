package dj.eventregister.eventrecord_test;

import dj.eventregister.eventrecord.dto.EventRecordReadDto;
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

import static dj.eventregister.eventrecord_test.TestMethods.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventRecordControllerTest {
    
    String baseUri;
    String participantLocation;
    String eventLocation;
    String eventRegisterLocation;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()).toString();
        participantLocation = createParticipant(baseUri);
        eventLocation = createEvent(baseUri);
        eventRegisterLocation = createEventRecordAndReturnLocation(baseUri, participantLocation, eventLocation);
    }

    @Test
    void shouldCreateAndGetEventRecord() {

        var actual = RestAssured
                .given()
                        .headers("Content-Type", ContentType.JSON)
                        .get(eventRegisterLocation)
                        .as(EventRecordReadDto.class);

        var expected = new EventRecordReadDto()
                .setId(actual.getId())
                .setEventId(actual.getEventId())
                .setParticipantId(actual.getParticipantId());

        Assertions.assertThat(actual).isEqualTo(expected);

        deleteEventRecord(eventRegisterLocation);
    }

    @Test
    void shouldCreateAndDeleteEventRecord() {

        deleteEventRecord(eventRegisterLocation);

        RestAssured
                .given()
                    .get(eventRegisterLocation)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
