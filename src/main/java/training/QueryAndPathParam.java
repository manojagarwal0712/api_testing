package training;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class QueryAndPathParam {

    //https://reqres.in/api/users?page=2&d=2
    @Test
    public void testPathAnQueryParam(){
        given()
                .pathParam("mypath", "users")
                .queryParam("page", 2)
                .queryParam("id", 5)
                .when()
                    .get("https://reqres.in/api/{mypath}")
                .then()
                    .statusCode(200)
                    .log().all();
    }


}
