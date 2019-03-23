package server.queries;

import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Fields needed for registration of a new user.
 */
public class RegisterQuery extends ServerQuery {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;


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

        return "{'error': true, 'reason' : 'not implemented yet'}";
    }

    private String[] getQueries() {
        String[] queries = new String[0];
        //check if username exists
        queries[0] = "SELECT username FROM client WHERE username = '" + username + "'";


        return queries;
    }

    /**Checks whether the username is already in use.
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
        }


        return true;
    }
}
