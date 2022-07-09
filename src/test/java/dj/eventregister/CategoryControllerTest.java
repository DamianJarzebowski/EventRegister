package dj.eventregister;

import dj.eventregister.category.CategoryWriteDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;


import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {

    public static final String BASE_URL = "/api/categories";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void postTest() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;


        // var dto = new CategoryWriteDto("Test Category");

        RestAssured
                .given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                    //.body("{\"name\": \"test\"}")
                .body("{ }")

                .when()
                    .post(uri)
                .then()
                    .statusCode(201);
    }
}
