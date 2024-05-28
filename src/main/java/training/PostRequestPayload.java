package training;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.module.jsv.JsonSchemaValidator;
import payload.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class PostRequestPayload {
    String user_id;
    @Test(priority = 0)
    public void createUserPostHashMap(){
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

    @Test(priority = 1)
    public void createUserPostJsonLib(){
        JSONObject data = new JSONObject();
        data.put("name", "Manoj");
        data.put("job", "learner");
        given()
                .contentType("application/json")
                .body(data.toString())
                .when()
                    .post("https://reqres.in/api/users")
                .then().statusCode(201)
                    .body("name", equalTo("Manoj"))
                    .body("job", equalTo("learner")).log().all();
    }

    @Test(priority = 2)
    public void createUserPostWithPOJO(){
        User user = new User();
        user.setName("Manoj");
        user.setJob("learner");
        Response response = given()
                .contentType("application/json")
                .body(user)
                .when()
                .post("https://reqres.in/api/users");
        user_id = response.jsonPath().get("id");
        response.then().body("name", equalTo("Manoj"))
                .body("job", equalTo("learner")).log().all();
    }

    @Test(priority = 2)
    public void createUserPostWithExternalJSONFile() throws FileNotFoundException {
        File fe = new File("src/main/java/payload/body.json");
        FileReader fr = new FileReader(fe);
        JSONTokener jt = new JSONTokener(fr);
        JSONObject data = new JSONObject(jt);

        Response response = given()
                .contentType("application/json")
                .body(data.toString())
                .when()
                .post("https://reqres.in/api/users");
        user_id = response.jsonPath().get("id");
        response.then().body("name", equalTo("Manoj"))
                .body("job", equalTo("learner")).log().all();
    }

}
