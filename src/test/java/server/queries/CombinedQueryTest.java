package server.queries;

import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Request;
import server.db.Query;

public class CombinedQueryTest {

    static final String testUserRow = "testUser";
    static final String testUserPass = "hunter2";

    /**
     * initializes variables, clean up test entry in users
     */
    @BeforeClass
    public static void init() {
        //set the score to 0 on the test row
        String[] queries = new String[6];
        queries[0] = "UPDATE vegetarian SET points = 0 WHERE username = '" + testUserRow + "'";
        queries[1] = "UPDATE localproduce SET points = 0 WHERE username = '" + testUserRow + "'";
        queries[2] = "UPDATE bikeride SET points = 0 WHERE username = '" + testUserRow + "'";
        queries[3] = "UPDATE publictransport SET points = 0 WHERE username = '" + testUserRow + "'";
        queries[4] = "UPDATE temperature SET points = 0 WHERE username = '" + testUserRow + "'";
        queries[5] = "UPDATE solar SET points = 0 WHERE username = '" + testUserRow + "'";
        Query.runQueries(queries);
    }

    /**
     * Tests for the combined request
     */
    @Test
    public void CombinedRequest(){
        String testString = "{'type' : 'Combined', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass  + "'}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{'vegPoints' : 0, 'locProdPoints' : 0, 'bikePoints' : 0, 'pubTransPoints' : 0, 'tempPoints' : 0, 'solarPoints' : 0}", request.execute());
    }
}