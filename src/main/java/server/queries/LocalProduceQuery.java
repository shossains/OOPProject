package server.queries;

import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocalProduceQuery extends ServerQuery {
    private Boolean addLocal;
    private int weight;

    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * TODO: Cleanup, add helper functions to make it more readable.
     * TODO: Figure out how new points can be queried in log
     * TODO: Connect with calc to determine points
     *
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        int addPoints = 0;

        if (addLocal) {
            String[] queries = new String[3];
            queries[0] = "UPDATE points SET points = points + " + addPoints + ", last_updated = "
                    + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

            queries[1] = "INSERT INTO localproduce (username, points, weight, datetime) values"
                    + " ('" + username + "',000,'" + weight + "',CURRENT_TIMESTAMP(0))";

            queries[2] = "SELECT points FROM points WHERE username = '" + username + "'";

            //should be one function
            ResultSet[] rsArray = Query.runQueries(queries);
            ResultSet rs = rsArray[0];

            try {
                while (rs.next()) {
                    int res = rs.getInt(1);
                    rs.close();
                    return "{\"points\" : " + res + "}";
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return "{'error' : true, 'reason' : 'Error parsing resultset'}";
            }
        } else if (!addLocal) {
            String[] newquery = new String[1];
            newquery[0] = "SELECT count(*) FROM localproduce WHERE username = '" + username + "'";

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