package training;
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
public class Authentication {
    @Test
    public void testBasicAuth(){
        given()
                .auth().basic("postman", "password")
                .when()
                    .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200);
    }
    @Test
    public void testDigestAuth(){
        given()
                .auth().digest("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200);
    }

    @Test
    public void testPreemptiveAuth(){
        given()
                .auth().preemptive().basic("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200);
    }
}
