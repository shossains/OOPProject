package server.queries;

import calculator.TemperatureCalculator;
import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemperatureQuery extends ServerQuery {
    private Boolean addTemp;
    private int thigh;
    private int tlow;
    private Double co2;
    private int addPoints;
    private int temp;

    /**
     * Connects to the database and executes the query to add a temperature saving.
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        temp = thigh - tlow;
        co2 = 0.0;
        addPoints = 0;

        try {
            co2 = TemperatureCalculator.temp(thigh,tlow);
            Double tempvalue = co2 * 100;
            addPoints = tempvalue.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (addTemp) {
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
            ResultSet[] rsArray = runSelect();
            ResultSet rs = rsArray[0];

            try {
                //If user had no logs in db
                if (rs.next()) {
                    //Ambiguity needed to prevent skipping first row from resultset
                    List<String> json = new ArrayList<>();
                    int firstPoint = rs.getInt(1);
                    int firstTemperature = rs.getInt(2);
                    String firstDatetime = rs.getString(3);
                    String firstRow = "{'points' : " + firstPoint + ",'temperature' : " + firstTemperature
                            + ",'datetime' : '" + firstDatetime + "'}";
                    json.add(firstRow);

                    while (rs.next()) {
                        int points = rs.getInt(1);
                        int temperature = rs.getInt(2);
                        String datetime = rs.getString(3);
                        String result = "{'points' : " + points + ",'temperature' : " + temperature
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
     * Runs db duties for the temperature, returns resultset array.
     * @param pointsToBeAdded points to be added for the temperature
     * @return resultset with the current points total.
     */
    private ResultSet[] runQueries(int pointsToBeAdded) {
        String[] queries = new String[4];
        queries[0] = "UPDATE points SET points = points + " + pointsToBeAdded + ", last_updated = "
                + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

        queries[1] = "INSERT INTO temperature "
                + "(username, points, temperature, datetime, co2) values"
                + " ('" + username + "'," + pointsToBeAdded + ",'" + temp
                + "',CURRENT_TIMESTAMP(0), " + co2 + ")";

        queries[2] = "SELECT points FROM points WHERE username = '" + username + "'";

        queries[3] = "UPDATE points SET co2 = co2 + " + co2
                + " WHERE username = '" + username + "'";

        return Query.runQueries(queries,username,password);
    }

    private ResultSet[] runSelect() {
        String[] queries = new String[1];
        queries[0] = "SELECT points, temperature, datetime FROM temperature WHERE username = '"
                + username + "' ORDER BY datetime DESC LIMIT 20";

        return Query.runQueries(queries,username,password);
    }
}