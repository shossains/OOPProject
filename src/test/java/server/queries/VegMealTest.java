package server.queries;

import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Request;
import server.db.Query;

public class VegMealTest {

    static final String testUserRow = "testUser";
    static final String testUserPass = "hunter2";


    /**
     * initializes variables, clean up test entry in users
     */
    @BeforeClass
    public static void init() {
        //set the score to 0 on the test row
        String[] queries = new String[1];
        queries[0] = "UPDATE points \n SET points = 0\n WHERE username = '"
                + testUserRow + "'";
        Query.runQueries(queries, testUserRow, testUserPass);
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
