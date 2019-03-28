package server.queries;

import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Request;
import server.db.Query;

public class PublicTransportQueryTest {
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
     * Tests for the request of bus.
     */
    @Test
    public void BusDistance(){
        String testString = "{'type' : 'PublicTransport', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addPublic' : true, 'distance' : 0, 'vehicle' : 'bus'}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{'points' : 0 , 'added' : 0 , 'co2' : 0.0}", request.execute());
    }

    /**
     * Tests for the request of train.
     */
    @Test
    public void TrainDistance(){
        String testString = "{'type' : 'PublicTransport', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addPublic' : true, 'distance' : 0, 'vehicle' : 'bus'}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{'points' : 0 , 'added' : 0 , 'co2' : 0.0}", request.execute());
    }

    /**
     * Tests is addPublic is false.
     */
    @Test
    public void addLocalFalse(){
        String testString = "{'type' : 'PublicTransport', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addPublic' : false, 'distance' : 0, 'vehicle' : 'bus'}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals(null, request.execute());
    }
}