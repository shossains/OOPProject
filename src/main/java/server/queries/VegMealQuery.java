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
        Double co2;
        if (mealType == null) {
            return "{'error' : true, 'reason' : 'mealType not given'}";
        }

        if (mealType.equals("vegan")) {
            addPoints = 60;
            co2 = 1.5   ;
        } else {
            addPoints = 50;
            co2 = 1.0;
        }

        if (addMeal) {
            String[] queries = new String[4];
            queries[0] = "UPDATE points SET points = points + " + addPoints + ", last_updated = "
                    + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

            queries[1] = "UPDATE points SET co2 = co2 + " + co2 + " WHERE username = '" + username + "'";

            queries[2] = "INSERT INTO vegetarian (username, points, type, datetime, co2) values"
                    + " ('" + username + "'," + addPoints + ",'" + mealType
                    + "',CURRENT_TIMESTAMP(0), " + co2 + ")";

            queries[3] = "SELECT points FROM points WHERE username = '" + username + "'";

            //should be one function
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

        } else if (!addMeal) {
            String[] queries = new String[1];
            queries[0] = "SELECT count(*) FROM localProduce WHERE username = '" + username + "'";

            //should be one function
            ResultSet[] rsArray = Query.runQueries(queries);
            ResultSet rs = rsArray[0];

            try {
                while (rs.next()) {
                    int res = rs.getInt(1);
                    return "{\"points\" : " + res + "}";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "Error in resultset";
            }
        }
        return "{'error' : true, 'reason' : 'Error in query'}";
    }
}