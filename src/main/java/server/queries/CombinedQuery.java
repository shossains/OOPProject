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
        String[] queries = new String[6];
        queries[0] = "SELECT SUM (points) AS total FROM vegetarian WHERE username = '" + username + "'";
        queries[1] = "SELECT SUM (points) AS total FROM localproduce WHERE username = '" + username + "'";
        queries[2] = "SELECT SUM (points) AS total FROM bikeride WHERE username = '" + username + "'";
        queries[3] = "SELECT SUM (points) AS total FROM publictransport WHERE username = '" + username + "'";
        queries[4] = "SELECT SUM (points) AS total FROM temperature WHERE username = '" + username + "'";
        queries[5] = "SELECT SUM (points) AS total FROM solar WHERE username = '" + username + "'";

        ResultSet[] rsArray = Query.runQueries(queries);
        ResultSet rs = rsArray[0];
        ResultSet rs1 = rsArray[1];
        ResultSet rs2 = rsArray[2];
        ResultSet rs3 = rsArray[3];
        ResultSet rsTemp = rsArray[4];
        ResultSet rsSolar = rsArray[5];


        try {
            while (rs.next() && rs1.next() && rs2.next() && rs3.next() && rsTemp.next() && rsSolar.next()) {
                int res = rs.getInt(1);
                int res1 = rs1.getInt(1);
                int res2 = rs2.getInt(1);
                int res3 = rs3.getInt(1);
                int resTemp = rsTemp.getInt(1);
                int resSolar = rsSolar.getInt(1);
                rs.close();
                rs1.close();
                rs2.close();
                rs3.close();
                rsTemp.close();
                rsSolar.close();
                String resStr = "{'vegPoints' : " + res  + ", 'locProdPoints' : " + res1 + ", 'bikePoints' : " + res2 + ", 'pubTransPoints' : " + res3 + ", 'tempPoints' : " + resTemp + ", 'solarPoints' : " + resSolar + "}";
                return resStr;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error in resultset";
        }
    }
}