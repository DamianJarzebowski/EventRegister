package dj.eventregister.eventrecord_test;

import dj.eventregister.models.eventrecord.dto.EventRecordReadDto;
import dj.eventregister.testMethods.CreateReadUpdateDelete;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

import static dj.eventregister.eventrecord_test.TestMethods.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventRecordControllerTest {

    String baseUri;
    String participantLocation;
    String eventLocation;
    String eventRegisterLocation;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()).toString();
    }

    @Test
    void shouldCreateAndGetEventRecord() {
        participantLocation = createParticipant(baseUri);
        eventLocation = createEvent(baseUri);
        eventRegisterLocation = createEventRecord(baseUri, participantLocation, eventLocation);

        var actual = CreateReadUpdateDelete.read(eventRegisterLocation, EventRecordReadDto.class, HttpStatus.SC_OK);

        var expected = new EventRecordReadDto()
                .setId(actual.getId())
                .setEventId(actual.getEventId())
                .setParticipantId(actual.getParticipantId());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldCreateAndDeleteEventRecord() {
        participantLocation = createParticipant(baseUri);
        eventLocation = createEvent(baseUri);
        eventRegisterLocation = createEventRecord(baseUri, participantLocation, eventLocation);
        CreateReadUpdateDelete.delete(eventRegisterLocation, HttpStatus.SC_NO_CONTENT);
        CreateReadUpdateDelete.delete(eventRegisterLocation, HttpStatus.SC_NOT_FOUND);
    }
}
