package training;

//import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaValidator;
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
public class SchemaValidation {

    @Test
    public void testJsonSchemaValidation(){
        Response response = given()
                .when()
                .get("domain/path");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Path to Json file"));
    }

    @Test
    public void testXmlSchemaValidation(){
        Response response = given()
                .when()
                .get("domain/path");
        response.then().assertThat().body(RestAssuredMatchers.matchesXsdInClasspath("path of XSD file"));

    }
}
