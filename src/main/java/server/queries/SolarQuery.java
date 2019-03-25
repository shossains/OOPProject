package server.queries;

import calculator.SolarPanelCalculator;
import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SolarQuery extends ServerQuery {
    private Boolean addSolar;
    private int kwh;

    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        Double co2 = SolarPanelCalculator.solar(kwh);
        int addPoints = co2.intValue();

        if (addSolar) {
            String[] queries = new String[4];
            queries[0] = "UPDATE points SET points = points + " + addPoints + ", last_updated = "
                    + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

            queries[1] = "INSERT INTO solar "
                    + "(username, points, kwh, datetime, co2) values"
                    + " ('" + username + "'," + addPoints + ",'" + kwh
                    + "',CURRENT_TIMESTAMP(0), " + co2 + ")";

            queries[2] = "SELECT points FROM points WHERE username = '" + username + "'";

            queries[3] = "UPDATE points SET co2 = co2 + " + co2
                    + " WHERE username = '" + username + "'";

            //should be one function
            ResultSet[] rsArray = Query.runQueries(queries);
            ResultSet rs = rsArray[0];

            try {
                while (rs.next()) {
                    int res = rs.getInt(1);
                    rs.close();

                    return "{'points' : " + res + ", 'added' : "
                            + addPoints + ", 'co2' : " + co2 + "}";
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return "{'error' : true, 'reason' : 'Error parsing resultset'}";
            }
        } else if (!addSolar) {
            String[] newquery = new String[1];
            newquery[0] = "SELECT count(*) FROM solar WHERE username = '"
                    + username + "'";

            //should be one function
            ResultSet[] newrsArray = Query.runQueries(newquery);
            ResultSet newrs = newrsArray[0];

            try {
                while (newrs.next()) {
                    int newres = newrs.getInt(1);
                    return "{\"points\" : " + newres + "}";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "Error in resultset";
            }
        }
        return "{'error' : true, 'reason' : 'Error in query'}";
    }
}