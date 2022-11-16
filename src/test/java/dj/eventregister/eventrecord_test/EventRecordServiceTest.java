package dj.eventregister.eventrecord_test;

import dj.eventregister.models.event.dto.EventReadDto;
import dj.eventregister.models.eventrecord.dto.EventRecordWriteDto;
import dj.eventregister.models.participant.dto.ParticipantReadDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static dj.eventregister.eventrecord_test.TestMethods.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventRecordServiceTest {

    String baseUri;
    String eventLocation;
    List<String> participantsLocations = new ArrayList<>();

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()).toString();
        eventLocation = createEvent(baseUri);
        for (int i = 0; i < 3; i++) {
            participantsLocations.add(createParticipant(baseUri));
        }
    }

    @Test
    void shouldProtectionOfOverflowMaxParticipant() {

        final var numberOfParticipants = 3;

        for (int i = 0; i < numberOfParticipants ; i++) {

            var actualParticipant = RestAssured
                    .given()
                    .headers("Content-Type", ContentType.JSON)
                    .get(participantsLocations.get(i))
                    .as(ParticipantReadDto.class);

            var actualEvent = RestAssured
                    .given()
                    .headers("Content-Type", ContentType.JSON)
                    .get(eventLocation)
                    .as(EventReadDto.class);

            var isOverflow = i == numberOfParticipants - 1;
            var expectedCode = isOverflow
                    ? HttpStatus.SC_BAD_REQUEST
                    : HttpStatus.SC_CREATED;

            RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .body(new EventRecordWriteDto()
                            .setEventId(actualEvent.getId())
                            .setParticipantId(actualParticipant.getId()))
                    .when()
                    .post(baseUri + EVENT_RECORD_URL)
                    .then()
                    .statusCode(expectedCode);
        }
    }
}
