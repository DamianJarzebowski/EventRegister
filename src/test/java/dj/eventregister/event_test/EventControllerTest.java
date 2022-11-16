package dj.eventregister.event_test;

import dj.eventregister.models.event.Event;
import dj.eventregister.models.event.dto.EventReadDto;
import dj.eventregister.testMethods.CreateReadUpdateDelete;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.Month;

import static dj.eventregister.event_test.TestMethods.createEvent;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerTest {

    public static final String BASE_URL = "/api/event";
    String baseUri;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldCreateAndGetEvent() {
        var location = createEvent(baseUri);
        var actual = CreateReadUpdateDelete.read(location, EventReadDto.class, HttpStatus.SC_OK);

        var expected = new EventReadDto()
                .setId(actual.getId())
                .setName("TestEventName")
                .setDescription("TestDescription")
                .setCategory("Taniec")
                .setMajority(true)
                .setMaxParticipant(3)
                .setMinParticipant(1)
                .setDateTime(LocalDateTime.of(2222, 12, 31, 23, 59, 59))
                .setStateEvent(Event.EventStateMachine.NOT_ENOUGH_PARTICIPANT);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldCreateAndUpdateEvent() {
        var location = createEvent(baseUri);
        var actual = CreateReadUpdateDelete.read(location, EventReadDto.class, HttpStatus.SC_OK);

        var dateForUpdate = new EventReadDto()
                .setId(actual.getId())
                .setName("UpdateName")
                .setDescription("UpdateDescription")
                .setCategory("Rower")
                .setMajority(false)
                .setMaxParticipant(4)
                .setMinParticipant(2)
                .setDateTime(LocalDateTime.of(2000, Month.JANUARY, 10, 12, 30));

        var actualUpdatedEvent = CreateReadUpdateDelete.update(location, EventReadDto.class, dateForUpdate, HttpStatus.SC_OK);

        Assertions.assertThat(actualUpdatedEvent).isEqualTo(dateForUpdate
                .setStateEvent(Event.EventStateMachine.NOT_ENOUGH_PARTICIPANT));
    }

    @Test
    void shouldCreateAndDeleteEvent() {
        var location = createEvent(baseUri);
        CreateReadUpdateDelete.delete(location, HttpStatus.SC_NO_CONTENT);
        CreateReadUpdateDelete.delete(location, HttpStatus.SC_NOT_FOUND);
    }
}
