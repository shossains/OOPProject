package server;

import application.VegController;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.db.Query;

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
    public void vegMealQueryBadJson(){
        String testString = "{'type':'VegMeal','username': '"+ testUserRow +"', 'addMeal': true}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{'error' : true, 'reason' : 'mealType not given'}", request.execute());
    }
}