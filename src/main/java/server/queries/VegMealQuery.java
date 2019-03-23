package server.queries;

import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VegMealQuery extends ServerQuery {
    private boolean addMeal;
    private String mealType;

    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * TODO: Cleanup, add helper functions to make it more readable.
     * TODO: Figure out how new points can be queried in log
     *
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        int addPoints;
        if (mealType == null) {
            return "{'error' : true, 'reason' : 'mealType not given'}";
        }

        if (mealType.equals("vegan")) {
            addPoints = 60;
        } else {
            addPoints = 50;
        }

        if (addMeal) {
            //run queries
            ResultSet rs = runQueries(addPoints)[0];

            if (rs == null) {
                return "{'error' : true, 'reason' : 'Could not find user for given credentials'}";
            }

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
            return null;
        }
    }

    /**
     * Runs db duties for the meal, as well as authentication, returns resultset array.
     *
     * @param pointsToBeAdded points to be added for the meal
     * @return resultset with the current points total.
     */
    private ResultSet[] runQueries(int pointsToBeAdded) {
        String[] queries = new String[3];
        queries[0] = "UPDATE points SET points = points + " + pointsToBeAdded + ", last_updated = "
                + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

        queries[1] = "INSERT INTO vegetarian (username, points, type, datetime) values"
                + " ('" + username + "',000,'" + mealType + "',CURRENT_TIMESTAMP(0))";

        queries[2] = "SELECT points FROM points WHERE username = '" + username + "'";

        //should be one function
        return Query.runQueries(queries, username, password);
    }
}