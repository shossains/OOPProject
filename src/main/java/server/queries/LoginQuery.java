package server.queries;

import server.db.Query;

public class LoginQuery extends ServerQuery {
    private String username;
    private String password;

    public String runQuery(){
        //construct query
        String[] queries = new String[0];

        if(Query.runQueries(queries, username, password) == null){
            return "{'error' : true, 'reason' : 'wrong user credentials'}";
        }

        return "{'login' : true}";
    }
}
