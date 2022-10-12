package dj.eventregister.category_test;

import dj.eventregister.models.category.dto.CategoryWriteDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryServiceTest {

    public static final String BASE_URL = "/api/categories";
    String baseUri;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void beforeEach() {
        baseUri = URI.create(testRestTemplate.getRootUri()) + BASE_URL;
    }


    /*
    @Test
    void shouldThrowDuplicateCategoryNameExceptionJUnit5() {

        String categoryName = "CategoryTestName";

        DuplicateCategoryNameException e = org.junit.jupiter.api.Assertions.assertThrows(DuplicateCategoryNameException.class, ()-> categoryService.saveCategory(new CategoryWriteDto(categoryName)));
        Assertions.assertThat(e.getMessage()).isEqualTo("Kategoria o tej samej nazwie już istnieje.");
    }

     */

    @Test
    void shouldThrowDuplicateCategoryNameExceptionRestAssured() {
        final int addDuplicate = 1;

        for (int i = 0; i < 2; i++) {
            var isDuplicated = i == addDuplicate;
            var expectedCode = isDuplicated
                    ? HttpStatus.SC_CONFLICT
                    : HttpStatus.SC_CREATED;

            RestAssured
                    .with()
                    .contentType(ContentType.JSON)
                    .body(new CategoryWriteDto()
                            .setName("CategoryName"))
                    .when()
                    .post(baseUri)
                    .then()
                    .statusCode(expectedCode);
        }
    }

    /*
    @Test
    void shouldThrowDuplicateCategoryNameException() {

        CategoryService categoryService = Mockito.mock(CategoryService.class);

        String categoryName = "CategoryTestName";

        categoryService.saveCategory(new CategoryWriteDto(categoryName));

        when(categoryService.saveCategory(new CategoryWriteDto(categoryName))).thenThrow(new DuplicateCategoryNameException());

        Assertions.assertThatThrownBy(() -> categoryService.saveCategory(new CategoryWriteDto(categoryName))).isInstanceOf(DuplicateCategoryNameException.class);

    }

     */
}
