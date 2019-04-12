package server.queries;

import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Request;
import server.db.Query;

public class TemperatureQueryTest {

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
     * Tests for the request of temp decrease of 0
     */
    @Test
    public void TemperatureReqeust(){
        String[] queries = new String[1];
        queries[0] = "UPDATE points SET points = 0 WHERE username = '"
                + testUserRow + "'";
        Query.runQueries(queries);

        String testString = "{'type' : 'Temp', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addTemp' : true, 'thigh' : 0, 'tlow' : 0}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{'points' : 0, 'added' : 0, 'co2' : 0.0}", request.execute());
    }

    /**
     * Tests is addTemp is false with no records.
     */
    @Test
    public void addTempFalseEmpty(){
        //reset db
        String[] queries = new String[1];
        queries[0] = "DELETE FROM temperature WHERE username = '"
                + testUserRow + "'";
        Query.runQueries(queries);

        String testString = "{'type' : 'Temp', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "',"
                + "'addTemp': false}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals(null, request.execute());
    }

    /**
     * Tests for the printing of the records
     */
    @Test
    public void addTempFalsePrint(){
        String[] queries = new String[3];
        queries[0] = "DELETE FROM temperature WHERE username = '" + testUserRow + "';";
        queries[1] = "INSERT INTO temperature VALUES ('testUser',15,5,'2019-03-29 00:00:00',3)";
        queries[2] = "INSERT INTO temperature VALUES ('testUser',15,5,'2019-03-29 00:00:00',3)";
        Query.runQueries(queries);

        String testString = "{'type' : 'Temp', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "',"
                + "'addTemp': false}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        request.execute();

        Assert.assertEquals("[{'points' : 15,'temperature' : 5,'datetime' : '2019-03-29 00:00:00'}, {'points' : 15,'temperature' : 5,'datetime' : '2019-03-29 00:00:00'}]", request.execute());
    }
}