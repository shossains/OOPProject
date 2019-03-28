package server.queries;

import calculator.LocalProduceCalculator;
import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocalProduceQuery extends ServerQuery {
    private Boolean addLocal;
    private int weight;
    private int addPoints;
    private Double co2;

    /**
     * Connects to the database and executes the query to add a local produce.
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        co2 = LocalProduceCalculator.produce(weight);
        Double addCo2 = LocalProduceCalculator.produce(weight) * 100;
        addPoints = addCo2.intValue();

        if (addLocal) {
            ResultSet[] rsArray = runQueries(addPoints);
            ResultSet rs = rsArray[0];

            try {
                while (rs.next()) {
                    int res = rs.getInt(1);
                    rs.close();

                    return "{'points' : " + res + " , 'added' : "
                            + addPoints + " , 'co2' : " + co2 + "}";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "{'error' : true, 'reason' : 'Error parsing resultset'}";
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * Runs db duties for the local produce, returns resultset array.
     * @param pointsToBeAdded points to be added for the local produce
     * @return resultset with the current points total.
     */
    private ResultSet[] runQueries(int pointsToBeAdded) {
        String[] queries = new String[4];
        queries[0] = "UPDATE points SET points = points + " + pointsToBeAdded + ", last_updated = "
                + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

        queries[1] = "UPDATE points SET co2 = co2 + " + co2
                + " WHERE username = '" + username + "'";

        queries[2] = "SELECT points FROM points WHERE username = '"
                + username + "'";

        queries[3] = "INSERT INTO localproduce (username, points, weight, datetime, co2)"
                + "values ('" + username + "'," + pointsToBeAdded + ",'"
                + weight + "',CURRENT_TIMESTAMP(0), " + co2 + ")";

        return Query.runQueries(queries,username,password);
    }
}