package server.db;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Query extends Adapter {

    /**
     * Execute any given query, SELECT or otherwise. Returns a ResultSet array for results
     * from any SELECT queries that were passed.
     *
     * @param query The queries given as string to be executed
     * @param db    A query object if you want to continue an existing connection
     * @return a ResultSet array of SELECT query results, in order corresponding to that
     *         of the query array order.
     *         I.E.: the first select query appears in the return array first, the second second.
     *         Returns an empty array if no SELECT queries were specified.
     */
    public static ResultSet[] runQueries(String query[], Query db) {
        if (db == null) {
            db = new Query();
        }
        db.connect();

        try {
            Statement stmnt = conn.createStatement();
            conn.setAutoCommit(false);
            ArrayList<ResultSet> resultSets = new ArrayList<>();

            for (int i = 0; i < query.length; i++) {
                if (query[i].contains("SELECT")) {
                    //SELECT query, add resultSet to array
                    try {
                        PreparedStatement select = conn.prepareStatement(query[i]);
                        ResultSet resultSet = select.executeQuery();
                        resultSets.add(resultSet);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Error executing SELECT query");
                    }

                } else {
                    //Not a SELECT query, so it can be safely added to the batch.
                    try {
                        stmnt.addBatch(query[i]);
                        stmnt.executeUpdate(query[i]);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            conn.commit();
            db.disconnect();
            return resultSets.toArray(new ResultSet[resultSets.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Basic version of runQueries, starts and ends a new database connection.
     * Executes any query, SELECT or otherwise.
     *
     * @param query Queries to be executed as a string
     * @return Array of result sets for the select queries.
     */
    public static ResultSet[] runQueries(String query[]) {
        return runQueries(query, null);
    }

    /**
     * runQueries identical to the other one, but with authentication.
     *
     * @param query    Same as in the other runQueries
     * @param username Username given by the client
     * @param password Raw password given by the client
     * @return The result of the queries same as in the other one,
     *      but null if authentication fails.
     */
    public static ResultSet[] runQueries(String query[], String username, String password) {
        Query db = new Query();
        db.connect();
        //auth
        String sql = "SELECT username, password FROM client WHERE username = '"+username+"'";

        try {
            PreparedStatement select = conn.prepareStatement(sql);
            ResultSet resultSet = select.executeQuery();
            resultSet.next();
            String passHash = resultSet.getString("password");

            if(BCrypt.checkpw(password, passHash)){
                return runQueries(query, db);
            } else{
                System.out.println("Authentication error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing authentication SELECT query");
        }

        return null;
    }

    /**
     * Takes in raw password and salt, returns a SHA512 hash from that.
     *
     * @param password The raw password to be hashed.
     * @param salt     The salt that will be used in the algorithm.
     * @return A String that is the SHA512 hash of the password and salt.
     */
    public static String getHashedPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }
}