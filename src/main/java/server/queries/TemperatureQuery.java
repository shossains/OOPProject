package server.queries;

import server.db.Query;
import calculator.TemperatureCalculator;

public class TemperatureQuery extends ServerQuery {
    private boolean addTemp;
    private int temp;

    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        if (addTemp) {
            try {
                double CO2 = TemperatureCalculator.temp(temp) *100;
            } catch (Exception e) {
                e.printStackTrace();
            }

            String[] queries = new String[2];
            queries[0] = "UPDATE points SET points = points + CO2, last_updated = "
                    + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

            queries[1] = "INSERT INTO log (username, type, points, dateTime) values"
                    + " ('" + username + "','temperature',000,CURRENT_TIMESTAMP(0))";

            //should be one function
            Query.query(queries);

            return server.db.Query.eaten(username);
        } else {
            //TODO
            return null;
        }
    }
}