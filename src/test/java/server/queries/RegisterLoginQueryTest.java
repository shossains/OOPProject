package server.queries;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterLoginQueryTest {
    private static String existingUserHash;
    private static String existingTestUser = "testUser";
    private static String regQueryJSON = "{'type': 'Register', 'username' : '"+existingTestUser+"', 'password' : 'hunter2'}";
    private static RegisterQuery testRegQuery;

    @BeforeClass
    public static void init(){
        existingUserHash = "$2a$10$bi7JYEY2MDeWYw7CH1KpzuN7xm3YcvfnefX58MQLQP3jdOm2/Bi0C";
        String[] queries = new String[1];
        queries[0] = "UPDATE client \n SET password = '"+existingUserHash+"'," +
                "phone = '0' \n WHERE username = '"
                + existingTestUser +"'";
        Query.runQueries(queries);

        GsonBuilder gson = new GsonBuilder();
        testRegQuery = gson.create().fromJson(regQueryJSON, RegisterQuery.class);
    }

    @Test
    public void hashTest(){
        String password = "hunter2";
        String salt = "$2a$10$bi7JYEY2MDeWYw7CH1Kpzu";

        String hash = testRegQuery.getHashedPassword(password);
        Assert.assertTrue(BCrypt.checkpw(password, hash));
    }

    @Test
    public void userNameExistsTest(){
        Assert.assertTrue(testRegQuery.usernameExists());
        testRegQuery.username = "aklsfja;klfdj;ljk";
        Assert.assertFalse(testRegQuery.usernameExists());
    }

    @Test
    public void fullRegTest(){
        String json = "{'type': 'Register', 'username' : 'registerTest', 'password' : 'sickPassword'," +
                " 'fname' : 'Test', 'lname' : 'User', 'email' : 'fraud@tudelft.nl', 'phone' : '12345678'}";

        RegisterQuery registerQuery = new GsonBuilder().create().fromJson(json, RegisterQuery.class);

        Assert.assertEquals("{'error' : false}", registerQuery.runQuery());
    }

    @Test
    public void checkUpdateTest(){
        String[] queries = new String[2];
        queries[0] = "SELECT * FROM client WHERE username='registerTest'";
        queries[1] = "SELECT * FROM points WHERE username='registerTest'";

        ResultSet[] rs = Query.runQueries(queries);

        try {
            Assert.assertTrue(rs[0].next());
            Assert.assertTrue(rs[1].next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            // assert that the usernames were added successfully
            Assert.assertEquals("registerTest", rs[0].getString("username"));
            Assert.assertEquals("registerTest", rs[1].getString("username"));

            //assert that some others were correct as well
            Assert.assertEquals("fraud@tudelft.nl", rs[0].getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginTest(){
        LoginQuery loginQuery = new GsonBuilder().create().fromJson("{'type':'Login',"
                + " 'username' : '"+existingTestUser+"', 'password' : 'hunter2'}", LoginQuery.class);

        Assert.assertEquals("{'login' : true}", loginQuery.runQuery());
    }

    @Test
    public void badLoginTest(){
        LoginQuery loginQuery = new GsonBuilder().create().fromJson("{'type':'Login',"
                + " 'username' : '"+existingTestUser+"', 'password' : 'hunter3'}", LoginQuery.class);

        Assert.assertEquals("{'login' : false}", loginQuery.runQuery());
    }


    @AfterClass
    public static void cleanup(){
        String[] query = new String[2];
        query[0] = "DELETE FROM client WHERE username='registerTest'";
        query[1] = "DELETE FROM points WHERE username='registerTest'";

        Query.runQueries(query);
    }

}