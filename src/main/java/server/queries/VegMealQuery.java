package server.queries;

public class VegMealQuery extends ServerQuery {
    private String somethingPassedByJson;

    /**
     * the query method lays the connection with the database and executes the query
     * @return json-format string of the amount of points of the username
     */
    public String runQuery(){
        server.db.Query.query("UPDATE points SET points = points + 1 WHERE username = '" + username + "'");
        somethingPassedByJson = server.db.Query.eaten(username);
        return somethingPassedByJson;
    }

}
