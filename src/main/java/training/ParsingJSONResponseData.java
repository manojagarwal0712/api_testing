package training;
import io.restassured.response.ResponseBody;
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
public class ParsingJSONResponseData {

    @Test(description = "When the reponse data is small json then we can do verify directly when we get the response")
    public void testJSONResponse(){
        given()
                .contentType("ContentType.json")
                .when()
                    .get("https://reqres.in/api/users?page=2&d=2")
                .then()
                    .statusCode(200)
                    .body("data[3].first_name", equalTo("Byron"));
    }

    @Test(description = "When huge JSON response")
    public void testJSONResponse_approach2(){
        Response response = given()
                .contentType("ContentType.json")
                .when()
                .get("https://reqres.in/api/users?page=2&d=2");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().get("data[3].first_name").toString(), "Byron");
        JSONObject jo = new JSONObject(response.toString());
        for (int i =0; i<jo.getJSONArray("data").length(); i++){
            String first_name = jo.getJSONArray("data").getJSONObject(i).get("first_name").toString();
            System.out.print(first_name);
        }
    }
}
