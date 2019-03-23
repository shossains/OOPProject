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
    public void hashTest(){
        String password = "hunter2";
        String salt = "$2a$10$bi7JYEY2MDeWYw7CH1Kpzu";
        String hash = Query.getHashedPassword(password, salt);
        Assert.assertEquals(hunter2Hash, hash);
        Assert.assertTrue(BCrypt.checkpw(password, hash));
    }

    @Test
    public void queryAuthTest(){
        String[] query = {"UPDATE client \n SET phone = '123456' \n" +
                " WHERE username = '"+testUserRow+"'"};

        try {
            ResultSet resquery = Query.runQueries(query, testUserRow, "hunter2")[0];
            resquery.next();
            System.out.println(resquery.getString("phone"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
