package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query extends Adapter {

    /**
     * Send the query to the sql db to insert a new client from reg form.
     * @param username the username of the client
     * @param firstName the first name of the client
     * @param lastName the last name of the client
     * @param email the email address of the client
     * @param phone the phone number of the client (not required)
     * @param password the password of the client
     */
    public void insertClient(String username, String firstName,
                             String lastName, String email, String phone, String password) {
        System.out.println("INSERT "+ username + " $" + firstName + " $"
                + lastName + " $" + email + " $" + phone + " $" + password);
        try {
            PreparedStatement lt = conn.prepareStatement(
                    "INSERT INTO points "
                            + "(username, points) "
                            + "VALUES(?,?)");
            lt.setString(1, username);
            lt.setInt(2,0);
            lt.executeUpdate();
            lt.close();

            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO client "
                            + "(username, first_name, last_name, email, phone, password) "
                            + "VALUES(?,?,?,?,?,?)");

            st.setString(1, username);
            st.setString(2, firstName);
            st.setString(3, lastName);
            st.setString(4, email);
            st.setString(5, phone);
            st.setString(6, password);
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute any given query.
     * @param query The query given as string to be executed
     */
    public void query(String query) {
        System.out.println(query);

        try {
            PreparedStatement st = conn.prepareStatement(query);

            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that checks if the given email already exists in the database
     * @param username the email that you want to check
     * @return true is it exists in the database
     */
    public boolean checkExistence(String username) {
        try {
            PreparedStatement st = conn.prepareStatement(
                    "SELECT username FROM client WHERE username = '" + username + "'");

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String res = rs.getString(1);

                if (res.equals(username)) {
                    return true;
                }
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}