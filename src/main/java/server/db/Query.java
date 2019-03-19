package server.db;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Query extends Adapter {

    /**
     * Execute any given query, as long as it is not a select query.
     * @param query The queries given as string to be executed
     */
    public static void query(String query[]) {
        Query db = new Query();
        db.connect();
        try {
            Statement stmnt = conn.createStatement();
            conn.setAutoCommit(false);
            ArrayList<ResultSet> resultSets = new ArrayList<>();

            for (int i = 0; i < query.length; i++) {
                if(query[i].contains("SELECT")){
                    //select query, save for later

                }else {
                    //add to batch
                    try {
                        stmnt.addBatch(query[i]);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            conn.commit();
            db.disconnect();
        }catch(Exception e){ //dont fucking do this
            e.printStackTrace();
        }
    }

    /**
     * Method that checks if the given email already exists in the database.
     * @param username the email that you want to check
     * @return true is it exists in the database
     */
    public boolean checkExistence(String username) {
        try {
            PreparedStatement st = conn.prepareStatement(
                    "SELECT username FROM client WHERE username = '" + username + "'");

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String res = rs.getString(1);

                if (res.equals(username)) {
                    return true;
                }
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks the amount of points a user has right now.
     * @param username the user that you want to check
     * @return the amount of points in JSON format
     */
    public static String eaten(String username) {
        Query db = new Query();
        db.connect();

        try {
            PreparedStatement st = conn.prepareStatement(
                    "SELECT points FROM points WHERE username = '" + username + "'");

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int res = rs.getInt(1);
                db.disconnect();
                return "{\"points\" : " + res + "}";
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.disconnect();
        return null;
    }

    /**
     * Method looks up in the database how many points someone has.
     * @param username The person you want to retrieve the information from
     * @return the amount of points
     */
    public static String checkPoints(String username) {
        Query db = new Query();
        db.connect();

        try {
            PreparedStatement st = conn.prepareStatement(
                    "SELECT points FROM points WHERE username = '" + username + "'");

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int res = rs.getInt(1);
                db.disconnect();
                return res+"";
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.disconnect();
        return null;
    }

    /**Verifies whether the username and password match
     * @return whether or no the user is authenticated
     */
    private boolean authenticate(){

        return false;
    }
}