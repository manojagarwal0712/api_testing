package api.test;

import api.endpoint.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenTests {
    @Test(dataProvider = "Data", dataProviderClass = DataProvider.class)
    public void testPostuser(String userID, String userName, String fName, String lName, String userEmail, String pwd, String ph){
        User payload = new User();
        payload.setId(Integer.parseInt(userID));
        payload.setUsername(userName);
        payload.setFirstname(fName);
        payload.setLastname(lName);
        payload.setEmail(userEmail);
        payload.setPassword(pwd);
        payload.setPhone(ph);
        Response response = UserEndpoints.createUser(payload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

}
