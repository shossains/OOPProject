package server;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationTest {
    static final String testUserRow = "testUser";
    static final String hunter2Hash = "$2a$10$bi7JYEY2MDeWYw7CH1KpzuN7xm3YcvfnefX58MQLQP3jdOm2/Bi0C";

    @BeforeClass
    public static void init(){
        String[] queries = new String[1];
        queries[0] = "UPDATE client \n SET password = '"+hunter2Hash+"'," +
                "phone = '0' \n WHERE username = '"
                + testUserRow +"'";
        Query.runQueries(queries);
    }

    @Test
    public void queryAuthTest(){
        String[] query = new String[2];
        query[0] = "UPDATE client \n SET phone = '123456' \n" +
                " WHERE username = '"+testUserRow+"'";
        query[1] = "SELECT phone FROM client WHERE username = 'testUser'";

        try {
            ResultSet resquery = Query.runQueries(query, testUserRow, "hunter2")[0];
            resquery.next();
            Assert.assertTrue(resquery.getString("phone").equals("123456"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void wrongPasswordTest(){
        String[] query = new String[2];
        query[0] = "UPDATE client \n SET phone = '654321' \n" +
                " WHERE username = '"+testUserRow+"'";
        query[1] = "SELECT phone FROM client WHERE username = 'testUser'";

            ResultSet[] resquery = Query.runQueries(query, testUserRow, "hunter3");
            Assert.assertTrue(resquery == null);
    }
}
