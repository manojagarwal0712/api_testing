package training;

import com.github.javafaker.Faker;
import com.google.gson.JsonObject;
import org.testng.annotations.Test;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.path.xml.XmlPath;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.module.jsv.JsonSchemaValidator;
public class APIChaining {

    /*@Test(priority = 0)
    public void createUser(){
        Faker faker = new Faker();
        JsonObject data = new JsonObject();
        data.addProperty("name", faker.funnyName().name());
        data.addProperty("gender", "Male");
        data.addProperty("email", faker.internet().emailAddress());
        data.addProperty("status", "active");
    }*/

    @Test
    public void testAPIChaining(){
        given()
                .when()
                .get("https://gorest.co.in/public/v2/users")
                .then().statusCode(200);
    }

}
