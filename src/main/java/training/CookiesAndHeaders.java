package training;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
public class CookiesAndHeaders {
    @Test
    public void cookiesAndHeaders(){
        Response response = given()
                .when()
                    .get("https://www.google.com/");
        String cookies_Value = response.getCookie("AEC");
        System.out.print(cookies_Value);
        Map<String, String> cookies_Values = response.getCookies();
    }
    @Test
    public void testHeaders(){
        Response response = given()
                .when()
                .get("https://www.google.com/");
        String header = response.getHeader("Content-Type");
        System.out.print(header);
        String encode = response.getHeader("Content-Encoding");
        System.out.print(encode);
    }

}
