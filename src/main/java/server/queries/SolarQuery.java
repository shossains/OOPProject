package server.queries;

import calculator.SolarPanelCalculator;
import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SolarQuery extends ServerQuery {
    private Boolean addSolar;
    private int kwh;
    private int addPoints;
    private Double co2;

    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        co2 = SolarPanelCalculator.solar(kwh);
        addPoints = co2.intValue();

        if (addSolar) {
            ResultSet[] rsArray = runQueries(addPoints);
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
        } else {
            return null;
        }
    }

    /**
     * Runs db duties for the meal, as well as authentication, returns resultset array.
     * @param pointsToBeAdded points to be added for the meal
     * @return resultset with the current points total.
     */
    private ResultSet[] runQueries(int pointsToBeAdded) {
        String[] queries = new String[4];
        queries[0] = "UPDATE points SET points = points + " + pointsToBeAdded + ", last_updated = "
                + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

        queries[1] = "INSERT INTO solar "
                + "(username, points, kwh, datetime, co2) values"
                + " ('" + username + "'," + pointsToBeAdded + ",'" + kwh
                + "',CURRENT_TIMESTAMP(0), " + co2 + ")";

        queries[2] = "SELECT points FROM points WHERE username = '" + username + "'";

        queries[3] = "UPDATE points SET co2 = co2 + " + co2
                + " WHERE username = '" + username + "'";

        return Query.runQueries(queries);
    }
}