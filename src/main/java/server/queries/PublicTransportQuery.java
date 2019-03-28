package server.queries;

import calculator.BusCalculator;
import calculator.TrainCalculator;
import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublicTransportQuery extends ServerQuery {
    private Boolean addPublic;
    private int distance;
    private String vehicle;
    private Double co2;
    private int addPoints;

    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        co2 = 0.0;
        addPoints = 0;

        if (vehicle.equals("bus")) {
            co2 = BusCalculator.bus(distance);

            Double temp = BusCalculator.bus(distance) * 10;
            addPoints = temp.intValue();
        } else if (vehicle.equals("train")) {
            try {
                co2 = TrainCalculator.train(distance);
                Double temp = TrainCalculator.train(distance) * 10;
                addPoints = temp.intValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (addPublic) {

            ResultSet[] rsArray = runQueries(addPoints);
            ResultSet rs = rsArray[0];

            try {
                while (rs.next()) {
                    int res = rs.getInt(1);
                    rs.close();

                    return "{'points' : " + res + " , 'added' : "
                            + addPoints + " , 'co2' : " + co2 + "}";
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

        queries[1] = "SELECT points FROM points WHERE username = '" + username + "'";

        queries[2] = "INSERT INTO publictransport"
                + "(username, points, vehicletype, distance, datetime, co2) values"
                + " ('" + username + "', " + pointsToBeAdded + ", '" + vehicle
                + "', '" + distance + "', CURRENT_TIMESTAMP(0), " + co2 + ")";

        queries[3] = "UPDATE points SET co2 = co2 + " + co2
                + " WHERE username = '" + username + "'";

        return Query.runQueries(queries);
    }
}