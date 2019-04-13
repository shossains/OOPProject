package server.queries;

import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Request;
import server.db.Query;

public class LoginQueryTest {
    private static String existingTestUser = "testUser";

    @Test
    public void loginTest() {
        LoginQuery loginQuery = new GsonBuilder().create().fromJson("{'type' : 'Login',"
        + " 'username' : '" + existingTestUser + "', 'password' : 'hunter2'}", LoginQuery.class);

        Assert.assertEquals("{'login' : true}", loginQuery.runQuery());
    }

    @Test
    public void badLoginTest() {
        LoginQuery loginQuery = new GsonBuilder().create().fromJson("{'type' : 'Login',"
                + " 'username' : '" + existingTestUser + "', 'password' : 'hunter3'}", LoginQuery.class);

        Assert.assertEquals("{'login' : false}", loginQuery.runQuery());
    }
}