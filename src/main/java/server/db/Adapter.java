package server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Adapter {
    /* 01 Database variables ------------------------------- */
    static Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    /* 02 Variables ---------------------------------------- */
    String jdbcUrl = "jdbc:postgresql://ec2-54-227-246-152.compute-1.amazonaws.com"
            + ":5432/dfetsmpdvou85t";
    String username = "ycqmkoaijmholl";
    String password = "cf35c72e380b460c7285d053492f81f4097474fced4342d73e163e228a32e2d3";

    /* 03 Constructor for DbAdapter ------------------------ */
    public Adapter() {
    }

    /**
     *  Connect to the database.
     */
    public void connect() {
        try {
            // Step 2 - Open connection
            conn = DriverManager.getConnection(jdbcUrl, username, password);

            // Print connected
            System.out.println("DbAdapter: Connection to database established");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // connect

    /**
     * Disconnect from database.
     */
    public void disconnect() throws SQLException {
        // Step 5 Close connection
        if (stmt != null) {
            stmt.close();
        }
        if (rs != null) {
            rs.close();
        }
        if (conn != null) {
            conn.close();
        }
        // Print connected
        System.out.println("DbAdapter: Connection to database closed");
    } // disconnect
}