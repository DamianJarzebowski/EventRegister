package dj.eventregister.category_test;

import dj.eventregister.models.category.dto.CategoryReadDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

import static dj.eventregister.category_test.TestMethods.createCategory;
import static dj.eventregister.category_test.TestMethods.deleteCategory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {

    public static final String BASE_URL = "/api/categories";
    String baseUri;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;
    }

    @Test
    void shouldCreateAndGetCategory() {

        var location = createCategory(baseUri);

        var actual = RestAssured
                .given()
                    .headers("Content-Type", ContentType.JSON)
                    .get(location)
                    .as(CategoryReadDto.class);

        var expected = new CategoryReadDto()
                .setId(actual.getId())
                .setName(actual.getName());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldCreateAndDeleteCategory() {

        var location = createCategory(baseUri);

        deleteCategory(location);

        RestAssured
                .given()
                    .get(location)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
