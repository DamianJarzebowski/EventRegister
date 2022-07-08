package dj.eventregister;

import dj.eventregister.category.CategoryWriteDto;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testng.annotations.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {

    public static final String BASE_URL = "http://localhost:8080/api/categories";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test(description = "Create a category")
    void postTest() {


        var dto = new CategoryWriteDto("Test Category");

        RestAssured
                .given()
                    .body("{\"name\": \"test\"}")
                .when()
                    .post(BASE_URL)
                .then()
                    .statusCode(201);
    }
}
