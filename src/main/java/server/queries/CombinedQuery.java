package server.queries;

import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CombinedQuery extends ServerQuery {
    /**
     * Connects to the database and executes the query to add ...
     * @return json-format string of the amount of points of the username
     */

    public String runQuery() {
        String[] queries = new String[6];
        queries[0] = "SELECT SUM (points) AS total FROM vegetarian "
                + "WHERE username = '" + username + "'";
        queries[1] = "SELECT SUM (points) AS total FROM localproduce "
                + "WHERE username = '" + username + "'";
        queries[2] = "SELECT SUM (points) AS total FROM bikeride "
                + "WHERE username = '" + username + "'";
        queries[3] = "SELECT SUM (points) AS total FROM publictransport "
                + "WHERE username = '" + username + "'";
        queries[4] = "SELECT SUM (points) AS total FROM temperature "
                + "WHERE username = '" + username + "'";
        queries[5] = "SELECT SUM (points) AS total FROM solar "
                + "WHERE username = '" + username + "'";

        ResultSet[] rsArray = Query.runQueries(queries);
        ResultSet rsVeg = rsArray[0];
        ResultSet rsLocProd = rsArray[1];
        ResultSet rsBike = rsArray[2];
        ResultSet rsPubTrans = rsArray[3];
        ResultSet rsTemp = rsArray[4];
        ResultSet rsSolar = rsArray[5];


        try {
            while (rsVeg.next() && rsLocProd.next() && rsBike.next()
                    && rsPubTrans.next() && rsTemp.next() && rsSolar.next()) {
                final int resVeg = rsVeg.getInt(1);
                final int resLocProd = rsLocProd.getInt(1);
                final int resBike = rsBike.getInt(1);
                final int resPubTrans = rsPubTrans.getInt(1);
                final int resTemp = rsTemp.getInt(1);
                final int resSolar = rsSolar.getInt(1);
                rsVeg.close();
                rsLocProd.close();
                rsBike.close();
                rsPubTrans.close();
                rsTemp.close();
                rsSolar.close();
                String resStr = "{'vegPoints' : " + resVeg  + ", 'locProdPoints' : " + resLocProd
                        + ", 'bikePoints' : " + resBike + ", 'pubTransPoints' : " + resPubTrans
                        + ", 'tempPoints' : " + resTemp + ", 'solarPoints' : " + resSolar + "}";
                return resStr;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error in resultset";
        }
    }
}
