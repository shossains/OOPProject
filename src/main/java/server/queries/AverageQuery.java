package server.queries;

import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AverageQuery {
    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * TODO: Cleanup, add helper functions to make it more readable.
     * TODO: Figure out how new points can be queried in log
     *
     * @return json-format string of the amount of points of the username
     */

    public String runQuery() {
        String[] queries = new String[7];
        queries[0] = "SELECT SUM (points) AS total FROM vegetarian";
        queries[1] = "SELECT SUM (points) AS total FROM localproduce";
        queries[2] = "SELECT SUM (points) AS total FROM bikeride";
        queries[3] = "SELECT SUM (points) AS total FROM publictransport";
        queries[4] = "SELECT SUM (points) AS total FROM temperature";
        queries[5] = "SELECT SUM (points) AS total FROM solar";
        queries[6] = "SELECT COUNT(*) FROM client";

        ResultSet[] rsArray = Query.runQueries(queries);
        ResultSet rsVeg = rsArray[0];
        ResultSet rsLocProd = rsArray[1];
        ResultSet rsBike = rsArray[2];
        ResultSet rsPubTrans = rsArray[3];
        ResultSet rsTemp = rsArray[4];
        ResultSet rsSolar = rsArray[5];
        ResultSet rsUsers = rsArray[6];


        try {
            while (rsVeg.next() && rsLocProd.next() && rsBike.next()
                    && rsPubTrans.next() && rsTemp.next() && rsSolar.next() && rsUsers.next()) {
                final int resVeg = rsVeg.getInt(1);
                final int resLocProd = rsLocProd.getInt(1);
                final int resBike = rsBike.getInt(1);
                final int resPubTrans = rsPubTrans.getInt(1);
                final int resTemp = rsTemp.getInt(1);
                final int resSolar = rsSolar.getInt(1);
                final int resUsers = rsUsers.getInt(1);
                rsVeg.close();
                rsLocProd.close();
                rsBike.close();
                rsPubTrans.close();
                rsTemp.close();
                rsSolar.close();
                rsUsers.close();
                String resStr = "{'vegPoints' : " + resVeg  + ", 'locProdPoints' : " + resLocProd
                        + ", 'bikePoints' : " + resBike + ", 'pubTransPoints' : " + resPubTrans
                        + ", 'tempPoints' : " + resTemp + ", 'solarPoints' : " + resSolar
                        + ", 'users' : " + resUsers + "}";
                return resStr;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error in resultset";
        }
    }
}