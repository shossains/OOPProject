package application;

import java.sql.*;

public class Adapter {

    /* 01 Variables ---------------------------------------- */
    String jdbcUrl = "jdbc:postgresql://localhost:5432/OOPP";
    String username = "postgres";
    String password = "VjifUr7";

    /* 02 Database variables ------------------------------- */
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    /* 03 Constructor for DbAdapter ------------------------ */
    public Adapter() {
    }

    /**
     * Connect to a database
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
     * Disconnect from database
     */
    public void disconnect() {
        try {

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    } // disconnect
}