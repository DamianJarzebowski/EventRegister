package dj.eventregister;

import dj.eventregister.category.CategoryWriteDto;
import dj.eventregister.event.EventWriteDto;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class CategoryControllerTest {

    public static final String BASE_URL = "http://localhost:8080/api/categories";

    @Test(description = "create a category")
    void postTest() {

        var dto = new CategoryWriteDto("Event", "Testowy Event Opis Eventu", 15, 20, true, LocalDateTime.of(2023, 12, 31, 12, 0, 0, 0), "Taniec");


        RestAssured
                .given()
                    .body("{\"name\": \"test\"}")
                .when()
                    .post(BASE_URL)
                .then()
                    .statusCode(201);
    }
}
