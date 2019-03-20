package server;

import application.VegController;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.db.Query;

import java.util.Arrays;

public class VegMealTest {

    /**
     * as in VegController, not vietcong
     */
    static private VegController vc;
    static final String testUserRow = "testUser";


    /**
     * initializes variables, clean up test entry in users
     */
    @BeforeClass
    public static void init(){
        vc = new VegController();

        //set the score to 0 on the test row
        String[] queries = new String[1];
        queries[0] = "UPDATE points \n SET points = 0\n WHERE username = '"
                + testUserRow +"'";
        Query.runQueries(queries);

    }

    /**
     * Test if json parsing used in the class actually parses
     */
    @Test
    public void JsonParsing(){
        int points = vc.parsePoints("{'points': 43}");
        Assert.assertEquals(43, points);
    }

    /**
     * Testing reaction to a bad json response
     */
    @Test
    public void BadJsonParsing(){
        int points = vc.parsePoints("{'points': '43'}");
        Assert.assertEquals(-1, points);
        points = vc.parsePoints("{'points': false}");
        Assert.assertEquals(-1, points);
        points = vc.parsePoints("what");
        Assert.assertEquals(-1, points);
    }

    /**
     * Tests whether the database returns the correct json via the VegMealQuery class.
     */
    @Test
    public void vegMealQueryJson(){
        String testString = "{'type':'VegMeal','username': '"+ testUserRow +"', 'addMeal': true }";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{\"points\" : 50}", request.execute());
    }

    /**
     * Test wheter the array is not messing up the queries
     */
    @Test
    public void testOneQuery(){
        String[] queries = new String[1];
        queries[0] = "UPDATE points SET points = 0 WHERE username = '"
                + testUserRow +"'";

        Assert.assertEquals("[UPDATE points SET points = 0 WHERE username = 'testUser']", Arrays.toString(queries));
    }

    /**
     * Test if the order is not messed up in the array
     */
    @Test
    public void testTwoQueries(){
        String[] queries = new String[2];
        queries[0] = "UPDATE points SET points = 0 WHERE username = '"
                + testUserRow +"'";
        queries[1] = "SELECT * FROM points WHERE username = '"
                + testUserRow +"'";

        Assert.assertEquals("SELECT * FROM points WHERE username = 'testUser'", queries[1].toString());
    }

    @Test
    public void testQueryReturn(){
        //TODO
    }
}
