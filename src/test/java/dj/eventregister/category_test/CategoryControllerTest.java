package dj.eventregister.category_test;

import dj.eventregister.category.dto.CategoryReadDto;
import dj.eventregister.category.dto.CategoryWriteDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

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

        var location = createCategoryCheckStatusAndReturnLocation(baseUri);

        var actual = RestAssured
                .given()
                    .headers("Content-Type", ContentType.JSON)
                    .get(location)
                    .as(CategoryReadDto.class);

        var expected = new CategoryReadDto()
                .setId(actual.getId())
                .setName(actual.getName());

        Assertions.assertThat(actual).isEqualTo(expected);

        deleteCategory(location);
    }

    @Test
    void shouldCreateAndDeleteCategory() {

        var location = createCategoryCheckStatusAndReturnLocation(baseUri);

        deleteCategory(location);

        RestAssured
                .given()
                    .get(location)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    String createCategoryCheckStatusAndReturnLocation(String uri) {

        return RestAssured
                .with()
                    .contentType(ContentType.JSON)
                    .body(new CategoryWriteDto()
                        .setName(RandomString.make()))
                .when()
                    .post(uri)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                .extract()
                    .header("location");
    }

    void deleteCategory(String location) {

        RestAssured
                .when()
                    .delete(location)
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
