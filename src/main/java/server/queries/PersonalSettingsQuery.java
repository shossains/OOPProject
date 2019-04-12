package server.queries;

import server.db.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalSettingsQuery extends ServerQuery {
    public String email;
    public Double co2;


    /**
     * Connects to the database and executes the query to fetch email and co2.
     * @return json-format string of the email and amount of co2
     */
    public String runQuery() {
        String[] queries = new String[2];
        queries[0] = "SELECT email FROM client WHERE username = '" + username + "'";
        queries[1] = "SELECT co2 FROM points WHERE username = '" + username + "'";

        ResultSet[] rsArray = Query.runQueries(queries,username,password);
        ResultSet rs = rsArray[0];
        ResultSet rs2 = rsArray[1];

        try {
            while (rs.next()) {
                email = rs.getString(1);
            }
            while (rs2.next()) {
                co2 = rs2.getDouble(1);
                co2 = Math.round(co2* 100.0) / 100.0;
            }

            rs.close();
            rs2.close();
            return "{'email' : '" + email + "', 'co2' : " + co2 + "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "{'error' : true, 'reason' : 'Error parsing resultset'}";
        }
    }
}
