package dj.eventregister.participant_test;

import dj.eventregister.models.participant.dto.ParticipantReadDto;
import dj.eventregister.testMethods.CreateReadUpdateDelete;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

import static dj.eventregister.participant_test.TestMethods.createParticipant;

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

        var actual = CreateReadUpdateDelete.read(participantLocation, ParticipantReadDto.class, HttpStatus.SC_OK);

        var dateForUpdate = new ParticipantReadDto()
                .setId(actual.getId())
                .setName("UpdateName")
                .setLastName("UpdateLastName")
                .setAge(99)
                .setEmail("UpdateEmail@gmail.com");

        var actualUpdatedParticipant = CreateReadUpdateDelete.update(participantLocation, ParticipantReadDto.class, dateForUpdate, HttpStatus.SC_OK);

        Assertions.assertThat(actualUpdatedParticipant).isEqualTo(dateForUpdate);
    }

    @Test
    void shouldCreateAndGetParticipant() {

        var actual = CreateReadUpdateDelete.read(participantLocation, ParticipantReadDto.class, HttpStatus.SC_OK);

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
        CreateReadUpdateDelete.delete(participantLocation, HttpStatus.SC_NO_CONTENT);
        CreateReadUpdateDelete.delete(participantLocation, HttpStatus.SC_NOT_FOUND);
    }
}
