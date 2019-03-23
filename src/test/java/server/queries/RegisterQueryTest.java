package server.queries;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.db.Query;

public class RegisterQueryTest {
    private static String existingTestUser = "testUser";
    private static String regQueryJSON = "{'type': 'register', 'username' : '"+existingTestUser+"', 'password' : 'hunter2'}";

    @BeforeClass
    public static void init(){
        String existingUserHash = "$2a$10$bi7JYEY2MDeWYw7CH1KpzuN7xm3YcvfnefX58MQLQP3jdOm2/Bi0C";
        String[] queries = new String[1];
        queries[0] = "UPDATE client \n SET password = '"+existingUserHash+"'," +
                "phone = '0' \n WHERE username = '"
                + existingTestUser +"'";
        Query.runQueries(queries);
    }

    @Test
    public void userNameExistsTest(){
        GsonBuilder gson = new GsonBuilder();
        RegisterQuery regQuery = gson.create().fromJson(regQueryJSON, RegisterQuery.class);
        Assert.assertTrue(regQuery.usernameExists());
    }

}