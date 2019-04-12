package server.queries;

import server.db.Query;

public class LoginQuery extends ServerQuery {

    /**Checks if user/pass combination is correct.
     * @return login true for correct combination, false if incorrect.
     */
    public String runQuery() {
        //construct query
        String[] queries = new String[0];

        if (Query.runQueries(queries, username, password) == null) {
            return "{'login' : false}";
        }

        return "{'login' : true}";
    }
}
