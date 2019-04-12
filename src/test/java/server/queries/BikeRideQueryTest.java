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
        Query.runQueries(queries);
    }

    /**
     * Tests is addBike is false with no records.
     */
    @Test
    public void addBikeFalseEmpty() {
        //reset db
        String[] queries = new String[1];
        queries[0] = "DELETE FROM bikeride WHERE username = '"
                + testUserRow + "'";
        Query.runQueries(queries);

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

    /**
     * Tests for the printing of the records
     */
    @Test
    public void addMealFalsePrint(){
        String[] queries = new String[3];
        queries[0] = "DELETE FROM bikeride WHERE username = '" + testUserRow + "';";
        queries[1] = "INSERT INTO bikeride VALUES ('testUser',20,'50','2019-03-29 00:00:00',2.2)";
        queries[2] = "INSERT INTO bikeride VALUES ('testUser',30,'60','2019-03-29 00:00:00',3.2)";
        Query.runQueries(queries, testUserRow, testUserPass);

        String testString = "{'type' : 'BikeRide', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "',"
                + "'addBike': false}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        request.execute();

        Assert.assertEquals("[{'points' : 20,'distance' : 50,'datetime' : '2019-03-29 00:00:00'}, {'points' : 30,'distance' : 60,'datetime' : '2019-03-29 00:00:00'}]", request.execute());
    }
}