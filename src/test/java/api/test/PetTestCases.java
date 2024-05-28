package api.test;

import api.endpoint.PetEndPoints;
import api.payload.Category;
import api.payload.Pet;
import api.payload.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.module.jsv.JsonSchemaValidator;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PetTestCases {

    Faker faker;
    Pet petPayload;
    Category categoryPayload;
    Tag tagPayload;

    Integer petId;
    Pet petObject;
    @BeforeClass
    public void setUpData(){
        faker = new Faker();
        petPayload = new Pet();
        categoryPayload = new Category();
        categoryPayload.setId(faker.idNumber().hashCode());
        categoryPayload.setName(faker.name().firstName());
        tagPayload = new Tag();
        tagPayload.setId(faker.idNumber().hashCode());
        tagPayload.setName(faker.name().firstName());

        petPayload.setId(faker.idNumber().hashCode());
        petPayload.setCategory(categoryPayload);
        petPayload.setName(faker.name().firstName());
        ArrayList<Tag> tagsList = new ArrayList<>();
        tagsList.add(tagPayload);
        petPayload.setTags(tagsList);
        petPayload.setStatus("available");
        ArrayList<String> photoUrlList = new ArrayList<>();
        photoUrlList.add("Photourl1");
        petPayload.setPhotoUrls(photoUrlList);
    }


    @Test
    public void testPathAnQueryParam(){
        //https://bookstore.demoqa.com/BookStore/v1/Books
        Response response = given()
                .pathParam("books", "Books")
                .when()
                .get("https://bookstore.demoqa.com/BookStore/v1/{books}");
        int statusCode = response.getStatusCode();
        JsonPath jsonPath = response.getBody().jsonPath();
        Assert.assertEquals(statusCode, 200);

        //List all book titles: $.books[*].title
        /*List<String> titleList = jsonPath.getList("books.title");
        for (String title :
                titleList) {
            System.out.println(title);
        }*/
        //Get the author of the second book: $.books[1].author
        List<Map<String, String>> booksList = jsonPath.getList("books");
        /*if (booksList.size()>1){
            Map<String, String> book = booksList.get(1);
            System.out.println(book.get("author"));
        }*/

        //Find the number of books published by O'Reilly Media: $.books[?(@.publisher == 'O\'Reilly Media')].size()
        /*int count =0;
        for (Map<String, String> map:
        booksList) {
            if (map.get("publisher").contains("Reilly Media")){
                count++;
                System.out.println(map.get("title"));
            }
        }*/

        //Extract the description of the book with ISBN "9781491950296": $.books[?(@.isbn == '9781491950296')].description
        for (Map<String, String> map:
                booksList) {
            if (map.get("isbn").equals("9781491950296")){
                System.out.println(map.get("description"));
            }
        }
    }

    @Test(priority = 1)
    public void testPostPet(){
        Response response = PetEndPoints.createPet(petPayload);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),200);
        petId = response.jsonPath().get("id");
    }

    @DataProvider(name = "userData")
    public Object[][] createData(){
        return new Object[][]{
                {"user1", "data1"},
                {"user2", "data2"}
        };
    }
    @Test(dataProvider = "userData")
    public void useDataProvider(String userName, String val){
        System.out.println(userName +"--"+ val);
    }
    @Test(priority = 2)
    public void testUploadPhotoUrlToPet(){
        Response response = PetEndPoints.uploadPhoto(petId, "testData/pet_photo.png","Test additional meta data");
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),200);
    }

    @Test(priority = 3)
    public void testGetPet() throws JsonProcessingException {
        Response response = PetEndPoints.getPet(1);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),200);

        /**
         * verify a single value/attribute from JSON
         */
        Assert.assertTrue(response.getBody().jsonPath().getInt("id")>0);

        /**
         * convert it to java object and then to assertion for attributes
         */
        String jsonResponse = response.getBody().asPrettyString();
        ObjectMapper mapper = new ObjectMapper();
        petObject = mapper.readValue(jsonResponse, Pet.class);
        Assert.assertTrue(petObject.getId()>0);

        /**
         * Verify the schema.
         */
        response.then().body(JsonSchemaValidator.matchesJsonSchema(new File("testData/pet_schema.json")));
        response.then().body("id", Matchers.greaterThan(0));
        response.then().body("status", Matchers.equalTo("available"));
    }

    @Test(priority = 3)
    public void testGetFindByStatusPet() {
        Response response = PetEndPoints.getFindByStatusPet();
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),200);
        String jsonData = response.getBody().asPrettyString();
        List<Map<String, Object>> petByStatusData = JsonPath.from(jsonData).getList("");
        int countAvl =0;
        int countSold =0;
        int countPend =0;
        for (Map<String, Object> pet:
                petByStatusData) {
            if ("available".equals(pet.get("status"))){
                countAvl++;
            }
            else if ("sold".equals(pet.get("status"))){
                countSold++;
            }
            else if ("pending".equals(pet.get("status"))){
                countPend++;
            }
        }
        System.out.println("countAvl: - "+ countAvl + " countSold : - " + countSold +" countPend: - "+ countPend);
    }


    @Test(priority = 4)
    public void testPutPet() {
        petObject.setStatus("Sold");
        Response response = PetEndPoints.putPet(petObject);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),200);
        petId = response.jsonPath().get("id");
    }

    @Test
    public void testGetRating(){
        //https://jsonmock.hackerrank.com/api/food_outlets?city=seattle
        Response response = given()
                .accept(ContentType.JSON)
                . when()
                        .get("https://jsonmock.hackerrank.com/api/food_outlets?city=seattle");

        String restJson = response.getBody().asPrettyString();
        Map<String, Object> data = JsonPath.from(restJson).getMap("");
        List<Map<String, Object>> restaurants = (List<Map<String, Object>>)data.get("data");

        float maxRating = Float.MIN_VALUE;
        String restName = "";
        for (Map<String, Object> restaurant : restaurants) {
            String name = (String) restaurant.get("name");
            Map<String, Object> userRating = (Map<String, Object>) restaurant.get("user_rating");
            float averageRating = (float) userRating.get("average_rating");
            if (maxRating<averageRating){
                maxRating = averageRating;
                restName = name;
            }
        }

        int total_page = response.getBody().jsonPath().getInt("total_pages");
        for (int i = 2; i<=total_page;i++){
            Response response1 = getOutLateWithPageNum(i);
            restJson = response1.getBody().asPrettyString();
            data = JsonPath.from(restJson).getMap("");
            restaurants = (List<Map<String, Object>>)data.get("data");
            for (Map<String, Object> restaurant : restaurants) {
                String name = (String) restaurant.get("name");
                Map<String, Object> userRating = (Map<String, Object>) restaurant.get("user_rating");
                float averageRating = (float) userRating.get("average_rating");
                if (maxRating<averageRating){
                    maxRating = averageRating;
                    restName = name;
                }
            }
        }
        System.out.println(restName + "--"+ maxRating);


    }
    public Response getOutLateWithPageNum(int num){
        Response response = given()
                .accept(ContentType.JSON)
                .queryParam("page", num)
                . when()
                .get("https://jsonmock.hackerrank.com/api/food_outlets?city=seattle");
        return response;
    }


}
