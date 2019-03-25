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

    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * TODO: Connect with calc to determine points
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        int addPoints = 0;

        if (vehicle.equals("bus")) {
            addPoints = BusCalculator.bus(distance);
        } else if (vehicle.equals("train")) {
            try {
                addPoints = TrainCalculator.train(distance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (addPublic) {
            String[] queries = new String[2];
            queries[0] = "UPDATE points SET points = points + " + addPoints + ", last_updated = "
                    + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

            queries[1] = "SELECT points FROM points WHERE username = '" + username + "'";

            ResultSet[] rsArray = Query.runQueries(queries);
            ResultSet rs = rsArray[0];

            try {
                while (rs.next()) {
                    int res = rs.getInt(1);
                    rs.close();

                    String[] updateQuery = new String[1];
                    updateQuery[0] = "INSERT INTO publictransport"
                            + "(username, points, vehicletype, distance, datetime) values"
                            + " ('" + username + "', " + res + ", '" + vehicle
                            + "', '" + distance + "', CURRENT_TIMESTAMP(0))";
                    Query.runQueries(updateQuery);

                    return "{'points' : " + res + ", 'added' : " + addPoints + "}";
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return "{'error' : true, 'reason' : 'Error parsing resultset'}";
            }
        } else if (!addPublic) {
            String[] newquery = new String[1];
            newquery[0] = "SELECT count(*) FROM publictransport WHERE username = '"
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