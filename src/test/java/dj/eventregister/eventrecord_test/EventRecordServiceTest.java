package dj.eventregister.eventrecord_test;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

import static dj.eventregister.eventrecord_test.TestMethods.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventRecordServiceTest {

    String baseUri;
    String participantLocation;
    String eventLocation;
    String eventRegisterLocation;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()).toString();
        participantLocation = createParticipant(baseUri);
        eventLocation = createEvent(baseUri);
        eventRegisterLocation = createEventRecordAndReturnLocation(baseUri, participantLocation, eventLocation);
    }




}
