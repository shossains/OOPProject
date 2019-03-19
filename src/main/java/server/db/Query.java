package server.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Query extends Adapter {

    /**
     * Execute any given query.
     *
     * @param query The queries given as string to be executed
     * @return a ResultSet array of SELECT query results, in order corresponding to that
     *         of the query array order.
     *         I.E.: the first select query appears in the return array first, the second second.
     *         Returns an empty array if no SELECT queries were specified.
     */
    public static ResultSet[] query(String query[]) {
        Query db = new Query();
        db.connect();
        try {
            Statement stmnt = conn.createStatement();
            conn.setAutoCommit(false);
            ArrayList<ResultSet> resultSets = new ArrayList<>();

            for (int i = 0; i < query.length; i++) {
                if (query[i].contains("SELECT")) {
                    //select query, add resultSet to array
                    try {
                        PreparedStatement select = conn.prepareStatement(query[i]);
                        ResultSet resultSet = select.executeQuery();
                        resultSets.add(resultSet);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Error executing SELECT query");
                    }

                } else {
                    //add to batch
                    try {
                        stmnt.addBatch(query[i]);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            conn.commit();
            db.disconnect();
            return resultSets.toArray(new ResultSet[resultSets.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}