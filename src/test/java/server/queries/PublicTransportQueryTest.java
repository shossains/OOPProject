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
        String[] queries = new String[2];
        queries[0] = "DELETE FROM publictransport WHERE username = '" + testUserRow + "';";
        queries[1] = "UPDATE points SET points = 0 WHERE username = '" + testUserRow + "'";
        Query.runQueries(queries);

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
        String[] queries = new String[2];
        queries[0] = "DELETE FROM publictransport WHERE username = '" + testUserRow + "';";
        queries[1] = "UPDATE points SET points = 0 WHERE username = '" + testUserRow + "'";
        Query.runQueries(queries);

        String testString = "{'type' : 'PublicTransport', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addPublic' : true, 'distance' : 0, 'vehicle' : 'train'}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{'points' : 0 , 'added' : 0 , 'co2' : 0.0}", request.execute());
    }

    /**
     * Tests is addPublic is false with no records.
     */
    @Test
    public void addPublicFalseEmpty(){
        //reset db
        String[] queries = new String[1];
        queries[0] = "DELETE FROM publictransport WHERE username = '"
                + testUserRow + "'";
        Query.runQueries(queries);

        String testString = "{'type' : 'PublicTransport', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addPublic' : false, 'distance' : 0, 'vehicle' : 'bus'}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals(null, request.execute());
    }

    /**
     * Tests for the printing of the records
     */
    @Test
    public void addPublicFalsePrint(){
        String[] queries = new String[4];
        queries[0] = "UPDATE points SET points = 0 WHERE username = '" + testUserRow + "';";
        queries[1] = "DELETE FROM publictransport WHERE username = '" + testUserRow + "';";
        queries[2] = "INSERT INTO publictransport VALUES ('testUser',30,'bus',15,'2019-03-29 00:00:00',1)";
        queries[3] = "INSERT INTO publictransport VALUES ('testUser',30,'bus',15,'2019-03-29 00:00:00',1)";
        Query.runQueries(queries);

        String testString = "{'type' : 'PublicTransport', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "',"
                + "'addPublic': false}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        request.execute();

        Assert.assertEquals("[{'points' : 30,'distance' : 15,'datetime' : '2019-03-29 00:00:00'}, {'points' : 30,'distance' : 15,'datetime' : '2019-03-29 00:00:00'}]", request.execute());
    }
}
