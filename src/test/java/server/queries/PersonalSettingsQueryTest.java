package server.queries;

import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Test;
import server.Request;
import server.db.Query;

public class PersonalSettingsQueryTest {

    static final String testUserRow = "testUser";
    static final String testUserPass = "hunter2";

    /**
     * Tests if correcct email is returned.
     */
    @Test
    public void emailco2Test() {
        String[] queries = new String[1];
        queries[0] = "UPDATE points SET co2 = 0 WHERE username = '"
                + testUserRow + "'";
        Query.runQueries(queries);
        //reset db
        String testString = "{'type' : 'Settings', 'username' : '"
                + testUserRow + "', 'password' : '" + testUserPass + "'}";
        Request request = new GsonBuilder().create().fromJson(testString, Request.class);
        request.setRaw(testString);

        Assert.assertEquals("{'email' : 'empty@hotmail.com', 'co2' : 0.0}", request.execute());
    }
}
