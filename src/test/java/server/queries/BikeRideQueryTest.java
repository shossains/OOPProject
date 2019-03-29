package server.queries;

import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Request;
import server.db.Query;

public class BikeRideQueryTest {

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
        Query.runQueries(queries, testUserRow, testUserPass);
    }

    /**
     * Tests is addBike is false.
     */
    @Test
    public void addBikeFalse() {
        //reset db
        String[] queries = new String[1];
        queries[0] = "DELETE FROM bikeride WHERE username = '"
                + testUserRow + "'";
        Query.runQueries(queries, testUserRow, testUserPass);

        String testString = "{'type' : 'BikeRide', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "',"
                + "'addBike': false}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals(null, request.execute());
    }

    /**
     * Tests for the request of the bike ride
     */
    @Test
    public void BikeRideDistance() {
        String testString = "{'type' : 'BikeRide', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addBike' : true, 'distance' : 2500}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{'points' : 33 , 'added' : 33 , 'co2' : 0.33444}", request.execute());
    }

    @Test
    public void testRunQuery() {

    }
}