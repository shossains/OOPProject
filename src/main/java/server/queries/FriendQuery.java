package server.queries;

import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendQuery extends ServerQuery {
    private String friend;
    public String runQuery () {
        String[] queries = new String[1];
        queries[0] = "SELECT username FROM client WHERE username = '" + friend + "'";

        ResultSet[] rsArray = Query.runQueries(queries);
        ResultSet rs = rsArray[0];

        try {
            while(rs.next()) {
                String res = rs.getString(1);
                rs.close();
                if(!res.equals("NULL")) {
                    return "1";
                } return "-1";
            } return "-1";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error in resultset";
        }


    }
}
