package server.queries;

import server.db.Query;

public class LoginQuery extends ServerQuery {

    public String runQuery(){
        //construct query
        String[] queries = new String[0];

        if(Query.runQueries(queries, username, password) == null){
            return "{'login' : false}";
        }

        return "{'login' : true}";
    }
}
