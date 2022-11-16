package dj.eventregister.testMethods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreateReadUpdateDelete {

    public static String create(String uri, Object objectToCreate, int statusCode) {
        return RestAssured
                .with()
                .contentType(ContentType.JSON)
                .body(objectToCreate)
                .when()
                .post(uri)
                .then()
                .statusCode(statusCode)
                .extract()
                .header("location");
    }

    public static <T> T read(String location, Class<T> clazz, int statusCode) {
        return RestAssured
                .given()
                .headers("Content-Type", ContentType.JSON)
                .get(location)
                .then()
                .statusCode(statusCode)
                .extract()
                .as(clazz);
    }

    public static <T> T update(String location, Class<T> clazz, Object objectToUpdate, int statusCode) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(objectToUpdate)
                .when()
                .put(location)
                .then()
                .statusCode(statusCode)
                .extract()
                .as(clazz);
    }

    public static void delete(String location, int statusCode) {
        RestAssured
                .when()
                .delete(location)
                .then()
                .statusCode(statusCode);
    }

}
