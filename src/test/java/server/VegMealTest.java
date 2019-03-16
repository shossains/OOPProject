package server;

import application.VegController;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class VegMealTest {

    static private VegController vc;


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
    @Test
    public void BadJsonParsing(){
        int points = vc.parsePoints("{'points': '43'}");
        Assert.assertEquals(-1, points);
        points = vc.parsePoints("{'points': false}");
        Assert.assertEquals(-1, points);
    }
}
