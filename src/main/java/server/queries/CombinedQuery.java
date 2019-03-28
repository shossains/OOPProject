package server.queries;

import server.db.Query;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CombinedQuery  extends ServerQuery {
    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * TODO: Cleanup, add helper functions to make it more readable.
     * TODO: Figure out how new points can be queried in log
     *
     * @return json-format string of the amount of points of the username
     */

    public String runQuery() {
        String[] queries = new String[3];
        queries[0] = "SELECT SUM (points) AS total FROM vegetarian WHERE username = '" + username + "'";
        queries[1] = "SELECT SUM (points) AS total FROM localproduce WHERE username = '" + username + "'";
        queries[2] = "SELECT SUM (points) AS total FROM bikeride WHERE username = '" + username + "'";

        ResultSet[] rsArray = Query.runQueries(queries);
        ResultSet rs = rsArray[0];
        ResultSet rs1 = rsArray[1];
        ResultSet rs2 = rsArray[2];


        try {
            while (rs.next() && rs1.next() && rs2.next()) {
                int res = rs.getInt(1);
                int res1 = rs1.getInt(1);
                int res2 = rs2.getInt(1);
                rs.close();
                rs1.close();
                rs2.close();
                String resStr = "{'vegPoints' : " + res  + ", 'locProdPoints' : " + res1 + ", 'bikePoints' : " + res2 + "}";
                return resStr;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error in resultset";
        }
    }
}