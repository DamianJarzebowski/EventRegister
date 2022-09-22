package dj.eventregister.event_test;

import dj.eventregister.event.dto.EventWriteDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import java.time.LocalDateTime;

class TestMethods {

    static String createEvent(String baseUri) {

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

    static void deleteEvent(String location) {

        RestAssured
                .when()
                .delete(location)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
