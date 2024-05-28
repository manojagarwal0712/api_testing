package training;

import com.github.javafaker.Faker;
import io.restassured.http.Cookie;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import  static io.restassured.RestAssured.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class APITesting {
    int id;



    @Test
    public void fakerLib(){
        Faker faker = new Faker(Locale.GERMANY);
        System.out.println(faker.address().fullAddress());
    }
    @Test
    public void testOAuth1Auhtentication(){
        Faker faker = new Faker();
        System.out.println(faker.address().fullAddress());

        given()
                .auth().oauth("consumerKey", "consumerSecret","accessToken", "secretToken")
                .when()
                .get("https://api.github.com/users/manojagarwal0712/repos")
                .then().statusCode(200).log().body();

    }

    @Test
    public void testOAuth2Auhtentication(){
        given()
                .auth().oauth2("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")
                .when()
                .get("https://api.github.com/users/manojagarwal0712/repos")
                .then().statusCode(200).log().body();

    }


    @Test
    public void testBearerAuhtentication(){
        String bearerToken = "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";
        given()
                .header("Authorization", "Bearer  " + bearerToken)
                .when()
                    .get("https://api.github.com/users/manojagarwal0712/repos")
                .then().log().body();

    }

    /**
     * https://toolsqa.com/rest-assured/basic-auth/
     */
    @Test
    public void testPreEmptiveAuhtentication(){
        Response response = given()
                .auth().preemptive().basic("postman", "password")
                .when().get("https://postman-echo.com/basic-auth");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getBody().jsonPath().get("authenticated"), true);

    }

    @Test
    public void testDiagestAuhtentication(){
        given()
                .auth().digest("postman", "password")
                .when().get("https://postman-echo.com/basic-auth").then().statusCode(200).log().body();
    }


    @Test
    public void testBasicAuhtentication(){
        given()
                .auth().basic("postman", "password")
                .when().get("https://postman-echo.com/basic-auth").then().statusCode(200).log().body();
    }

    @Test
    public void testResponseBody(){
        /*given().when().get("http://localhost:3000/Books").then().statusCode(200)
                .body("[0].Title", equalTo("A First Course in Database Systems"))
                .body("[0].ISBN", equalTo("ISBN-0-13-713526-2")).log().body();*/
        Response response = given().when().get("http://localhost:3000/Books");
        /*Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("application/json; charset=utf-8", response.getHeader("Content-Type"));
        Assert.assertEquals("A First Course in Database Systems", response.jsonPath().get("[0].Title").toString());*/
    }

    @Test
    public void testLogs(){
        //print only body of resposne as log
        given().when()
                .get("https://reqres.in/api/users?page=2").then()
                //.log().body();
                //.log().cookies();
                //.log().headers();
                .log().all();
    }
    /**
     * Test Headers
     */
    @Test
    public void testHeadersInfo(){
        /*given().when().get("https://www.google.com/").then()
                .header("Content-Type", "text/html; charset=ISO-8859-1")
                .header("Server","gws")
                .header("Content-Encoding","gzip");*/

        Response response = given().when().get("https://www.google.com/");
        System.out.println(response.getHeader("Content-Type"));

        Headers headers = response.getHeaders();
        for (Header header:
             headers) {
            System.out.println(header.getName() + "->" + header.getValue());
        }
    }
    /**
     * List Cookies information.
     */
    @Test
    public void testGetCookieInfo(){
        Response response = given().when().get("https://www.google.com/");
        String cookieAEC = response.getCookie("AEC");
        System.out.println("Value of AEC cookie : "+ cookieAEC);
        Map<String, String> cookies = response.getCookies();
        for (Map.Entry<String, String> entry:
             cookies.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }
    }
    @Test
    public void testCokkiesAndHeaders(){
        given().
                when().
                get("https://www.google.com/").
                then().cookie("AEC", "AUEFqZfYM1_D0cY-5mwVIGSCLQDpRGS7ML3BObmf4zHf8Wu0374cX-8-Eg").log().all();
    }

    /***
     * Query param in Rest Assured.
     *https://reqres.in/api/users?page=2
     * <domain>/<path>/<path>/<query param>
     */
    @Test
    public void testWithQueryAndPathParam(){
        given()
                .pathParam("mypath", "users") // path pram
                .queryParam("page", 2) // query param
                .when()
                    .get("https://reqres.in/api/{mypath}")
                .then()
                    .statusCode(200)
                    .log().all();
    }
    @Test
    public void getUsers(){
        /*get("https://reqres.in/api/users?page=2").then()
                    .statusCode(200).assertThat().body("page",equalTo(2)).log().all();*/
        Response response = get("https://reqres.in/api/users?page=2");
        response.then().statusCode(200);
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void createUser(){
        HashMap<String, String> hm = new HashMap<>();
        hm.put("name", "pavan");
        hm.put("job", "master");

        given().contentType("application/json").body(hm)
                .when().post("https://reqres.in/api/users").then().statusCode(201).log().all();
    }


    @Test
    public void updateUser(){
        /**
         * Creating a new user
         */
        HashMap<String, String> hm = new HashMap<>();
        hm.put("name", "pavan");
        hm.put("job", "master");

        id = given().contentType("application/json").body(hm)
                .when().post("https://reqres.in/api/users").jsonPath().getInt("id");

        /**
         * updating the same usr
         */
        HashMap<String, String> hm1 = new HashMap<>();
        hm.put("name", "pavan");
        hm.put("job", "SDM");

        ValidatableResponse response = given().contentType("application/json").body(hm1)
                .when().put("https://reqres.in/api/users/"+id).then().statusCode(200).assertThat().body("updatedAt", startsWith("2023")).log().all();
    }

    @Test
    public void deleteUser(){
        /**
         * Create a new user
         */
        HashMap<String, String> hm = new HashMap<>();
        hm.put("name", "pavan");
        hm.put("job", "master");
        id = given().contentType("application/json").body(hm)
                .when().post("https://reqres.in/api/users").jsonPath().getInt("id");

        /**
         * Deleting the created user.
         */
        delete("https://reqres.in/api/users/"+id).then().statusCode(204).log().all();
    }

}
