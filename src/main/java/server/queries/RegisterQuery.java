package server.queries;

import org.springframework.security.crypto.bcrypt.BCrypt;
import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Fields needed for registration of a new user.
 */
public class RegisterQuery extends ServerQuery {
    private String fname;
    private String lname;
    private String email;
    private String phone;


    /**
     * Runs the actual registration process.
     * Logic for actually running the query:
     * 1: Make sure that the username doesn't already exist
     * 2: Construct the database query string, creating rows for each table
     * 3: Run it
     * 4: Make sure it successfully registered a new user and its tables
     *
     * @return result of registration.
     */
    public String runQuery() {
        if (usernameExists()) {
            return "{'error': true, 'reason' : 'Username already exists'}";
        }

        Query.runQueries(getQueries());
        return "{'error' : false}";
        //return "{'error': true, 'reason' : 'not fully implemented yet'}";
    }

    /**
     * Constructs queries for the insertion of the new user to the database.
     * Note that for some reason the points table needs to be updated first.
     *
     * @return Returns the queries needed to run.
     */
    private String[] getQueries() {
        String[] queries = new String[2];
        //add to client
        queries[1] = "INSERT INTO client \n"
                + "VALUES ('" + username + "', '" + fname + "', '" + lname + "',"
                + " '" + email + "', '" + getHashedPassword(password) + "', '" + phone + "')";

        //add to points
        queries[0] = "INSERT INTO points VALUES ('" + username + "', 0, CURRENT_TIMESTAMP(0))";

        return queries;
    }

    /**
     * Checks whether the username is already in use.
     *
     * @return Whether or not the username exists in the main client table.
     */
    public boolean usernameExists() {
        String[] queries = {"SELECT username FROM client WHERE username = '" + username + "'"};
        ResultSet rs = Query.runQueries(queries)[0];
        try {
            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }


    /**
     * Takes in raw password and salt, returns a SHA512 hash from that.
     *
     * @param password The raw password to be hashed.
     * @return A String that is the SHA512 hash of the password and salt.
     */
    public static String getHashedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
}
