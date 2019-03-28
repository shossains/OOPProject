package server.queries;

import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class VegMealQuery extends ServerQuery {
    private boolean addMeal;
    private String mealType;
    private int addPoints;
    private Double co2;

    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        if (addMeal) {
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
            //run queries
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
                String[] json = new String[20];
                for (int i = 0; i < 20; i++) {
                    rs.next();
                    int points = rs.getInt(1);
                    String type = rs.getString(2);
                    String datetime = rs.getString(3);
                    json[i] = "{'points' : " + points + ",'type' : '" + type
                            + "','datetime' : '" + datetime + "'}";
                }
                rs.close();
                return Arrays.toString(json);
            } catch (SQLException e) {
                e.printStackTrace();
                return "{'error' : true, 'reason' : 'Error parsing resultset'}";
            }
        }
    }

    /**
     * Runs db duties for the meal, as well as authentication, returns resultset array.
     *
     * @param pointsToBeAdded points to be added for the meal
     * @return resultset with the current points total.
     */
    private ResultSet[] runQueries(int pointsToBeAdded) {
        String[] queries = new String[4];
        queries[0] = "UPDATE points SET points = points + " + pointsToBeAdded + ", last_updated = "
                + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

        queries[1] = "UPDATE points SET co2 = co2 + " + co2
                + " WHERE username = '" + username + "'";

        queries[2] = "INSERT INTO vegetarian (username, points, type, datetime, co2) values"
                + " ('" + username + "'," + pointsToBeAdded + ",'" + mealType
                + "',CURRENT_TIMESTAMP(0), " + co2 + ")";

        queries[3] = "SELECT points FROM points WHERE username = '" + username + "'";

        return Query.runQueries(queries,username,password);
    }

    private ResultSet[] runSelect() {
        String[] queries = new String[1];
        queries[0] = "SELECT points, type, datetime FROM vegetarian WHERE username = '"
                + username + "' ORDER BY datetime DESC LIMIT 20";

        return Query.runQueries(queries,username,password);
    }
}