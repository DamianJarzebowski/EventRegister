package dj.eventregister.category_test;

import dj.eventregister.category.dto.CategoryWriteDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.assertj.core.internal.bytebuddy.utility.RandomString;

public class TestMethods {

    static String createCategory(String uri) {

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

    static void deleteCategory(String location) {

        RestAssured
                .when()
                .delete(location)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
