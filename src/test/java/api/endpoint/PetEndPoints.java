package api.endpoint;

import api.payload.Pet;
import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PetEndPoints {
    public static Response createPet(Pet payload){
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.pet_url);
    }

    public static Response uploadPhoto(Integer petId, String filePath, String additionalMetadata){
        File file = new File(filePath);
        return given()
                .multiPart("file", file)
                .multiPart("additionalMetadata", additionalMetadata)
                .contentType("multipart/form-data")
                .pathParam("petId", petId)
                .when()
                .post(Routes.upload_url);
    }

    public static Response putPet(Pet payload){
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .put(Routes.pet_url);
    }

    public static Response getPet(Integer petId){
        return given()
                .accept(ContentType.JSON)
                .pathParam("petId", petId)
                .when()
                .get(Routes.get_pet_url+"/"+"{petId}");
    }

    public static Response getFindByStatusPet(){
        return given()
                .accept(ContentType.JSON)
                .queryParam("status", "available")
                .queryParam("status", "pending")
                .queryParam("status", "sold")
                .when()
                .get(Routes.find_by_status_url);
    }



}
