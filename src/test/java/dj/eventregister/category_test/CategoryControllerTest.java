package dj.eventregister.category_test;

import dj.eventregister.models.category.dto.CategoryReadDto;
import dj.eventregister.models.category.dto.CategoryWriteDto;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

import static dj.eventregister.testMethods.CreateReadUpdateDelete.delete;
import static dj.eventregister.testMethods.CreateReadUpdateDelete.read;
import static dj.eventregister.testMethods.CreateReadUpdateDelete.create;


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
        // Create new Category and get her location
        var location = create(baseUri, new CategoryWriteDto()
                .setName(RandomString.make()));
        // Read saved category
        var actual = read(location, CategoryReadDto.class);
        // This what I am excepted in assert
        var expected = new CategoryReadDto()
                .setId(actual.getId())
                .setName(actual.getName());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldCreateAndDeleteCategory() {
        // Create new Category and get her location
        var location = create(baseUri, new CategoryWriteDto()
                .setName(RandomString.make()));

        delete(location);

        // Looking deleted category
        RestAssured
                .given()
                    .get(location)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
