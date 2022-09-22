package dj.eventregister.participant_test;

import dj.eventregister.participant.dto.ParticipantWriteDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import java.util.Random;

class TestMethods {

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
                .post(baseUri)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .header("location");
    }

    static void deleteParticipant(String location) {

        RestAssured
                .when()
                .delete(location)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
