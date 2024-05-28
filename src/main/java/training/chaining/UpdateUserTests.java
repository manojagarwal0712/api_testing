package training.chaining;
import com.github.javafaker.Faker;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.path.xml.XmlPath;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.module.jsv.JsonSchemaValidator;
public class UpdateUserTests {
    @Test
    public void testUpdateUser(ITestContext context){
        int id = (int) context.getSuite().getAttribute("user_id");
        Faker faker = new Faker();
        JSONObject data = new JSONObject();
        data.put("name", faker.name().fullName());
        data.put("gender", "Female");
        data.put("email", faker.internet().emailAddress());
        data.put("status", "active");
        String bearerToken = "72f3d2b300e82d2c52a7dfa8be971eb7ab70d8473437886445fdbdc8d418ae1e";
     given()
                .header("Authorization", "Bearer "+ bearerToken)
                .contentType("application/json")
                .pathParam("id", id)
                .body(data.toString())
                .when()
                .put("https://gorest.co.in/public/v2/users")
             .then().statusCode(200).log().all();
    }
}
