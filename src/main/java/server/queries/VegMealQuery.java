package server.queries;

public class VegMealQuery extends ServerQuery {
    private boolean addMeal;

    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        if (addMeal) {
            server.db.Query.query("UPDATE points SET points = points + 50, last_updated = "
                    + "CURRENT_TIMESTAMP(0) WHERE username = '" + username + "'");

            server.db.Query.query("INSERT INTO log (username, type, points, dateTime) values"
                    + " ('" + username + "','vegMeal',150,CURRENT_TIMESTAMP(0))");
            return server.db.Query.eaten(username);
        }

        else {
            //TODO
            return null;
        }
    }
}