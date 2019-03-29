package server.queries;

import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Request;
import server.db.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VegMealQueryTest {

    static final String testUserRow = "testUser";
    static final String testUserPass = "hunter2";


    /**
     * initializes variables, clean up test entry in users
     */
    @BeforeClass
    public static void init() {
        //set the score to 0 on the test row
        String[] queries = new String[2];
        queries[0] = "UPDATE points \n SET points = 0\n WHERE username = '"
                + testUserRow + "'";
        queries[1] = "DELETE FROM vegetarian WHERE username = '"
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

    /**
     * Tests for the request of the vegan meal
     */
    @Test
    public void vegMealQueryVegan(){
        //reset db
        String[] queries = new String[2];
        queries[0] = "UPDATE points \n SET points = 0\n WHERE username = '"
                + testUserRow + "'";
        queries[1] = "DELETE FROM vegetarian WHERE username = '"
                + testUserRow + "'";
        Query.runQueries(queries, testUserRow, testUserPass);

        String testString = "{'type' : 'VegMeal', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "',"
                + "'addMeal': true, 'mealType' : 'vegan'}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{'points' : 60 , 'added' : 60 , 'co2' : 1.5}", request.execute());
    }

    /**
     * Tests for the request of the vegetarian.
     */
    @Test
    public void vegMealQueryVegetarian(){
        //reset db
        String[] queries = new String[2];
        queries[0] = "UPDATE points \n SET points = 0\n WHERE username = '"
                + testUserRow + "'";
        queries[1] = "DELETE FROM vegetarian WHERE username = '"
                + testUserRow + "'";
        Query.runQueries(queries, testUserRow, testUserPass);

        String testString = "{'type' : 'VegMeal', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "',"
                + "'addMeal': true, 'mealType' : 'vegetarian'}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{'points' : 50 , 'added' : 50 , 'co2' : 1.0}", request.execute());
    }

    /**
     * Tests is addMeal is false and no records.
     */
    @Test
    public void addMealFalseEmpty(){
        //reset db
        String[] queries = new String[1];
        queries[0] = "DELETE FROM vegetarian WHERE username = '"
                + testUserRow + "'";
        Query.runQueries(queries, testUserRow, testUserPass);

        String testString = "{'type' : 'VegMeal', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "',"
                + "'addMeal': false}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals(null, request.execute());
    }

//    /**
//     * Tests for the printing of the records
//     */
//    @Test
//    public void addMealFalsePrint(){
//        String testString = "{'type' : 'VegMeal', 'username' : '"
//                + testUserRow + "', 'password' : '" + testUserPass + "',"
//                + "'addMeal': true, 'mealType' : 'vegetarian'}";
//        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
//        request.setRaw(testString);
//
//        String testString2 = "{'type' : 'VegMeal', 'username' : '"
//                + testUserRow + "', 'password' : '" + testUserPass + "', "
//                + "'addMeal' : false}";
//        Request request2 = new GsonBuilder().create().fromJson(testString2, Request.class);
//        request2.setRaw(testString2);
//
//        String testString3 = "{'type' : 'VegMeal', 'username' : '"
//                + testUserRow + "', 'password' : '" + testUserPass + "',"
//                + "'addMeal': true, 'mealType' : 'vegan'}";
//        Request request3 = new GsonBuilder().create().fromJson(testString3, Request.class);
//        request3.setRaw(testString3);
//
//        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
//
//        request3.execute();
//        request.execute();
//        Assert.assertEquals("[{'points' : 50,'type' : 'vegetarian','datetime' : '"+datetime+"+01'}, {'points' : 60,'type' : 'vegan','datetime' : '"+datetime+"+01'}]", request2.execute());
//    }
}