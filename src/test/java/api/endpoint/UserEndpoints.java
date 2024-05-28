package api.endpoint;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class UserEndpoints {
    public static Response createUser(User payload){
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
        .when()
                .post(Routes.post_url);
    }

    public static Response readUser(String userName){
        return given()
                .accept(ContentType.JSON)
                .pathParam("userName", userName)
                .when()
                    .get(Routes.get_url);
    }

    public static Response updateUser(String userName, User payload){
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("userName", userName)
                .body(payload)
                .when()
                .put(Routes.update_url);
    }
    public static Response deleteUser(String userName){
        return given()
                .accept(ContentType.JSON)
                .pathParam("userName", userName)
                .when()
                .delete(Routes.delete_url);
    }
}
