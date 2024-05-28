package training;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.util.HashMap;

public class HttpRequest {
    String user_id;
    @Test(priority = 0)
    public void getUser(){
        given().
                when()
                    .get("https://reqres.in/api/users?page=2")
                .then()
                    .statusCode(200)
                .body("page", equalTo(2))
                .log().all();
    }

    @Test(priority = 1)
    public void createUser(){
        HashMap<String, String> data = new HashMap<>();
        data.put("name", "Manoj");
        data.put("job", "learner");
        Response response = given()
                .contentType("application/json")
                .body(data)
                .when()
                    .post("https://reqres.in/api/users");
        user_id = response.jsonPath().get("id");
    }
    @Test(priority = 2)
    public void updateUser(){
        HashMap<String, String> data = new HashMap<>();
        data.put("name", "Manoj_updated");
        data.put("job", "learner_updated");

        given()
                .contentType("application/json")
                .body(data)
                .when()
                .put("https://reqres.in/api/users/"+user_id)
                .then().statusCode(200)
                .log().all();
    }
}
