package server.queries;

public class VegMealQuery extends ServerQuery {
    private boolean addMeal;

    /**
     * Connects to the database and executes the query to add a vegetarian meal.
     * @return json-format string of the amount of points of the username
     */
    public String runQuery() {
        if (addMeal) {
            server.db.Query.query("UPDATE points SET points = points + 1 WHERE username = '"
                    + username + "'");
            return server.db.Query.eaten(username);
        }

        else if (!addMeal) {
            server.db.Query.query("SELECT points, last_updated FROM points WHERE username = '"
                    + username + "'");
            return server.db.Query.checkPoints(username);
        }
        return null;
    }
}
