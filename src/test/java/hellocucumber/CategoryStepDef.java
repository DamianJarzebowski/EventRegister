package hellocucumber;

import dj.eventregister.models.category.dto.CategoryReadDto;
import dj.eventregister.models.category.dto.CategoryWriteDto;
import dj.eventregister.testMethods.CreateReadUpdateDelete;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.bytebuddy.utility.RandomString;

public class CategoryStepDef {

    public static final String BASE_URL = "/api/categories";
    public static final String BASE_URI = "http://localhost:8080";
    private String location;
    private CategoryReadDto actual;

    @Given("Created a category")
    public void created_a_category() {
        location = CreateReadUpdateDelete.create(BASE_URI + BASE_URL, new CategoryWriteDto()
                .setName(RandomString.make()));
    }

    @When("Reade created category")
    public void reade_created_category() {
        actual = CreateReadUpdateDelete.read(location, CategoryReadDto.class);
    }

    @Then("Got category should be as created")
    public void got_category_should_be_as_created() {
        CategoryReadDto expected = new CategoryReadDto()
                .setId(actual.getId())
                .setName(actual.getName());
        Assertions.assertThat(expected).isEqualTo(actual);
    }


}
