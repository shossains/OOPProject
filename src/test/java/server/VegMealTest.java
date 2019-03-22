package server;

import application.VegController;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.db.Query;
import server.queries.VegMealQuery;

public class VegMealTest {

    /**
     * as in VegController, not vietcong
     */
    static private VegController vc;
    static final String testUser = "testUser";


    /**
     * initializes variables, clean up test entry in users
     */
    @BeforeClass
    public static void init(){
        vc = new VegController();

        //set the score to 0 on the test row
        String[] queries = new String[1];
        queries[0] = "UPDATE points \n SET points = 0\n WHERE username = '"
                + testUser +"'";
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
    public void vegMealQueryJsonResponse(){
        String testString = "{'type':'VegMeal','username': '"+ testUser +"', 'addMeal': true }";
        VegMealQuery request = new GsonBuilder().create().fromJson(testString, VegMealQuery.class);
        Assert.assertEquals("{\"points\" : 50}", request.runQuery());
    }

    /**
     * Tests whether the database returns the correct json via the Request class.
     */
    @Test
    public void vegMealQueryJsonResponseFull(){
        String testString = "{'type':'VegMeal','username': '"+ testUser +"', 'addMeal': true }";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{\"points\" : 100}", request.execute());
    }
}