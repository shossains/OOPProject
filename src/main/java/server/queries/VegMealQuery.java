package server.queries;

import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VegMealQuery extends ServerQuery {
    private boolean addMeal;

    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     *
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        if (addMeal) {
            String[] queries = new String[3];
            queries[0] = "UPDATE points SET points = points + 50, last_updated = "
                    + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

            queries[1] = "INSERT INTO log (username, type, points, dateTime) values"
                    + " ('" + username + "','vegMeal',000,CURRENT_TIMESTAMP(0))";

            queries[2] = "SELECT points FROM points WHERE username = '" + username + "'";

            //should be one function
            ResultSet[] rsArray = Query.query(queries);
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

        } else {
            //TODO
            return null;
        }
    }
}