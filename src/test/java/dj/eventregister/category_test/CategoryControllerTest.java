package dj.eventregister.category_test;

import dj.eventregister.category.CategoryReadDto;
import dj.eventregister.category.CategoryWriteDto;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    public static final String BASE_URL = "/api/categories";

    @Test
    void shouldCreateCategory() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        RestAssured
                .with()
                .contentType((ContentType.JSON))
                .body(new CategoryReadDto()
                        .setName("TestCategoryName"))
                .when()
                    .post(uri)
                .then()
                .spec(ResponseSpecCreate);
    }

    @Test
    void shouldCreateAndGetCategory() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        var location = createCategoryAndReturnLocation(uri);

        var actual = RestAssured
                .given()
                    .headers("Content-Type", ContentType.JSON)
                    .get(location)
                    .as(CategoryReadDto.class);

        var expected = new CategoryReadDto()
                .setId(actual.getId())
                .setName("TestCategoryName");

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldCreateAndDeleteCategory() {

        var uri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;

        var location = createCategoryAndReturnLocation(uri);

        RestAssured
                .when()
                    .delete(location)
                    .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    String createCategoryAndReturnLocation(String uri) {

        return RestAssured
                .with()
                    .contentType(ContentType.JSON)
                    .body(new CategoryWriteDto()
                        .setName("TestCategoryName"))
                .when()
                    .post(uri)
                    .header("location");
    }

    ResponseSpecification ResponseSpecCreate = new ResponseSpecBuilder()
            .expectStatusCode(HttpStatus.SC_CREATED)
            .build();


}
