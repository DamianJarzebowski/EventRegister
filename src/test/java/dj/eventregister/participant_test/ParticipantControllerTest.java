package dj.eventregister.participant_test;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParticipantControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    public static final String BASE_URL = "/api/participants";
    String baseUri;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;
    }

    @Test
    void shouldCreateParticipant() {

        var location = createParticipantAndReturnLocation(baseUri);

        deleteParticipant(location);
    }

    @Test
    void shouldCreateAndUpdateParticipant() {

        var location = createParticipantAndReturnLocation(baseUri);

        var actual = RestAssured
                .given().headers("Content-Type", ContentType.JSON)
                .get(location)
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
                    .put(location)
                .then()
                    .statusCode(HttpStatus.SC_OK);

        deleteParticipant(location);
    }

    @Test
    void shouldCreateAndGetParticipant() {

        var location = createParticipantAndReturnLocation(baseUri);

        var actual = RestAssured
                .given().headers("Content-Type", ContentType.JSON)
                .get(location)
                .as(ParticipantReadDto.class);

        var expected = new ParticipantReadDto()
                .setId(actual.getId())
                .setName("TestName")
                .setLastName("TestLastName")
                .setAge(18)
                .setEmail("testEmail@gmail.com");

        Assertions.assertThat(actual).isEqualTo(expected);

        deleteParticipant(location);
    }

    @Test
    void shouldCreateAndDeleteParticipant() {

        var location = createParticipantAndReturnLocation(baseUri);

        deleteParticipant(location);
    }

    String createParticipantAndReturnLocation(String baseUri) {

        return RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new ParticipantWriteDto()
                            .setName("TestName")
                            .setLastName("TestLastName")
                            .setAge(18)
                            .setEmail("testEmail@gmail.com"))
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
