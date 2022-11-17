package dj.eventregister.category_test;

import dj.eventregister.models.category.dto.CategoryReadDto;
import dj.eventregister.models.category.dto.CategoryWriteDto;
import dj.eventregister.testMethods.CreateReadUpdateDelete;
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
        // Create new Category and get her location
        var location = CreateReadUpdateDelete.create(baseUri, new CategoryWriteDto().setName(RandomString.make()), HttpStatus.SC_CREATED);
        // Read saved category
        var actual = CreateReadUpdateDelete.read(location, CategoryReadDto.class, HttpStatus.SC_OK);
        // This what I am excepted in assert
        var expected = new CategoryReadDto()
                .setId(actual.getId())
                .setName(actual.getName());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldCreateAndDeleteCategory() {
        // Create new Category and get her location
        var location = CreateReadUpdateDelete.create(baseUri, new CategoryWriteDto().setName(RandomString.make()), HttpStatus.SC_CREATED);

        CreateReadUpdateDelete.delete(location, HttpStatus.SC_NO_CONTENT);
        CreateReadUpdateDelete.delete(location, HttpStatus.SC_NOT_FOUND);
    }
}
