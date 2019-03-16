package server;

import application.VegController;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.queries.VegMealQuery;

public class VegMealTest {

    /**
     * as in VegController, not vietcong
     */
    static private VegController vc;


    /**
     * initializes variables
     */
    @BeforeClass
    public static void init(){
        vc = new VegController();
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
        String testString = "{'type':'VegMeal','username':'shossain'}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("", request.execute());
    }


}
