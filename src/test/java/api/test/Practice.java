package api.test;
import api.endpoint.PetEndPoints;
import api.payload.Category;
import api.payload.Pet;
import api.payload.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.module.jsv.JsonSchemaValidator;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class Practice {

    @Test
    public void schemaValidation(){
       Response response =  given()
                .contentType("application/json").when().get("path of url");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Path of Json file."));

        Response response1 = given().contentType("application/json").when().get("");
        response1.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("path ofson"));

        Response response2 = given().contentType("applicatino/json").when().get("url");
        response2.jsonPath().get("id");
        response2.getStatusCode();

        Response response3 = given().contentType("applicaition/json").queryParam("page", 2)
                .when().get("url");
        Response response4 = given().contentType("application/json").pathParam("myPath1", "Value")
                .when().get("baser url/{mypath1}");
        response4.getStatusCode();
    }
}
