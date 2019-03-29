package server.queries;

import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Request;
import server.db.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LocalProduceQueryTest {

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
        Query.runQueries(queries, testUserRow, testUserPass);
    }

    /**
     * Tests for the request of the weight 0.
     */
    @Test
    public void LocalProduceWeight(){
        String testString = "{'type' : 'LocalProduce', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addLocal' : true, 'weight' : 1500}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{'points' : 49 , 'added' : 49 , 'co2' : 0.49}", request.execute());
    }

    /**
     * Tests for the request of the weight 0.
     */
    @Test
    public void LocalProduceZero(){
        String testString = "{'type' : 'LocalProduce', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addLocal' : true, 'weight' : 0}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{'points' : 0 , 'added' : 0 , 'co2' : 0.0}", request.execute());
    }

    /**
     * Tests is addLocal is false and no records.
     */
    @Test
    public void addLocalFalseEmpty(){
        //reset db
        String[] queries = new String[1];
        queries[0] = "DELETE FROM localproduce WHERE username = '"
                + testUserRow + "'";
        Query.runQueries(queries, testUserRow, testUserPass);

        String testString = "{'type' : 'LocalProduce', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "',"
                + "'addLocal': false}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals(null, request.execute());
    }

    /**
     * Tests for the printing of the records
     */
    @Test
    public void addLocalFalsePrint(){
        //reset db
        String[] queries = new String[1];
        queries[0] = "DELETE FROM localproduce WHERE username = '"
                + testUserRow + "'";
        Query.runQueries(queries, testUserRow, testUserPass);

        String testString = "{'type' : 'LocalProduce', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addLocal' : true, 'weight' : 0}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        request.execute();

        String testString2 = "{'type' : 'LocalProduce', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "', "
                + "'addLocal' : false}";
        Request request2 = new GsonBuilder().create().fromJson(testString2, Request.class);
        request2.setRaw(testString2);
        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        Assert.assertEquals("[{'points' : 0,'weight' : 0,'datetime' : '"+datetime+"+01'}]", request2.execute());
    }
}