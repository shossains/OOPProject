package server.queries;

import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendQuery extends ServerQuery {
    private String friend;
    private boolean addFriend;
    private boolean getFriends;


    public String runQuery () {
        if(getFriends == true) {
            String[] queries = new String[1];
            queries[0] = "SELECT friends FROM friendlist WHERE username = '" + username + "'";
            ResultSet[] rsArray = Query.runQueries(queries);
            ResultSet rs = rsArray[0];
            String res;
            try {
                rs.next();
                res = rs.getString(1);
                while(rs.next()) {
                    res +=  ", " + rs.getString(1);
                } rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(addFriend == true) {
            String[] queries = new String[1];
            queries[0] = "INSERT INTO friendlist (username, friends) VALUES('" + username + "', '" +  friend + "')";
            ResultSet[] rsArray = Query.runQueries(queries);
            ResultSet rs = rsArray[0];
            try {
                while(rs.next()) {
                    String res = rs.getString(1);
                    rs.close();
                    return res;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "1";
        } else {
            String[] queries = new String[2];
            queries[0] = "SELECT username FROM client WHERE username = '" + friend + "'"; //check if user exists at all
            queries[1] = "SELECT friends FROM friendlist WHERE friends = '" + friend + "'"; //check if already in friendlist

            ResultSet[] rsArray = Query.runQueries(queries);
            ResultSet rs = rsArray[0];
            ResultSet rs2 = rsArray[1];

            try {
                while (rs.next()) {
                    String res = rs.getString(1);
                    String res2 = "NULL";
                    if(rs2.next()) {
                        res2 = rs2.getString(1);
                    }
                    rs.close();
                    rs2.close();
                    if (!res.equals("NULL") && !res.equals(username) && res2.equals("NULL")) { //TODO test middle part
                        return "1";
                    }
                    return "-1";
                }
                return "-1";
            } catch (SQLException e) {
                e.printStackTrace();
                return "Error in resultset";
            }
        }
    }
}
