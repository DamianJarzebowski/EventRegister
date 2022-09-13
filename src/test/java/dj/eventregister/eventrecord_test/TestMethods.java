package dj.eventregister.eventrecord_test;

import dj.eventregister.event.dto.EventReadDto;
import dj.eventregister.event.dto.EventWriteDto;
import dj.eventregister.eventrecord.dto.EventRecordWriteDto;
import dj.eventregister.participant.dto.ParticipantReadDto;
import dj.eventregister.participant.dto.ParticipantWriteDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Random;

public class TestMethods {

    static final String EVENT_RECORD_URL = "/api/event-record";
    static final String PARTICIPANT_URL = "/api/participants";
    static final String EVENT_URL = "/api/event";

    static void deleteEventRecord(String location) {

        RestAssured
                .when()
                .delete(location)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    static String createParticipant(String baseUri) {

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

    static String createEventRecordAndReturnLocation(String baseUri, String participantLocation, String eventLocation) {

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

    static String createEvent(String baseUri) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new EventWriteDto()
                        .setName("TestEventName")
                        .setDescription("TestDescription")
                        .setCategory("Taniec")
                        .setMajority(true)
                        .setMaxParticipant(2)
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