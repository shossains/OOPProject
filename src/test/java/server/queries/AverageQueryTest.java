package server.queries;

import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Request;
import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AverageQueryTest {
    static int resUsers;

    /**
     * initializes variables, clean up test entry in users
     */
    @BeforeClass
    public static void init() {
        //set the score to 0 on the test row
        String[] queries = new String[7];
        queries[0] = "UPDATE vegetarian SET points = 0";
        queries[1] = "UPDATE localproduce SET points = 0";
        queries[2] = "UPDATE bikeride SET points = 0";
        queries[3] = "UPDATE publictransport SET points = 0";
        queries[4] = "UPDATE temperature SET points = 0";
        queries[5] = "UPDATE solar SET points = 0";
        queries[6] = "SELECT COUNT(*) FROM client";
        ResultSet[] rsArray = Query.runQueries(queries);
        ResultSet rsUsers = rsArray[0];
        try {
            while (rsUsers.next()) {
                resUsers = rsUsers.getInt(1);
            }
            rsUsers.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in resultset");
        }
    }

    /**
     * Tests for the combined request
     */
    @Test
    public void AverageRequest(){
        String testString = "{'type' : 'Average'}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);
        Assert.assertEquals("{'vegPoints' : 0, 'locProdPoints' : 0, 'bikePoints' : 0, 'pubTransPoints' : 0, 'tempPoints' : 0, 'solarPoints' : 0, 'users' : " + resUsers + "}", request.execute());
    }
}