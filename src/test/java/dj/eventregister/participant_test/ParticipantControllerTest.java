package dj.eventregister.participant_test;

import dj.eventregister.event.dto.EventReadDto;
import dj.eventregister.participant.dto.ParticipantReadDto;
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

import static dj.eventregister.participant_test.TestMethods.createParticipant;
import static dj.eventregister.participant_test.TestMethods.deleteParticipant;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParticipantControllerTest {

    public static final String BASE_URL = "/api/participants";
    String baseUri;
    String participantLocation;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;
        participantLocation = createParticipant(baseUri);
    }

    @Test
    void shouldCreateAndUpdateParticipant() {

        var actual = RestAssured
                .given().headers("Content-Type", ContentType.JSON)
                .get(participantLocation)
                .as(ParticipantReadDto.class);

        var dateForUpdate = new ParticipantReadDto()
                .setId(actual.getId())
                .setName("UpdateName")
                .setLastName("UpdateLastName")
                .setAge(99)
                .setEmail("UpdateEmail@gmail.com");

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(dateForUpdate)
                .when()
                    .put(participantLocation)
                .then()
                    .statusCode(HttpStatus.SC_OK);

        var actualUpdatedParticipant = RestAssured
                .given()
                .headers("Content-Type", ContentType.JSON)
                .get(participantLocation)
                .as(ParticipantReadDto.class);

        Assertions.assertThat(actualUpdatedParticipant).isEqualTo(dateForUpdate);
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
}
