package dj.eventregister.event_test;

import dj.eventregister.event.EventWriteDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.net.URI;
import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerTest {

    public static final String BASE_URL = "/api/event";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldCreateEvent() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var dto = new EventWriteDto("Event", "Testowy Event Opis Eventu", 15, 20, true, LocalDateTime.of(2023, 12, 31, 12, 0, 0, 0), "Taniec");

        HttpEntity<EventWriteDto> requestEntity = new HttpEntity<>(dto, headers);

        var response = testRestTemplate.postForLocation("/api/event", requestEntity);

        var uri = URI.create(testRestTemplate.getRootUri());
        var port = uri.getPort();

        Assertions.assertThat(response).isEqualTo(URI.create("http://localhost:"+ port +"/api/event/4"));
    }

    @Test
     void testPost() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("{ \"category\": \"Taniec\", \"dateTime\": \"2022-07-16T00:56:30.604Z\", \"description\": \"EventDescription\", \"majority\": true, \"maxParticipant\": 10, \"minParticipant\": 5, \"name\": \"EventName\"}")
                .when()
                    .post(uri)
                .then()
                .statusCode(201);
    }

    @Test
    void testPut() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;


    }

    @Test
    void testDelete() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;


    }


}
