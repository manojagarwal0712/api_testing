package training;
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

import java.util.List;

public class parsingXMLDataResponse {
    @Test
    public void testXMLResponse_approach1(){
        given()
                .when()
                    .get("http://restapi.adequateshop.com/api/Traveler?page=1")
                .then()
                    .statusCode(200)
                    .header("Content-Type", "application/xml; charset=utf-8")
                    .body("TravelerinformationResponse.page", equalTo("1"))
                    .body("TravelerinformationResponse.travelers.Travelerinformation[0].name", equalTo("Developer"));
    }

    @Test
    public void testXMLResponse_approach2(){
        Response response = given()
                .when()
                .get("http://restapi.adequateshop.com/api/Traveler?page=1");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getHeader("Content-Type"), "application/xml; charset=utf-8");
        Assert.assertEquals(response.xmlPath().get("TravelerinformationResponse.page").toString(), "1");
        Assert.assertEquals(response.xmlPath().get("TravelerinformationResponse.travelers.Travelerinformation[0].name").toString(), "Developer");
    }

    @Test
    public void testXMLResponse_approach3(){
        Response response = given()
                .when()
                .get("http://restapi.adequateshop.com/api/Traveler?page=1");

        XmlPath xmlObj = new XmlPath(response.asString());
        List<String> travellers = xmlObj.getList("TravelerinformationResponse.travelers.Travelerinformation");
        Assert.assertEquals(travellers.size(), 10);
        //verify traveller name present in the travellers.
        List<String> travellersName = xmlObj.getList("TravelerinformationResponse.travelers.Travelerinformation.name");
        boolean isPresent =false;
        for (String str:
                travellersName) {
            if ("Developer".equals(str)){
                isPresent = true;
                break;
            }
        }
        Assert.assertEquals(isPresent, true);
    }


}
