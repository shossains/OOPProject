package server.queries;

import server.db.Query;

public class VegMealQuery extends ServerQuery {
    private boolean addMeal;

    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        if (addMeal) {
            String[] queries = new String[2];
            queries[0] = "UPDATE points SET points = points + 50, last_updated = "
                    + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'";

            queries[1] = "INSERT INTO log (username, type, points, dateTime) values"
                    + " ('" + username + "','vegMeal',000,CURRENT_TIMESTAMP(0))";

            //should be one function
            Query.query(queries);

            return server.db.Query.eaten(username);
        } else {
            //TODO
            return null;
        }
    }
}