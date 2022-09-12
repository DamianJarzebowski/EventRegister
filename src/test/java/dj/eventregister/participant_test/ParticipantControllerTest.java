package dj.eventregister.participant_test;

import dj.eventregister.participant.dto.ParticipantReadDto;
import dj.eventregister.participant.dto.ParticipantWriteDto;
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
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParticipantControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    public static final String BASE_URL = "/api/participants";
    String baseUri;
    String participantLocation;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;
        participantLocation = createParticipantAndReturnLocation(baseUri);
    }

    @Test
    void shouldCreateAndUpdateParticipant() {

        var actual = RestAssured
                .given().headers("Content-Type", ContentType.JSON)
                .get(participantLocation)
                .as(ParticipantReadDto.class);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new ParticipantReadDto()
                            .setId(actual.getId())
                            .setName("UpdateName")
                            .setLastName("UpdateLastName")
                            .setAge(99)
                            .setEmail("UpdateEmail@gmail.com"))
                .when()
                    .put(participantLocation)
                .then()
                    .statusCode(HttpStatus.SC_OK);

    }

    @Test
    void shouldCreateAndGetParticipant() {

        var actual = RestAssured
                .given().headers("Content-Type", ContentType.JSON)
                .get(participantLocation)
                .as(ParticipantReadDto.class);

        var expected = new ParticipantReadDto()
                .setId(actual.getId())
                .setName("TestName")
                .setLastName("TestLastName")
                .setAge(18)
                .setEmail(actual.getEmail());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldCreateAndDeleteParticipant() {

        deleteParticipant(participantLocation);

        RestAssured
                .given()
                    .get(participantLocation)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    String createParticipantAndReturnLocation(String baseUri) {

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

    void deleteParticipant(String location) {

        RestAssured
                .when()
                    .delete(location)
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
