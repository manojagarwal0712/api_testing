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

import java.io.File;

public class FileUploadAndDownload {

    @Test
    public void testSingleFileUpload(){

        File file = new File("");
        given()
                .multiPart("file", file)
                .contentType("multipart/form-data")
                .when()
                    .post("domain/fileupload")
                .then()
                    .statusCode(200)
                .body("fileName", equalTo("file1.txt")).log().all();
    }

    public void testMultiFileUpload(){
        File file1 = new File("test1.txt");
        File file2 = new File("test2.txt");
        given()
                .multiPart("files", file1)
                .multiPart("files", file2)
                .contentType("multipart/form-data")
                .when()
                .post("domain/fileupload")
                .then()
                .statusCode(200)
                .body("[0].fileName", equalTo("test1.txt"))
                .body("[1].fileName", equalTo("test2.txt")).log().all();
    }

    public void testMultiFileUpload_approac2(){
        File file1 = new File("test1.txt");
        File file2 = new File("test2.txt");
        File fileArr[] = {file1, file2};
        given()
                .multiPart("files", fileArr)
                .contentType("multipart/form-data")
                .when()
                .post("domain/fileupload")
                .then()
                .statusCode(200)
                .body("[0].fileName", equalTo("test1.txt"))
                .body("[1].fileName", equalTo("test2.txt")).log().all();
    }
    public void testFileDownload() {
        given()
                .when()
                    .get("domain/fileudownload/test1.txt")
                .then()
                    .statusCode(200).log().body();
    }
}

