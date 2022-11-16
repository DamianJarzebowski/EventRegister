package hellocucumber.category_test;

import dj.eventregister.models.category.dto.CategoryReadDto;
import dj.eventregister.models.category.dto.CategoryWriteDto;
import dj.eventregister.testMethods.CreateReadUpdateDelete;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.bytebuddy.utility.RandomString;

public class CategoryStepDef {

    private static final String baseUri = "http://localhost:8080/api/categories";

    private String location;
    private CategoryReadDto actual;

    @Given("Created a category")
    public void created_a_category() {
        location = CreateReadUpdateDelete.create(baseUri, new CategoryWriteDto().setName(RandomString.make()), HttpStatus.SC_CREATED);
    }

    @When("Reade created category")
    public void reade_created_category() {
        actual = CreateReadUpdateDelete.read(location, CategoryReadDto.class, HttpStatus.SC_OK);
    }

    @Then("Got category should be as created")
    public void got_category_should_be_as_created() {
        CategoryReadDto expected = new CategoryReadDto()
                .setId(actual.getId())
                .setName(actual.getName());
        Assertions.assertThat(expected).isEqualTo(actual);
    }

    @When("Delete created category")
    public void deleteCreatedCategory() {
        CreateReadUpdateDelete.delete(location, HttpStatus.SC_NO_CONTENT);
    }

    @Then("Try get return 404_Not_found")
    public void tryGetReturnNotFound() {
        RestAssured
                .given()
                .headers("Content-Type", ContentType.JSON)
                .get(location)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
