package server.queries;

import calculator.CarCalculator;
import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BikeRideQuery extends ServerQuery {
    private Boolean addBike;
    private int distance;

    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        Double co2 = CarCalculator.car(distance) / 1000;
        Double temp = CarCalculator.car(distance) / 10;
        int addPoints = temp.intValue();

        if (addBike) {
            String[] queries = new String[4];
            queries[0] = "UPDATE points SET points = points + " + addPoints + ", last_updated = "
                    + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

            queries[1] = "INSERT INTO bikeride (username, points, distance, datetime, co2)"
                    + "values ('" + username + "'," + addPoints + ",'"
                    + distance + "',CURRENT_TIMESTAMP(0), " + co2 + ")";

            queries[2] = "UPDATE points SET co2 = co2 + " + co2
                    + " WHERE username = '" + username + "'";

            queries[3] = "SELECT points FROM points WHERE username = '" + username + "'";

            ResultSet[] rsArray = Query.runQueries(queries);
            ResultSet rs = rsArray[0];

            try {
                while (rs.next()) {
                    int res = rs.getInt(1);
                    rs.close();

                    return "{'points' : " + res + " , 'added' : " + addPoints + " , 'co2' : " + co2 + "}";
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return "{'error' : true, 'reason' : 'Error parsing resultset'}";
            }
        } else if (!addBike) {
            String[] newquery = new String[1];
            newquery[0] = "SELECT count(*) FROM bikeride WHERE username = '" + username + "'";

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