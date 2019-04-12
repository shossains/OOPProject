package server.queries;

import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Request;
import server.db.Query;

import java.util.Arrays;

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
     * Tests for the request of solar panel usage
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
     * Tests is addSolar is false with no records.
     */
    @Test
    public void addSolarFalse(){
        String[] queries = new String[2];
        queries[0] = "DELETE FROM solar WHERE username = '" + testUserRow + "';";
        queries[1] = "UPDATE points SET points = 0 WHERE username = '" + testUserRow + "'";
        Query.runQueries(queries);

        String testString = "{'type' : 'Solar', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addSolar' : false, 'kwh' : 0}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals(null, request.execute());
    }

    /**
     * Tests for the printing of the records
     */
    @Test
    public void addPanelFalsePrint(){
        String[] queries = new String[4];
        queries[0] = "UPDATE points SET points = 0 WHERE username = '" + testUserRow + "';";
        queries[1] = "DELETE FROM solar WHERE username = '" + testUserRow + "';";
        queries[2] = "INSERT INTO solar VALUES ('testUser',20,150,'2019-03-29 00:00:00',2)";
        queries[3] = "INSERT INTO solar VALUES ('testUser',30,200,'2019-03-29 00:00:00',3)";
        Query.runQueries(queries);

        String testString = "{'type' : 'Solar', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "',"
                + "'addSolar': false}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        request.execute();

        Assert.assertEquals("[{'points' : 20,'kwh' : 150,'datetime' : '2019-03-29 00:00:00'}, {'points' : 30,'kwh' : 200,'datetime' : '2019-03-29 00:00:00'}]", request.execute());
    }
}