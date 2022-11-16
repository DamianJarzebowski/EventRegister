package dj.eventregister.participant_test;

import dj.eventregister.models.participant.dto.ParticipantReadDto;
import dj.eventregister.models.participant.dto.ParticipantWriteDto;
import dj.eventregister.repository.ParticipantRepository;
import dj.eventregister.testMethods.CreateReadUpdateDelete;
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

    public static final String BASE_URL = "/api/participants";
    String baseUri;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;
    }

    private static final Random random = new Random();

    public static ParticipantWriteDto returnDataForCreateParticipant() {
        int rangePrefixNumber = 999;
        return new ParticipantWriteDto().setName("TestName")
                .setLastName("TestLastName")
                .setAge(18)
                .setEmail(random.nextInt(rangePrefixNumber) + "testEmail@gmail.com");
    }
    @Test
    void shouldCreateAndUpdateParticipant() {
        var participantLocation = CreateReadUpdateDelete.create(baseUri, returnDataForCreateParticipant(), HttpStatus.SC_CREATED);

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
        var participantLocation = CreateReadUpdateDelete.create(baseUri, returnDataForCreateParticipant(), HttpStatus.SC_CREATED);

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
        var participantLocation = CreateReadUpdateDelete.create(baseUri, returnDataForCreateParticipant(), HttpStatus.SC_CREATED);
        CreateReadUpdateDelete.delete(participantLocation, HttpStatus.SC_NO_CONTENT);
        CreateReadUpdateDelete.delete(participantLocation, HttpStatus.SC_NOT_FOUND);
    }
}
