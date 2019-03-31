package server.queries;

import calculator.CarCalculator;
import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BikeRideQuery extends ServerQuery {
    private Boolean addBike;
    private int distance;
    private Double co2;
    private int addPoints;

    /**
     * Connects to the database and executes the query to add a bike ride.
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        co2 = CarCalculator.car(distance) / 1000;
        Double temp = CarCalculator.car(distance) / 10;
        addPoints = temp.intValue();

        if (addBike) {
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
            ResultSet[] rsArray = runSelect();
            ResultSet rs = rsArray[0];

            try {
                //If user had no logs in db
                if (rs.next()) {
                    //Ambiguity needed to prevent skipping first row from resultset
                    List<String> json = new ArrayList<>();
                    int firstPoint = rs.getInt(1);
                    int firstDistance = rs.getInt(2);
                    String firstDatetime = rs.getString(3);
                    String firstRow = "{'points' : " + firstPoint + ",'distance' : " + firstDistance
                            + ",'datetime' : '" + firstDatetime + "'}";
                    json.add(firstRow);

                    while (rs.next()) {
                        int points = rs.getInt(1);
                        int distance = rs.getInt(2);
                        String datetime = rs.getString(3);
                        String result = "{'points' : " + points + ",'distance' : " + distance
                                + ",'datetime' : '" + datetime + "'}";
                        json.add(result);
                    }

                    rs.close();
                    return Arrays.toString(json.toArray());
                } else {
                    return null;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return "{'error' : true, 'reason' : 'Error parsing resultset'}";
            }
        }
    }

    /**
     * Runs db duties bike ride, as well as authentication, returns resultset array.
     * @param pointsToBeAdded points to be added for the bike ride
     * @return resultset with the current points total.
     */
    private ResultSet[] runQueries(int pointsToBeAdded) {
        String[] queries = new String[4];
        queries[0] = "UPDATE points SET points = points + " + pointsToBeAdded + ", last_updated = "
                + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

        queries[1] = "INSERT INTO bikeride (username, points, distance, datetime, co2)"
                + "values ('" + username + "'," + pointsToBeAdded + ",'"
                + distance + "',CURRENT_TIMESTAMP(0), " + co2 + ")";

        queries[2] = "UPDATE points SET co2 = co2 + " + co2
                + " WHERE username = '" + username + "'";

        queries[3] = "SELECT points FROM points WHERE username = '" + username + "'";

        return Query.runQueries(queries,username,password);
    }

    private ResultSet[] runSelect() {
        String[] queries = new String[1];
        queries[0] = "SELECT points, distance, datetime FROM bikeride WHERE username = '"
                + username + "' ORDER BY datetime DESC LIMIT 20";

        return Query.runQueries(queries,username,password);
    }
}