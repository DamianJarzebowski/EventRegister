package dj.eventregister.eventrecord_test;

import dj.eventregister.event.EventReadDto;
import dj.eventregister.event.EventWriteDto;
import dj.eventregister.eventrecord.EventRecordReadDto;
import dj.eventregister.eventrecord.EventRecordWriteDto;
import dj.eventregister.participant.ParticipantReadDto;
import dj.eventregister.participant.ParticipantWriteDto;
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
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventRecordControllerTest {

    static final String EVENT_RECORD_URL = "/api/event-record";
    static final String PARTICIPANT_URL = "/api/participants";
    public static final String EVENT_URL = "/api/event";
    
    String baseUri;
    String participantLocation;
    String eventLocation;
    String eventRegisterLocation;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()).toString();
        participantLocation = createParticipant();
        eventLocation = createEvent();
        eventRegisterLocation = createEventRecordAndReturnLocation(baseUri);
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

    String createEventRecordAndReturnLocation(String baseUri) {

        var actualParticipant = RestAssured
                .given()
                    .headers("Content-Type", ContentType.JSON)
                    .get(participantLocation)
                    .as(ParticipantReadDto.class);

        var actualEvent = RestAssured
                .given()
                    .headers("Content-Type", ContentType.JSON)
                    .get(eventLocation)
                    .as(EventReadDto.class);

        return RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new EventRecordWriteDto()
                            .setEventId(actualEvent.getId())
                            .setParticipantId(actualParticipant.getId()))
                .when()
                    .post(baseUri + EVENT_RECORD_URL)
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

    String createParticipant() {

        Random random = new Random();
        final int rangePrefixNumber = 999;

        return RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new ParticipantWriteDto()
                            .setName("TestName")
                            .setLastName("TestLastName")
                            .setAge(18)
                            .setEmail(random.nextInt(rangePrefixNumber) + "testEmail@gmail.com"))
                .when()
                    .post(baseUri + PARTICIPANT_URL)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                .extract()
                    .header("location");
    }

    String createEvent() {
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
                    .post(baseUri + EVENT_URL)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                .extract()
                    .header("location");
    }

}
