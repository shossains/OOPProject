package server.queries;

public class VegMealQuery extends ServerQuery {
    private String addMeal;

    /**
     * the query method lays the connection with the database and executes the query
     * @return json-format string of the amount of points of the username
     */
    public String runQuery(){
        username = "shossain";
        server.db.Query.query("UPDATE points SET points = points + 1 WHERE username = '" + username + "'");
        return server.db.Query.eaten(username);
    }

}
