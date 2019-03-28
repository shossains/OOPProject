package server.queries;

import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Request;
import server.db.Query;

public class SolarPanelQueryTest {
    static final String testUserRow = "testUser";
    static final String testUserPass = "hunter2";

    /**
     * initializes variables, clean up test entry in users
     */
    @BeforeClass
    public static void init() {
        //set the score to 0 on the test row
        String[] queries = new String[1];
        queries[0] = "UPDATE points SET points = 0 WHERE username = '"
                + testUserRow + "'";
        Query.runQueries(queries);
    }

    /**
     * Tests for the request of the
     */
    @Test
    public void SolarPanelRequest(){
        String testString = "{'type' : 'Solar', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addSolar' : true, 'kwh' : 0}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{'points' : 0, 'added' : 0, 'co2' : 0.0}", request.execute());
    }

    /**
     * Tests is addSolar is false.
     */
    @Test
    public void addSolarFalse(){
        String testString = "{'type' : 'Solar', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addSolar' : false, 'kwh' : 0}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals(null, request.execute());
    }
}