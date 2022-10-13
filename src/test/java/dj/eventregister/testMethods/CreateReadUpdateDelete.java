package dj.eventregister.testMethods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

public class CreateReadUpdateDelete {

    public static String create(String uri, Object objectToCreate) {
        return RestAssured
                .with()
                .contentType(ContentType.JSON)
                .body(objectToCreate)
                .when()
                .post(uri)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .header("location");
    }

    public static <T> T read(String location, Class<T> clazz) {
        return RestAssured
                .given()
                .headers("Content-Type", ContentType.JSON)
                .get(location)
                .as(clazz);
    }

    public static <T> T update(String location, Class<T> clazz, Object objectToUpdate) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(objectToUpdate)
                .when()
                .put(location)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(clazz);
    }

    public static void delete(String location) {
        RestAssured
                .when()
                .delete(location)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
