package dj.eventregister.category_test;

import dj.eventregister.category.CategoryWriteDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;


import java.net.URI;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {

    public static final String BASE_URL = "/api/categories";



    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void postTest() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body("{\"name\": \"CategoryTestName\"}")
                .when()
                    .post(uri)
                .then()
                    .statusCode(201);
        RestAssured.get(uri + "/7")
                .then()
                .body(Matchers.containsString("CategoryTestName"));
    }

    @Test
    void getTest() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured.get(uri + "/7")
                .then()
                .body(Matchers.containsString("CategoryTestName"));
    }

    @Test
    void deleteTest() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured
                .given()
                .when()
                    .delete(uri + "/1")
                    .then()
                .statusCode(204);
    }

}
