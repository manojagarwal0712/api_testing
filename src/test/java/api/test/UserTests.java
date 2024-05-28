package api.test;

import api.endpoint.UserEndpoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;

public class UserTests {

    Faker faker ;
    User userPayload;
    public Logger log;
    @BeforeClass
    public void setUpData(){
        faker = new Faker();
        userPayload = new User();
        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstname(faker.name().firstName());
        userPayload.setLastname(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        log = LogManager.getLogger(UserTests.class);
    }
    @Test(priority = 1)
    public void testPostUser(){
        log.info("*************Creating user");
        Response response = UserEndpoints.createUser(userPayload);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),200);
        log.info("***************User is created");
    }
    @Test(priority = 2)
    public void testGetUserByName(){
        Response response = UserEndpoints.readUser(userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),200);
    }
    @Test(priority = 3)
    public void testUpdateUser(){
        userPayload.setFirstname(faker.name().firstName());
        userPayload.setLastname(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        Response response = UserEndpoints.updateUser(this.userPayload.getUsername(), userPayload);
        response.then().log().body();
        Assert.assertEquals(response.statusCode(),200);

        //check data is updated or not.
        Response responseAfterUpdate = UserEndpoints.readUser(userPayload.getUsername());
        responseAfterUpdate.then().log().all();
        Assert.assertEquals(response.statusCode(),200);
    }

    @Test(priority = 4)
    public void testDeleteUser(){
        Response response = UserEndpoints.deleteUser(userPayload.getUsername());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

}
