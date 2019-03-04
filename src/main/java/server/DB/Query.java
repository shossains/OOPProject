package server.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query extends Adapter {

    /**
     * Send the query to the sql db to insert a new client from reg form.
     * @param firstName the first name of the client
     * @param lastName the last name of the client
     * @param email the email address of the client
     * @param phone the phone number of the client (not required)
     * @param password the password of the client
     */
    public void insertClient(String firstName,
                             String lastName, String email, String phone, String password) {
        String newID = newId();
        System.out.println("INSERT " + newID + " $" + firstName + " $"
                + lastName + " $" + email + " $" + phone + " $" + password);
        try {
            PreparedStatement lt = conn.prepareStatement(
                    "INSERT INTO points "
                            + "(id, points) "
                            + "VALUES(?,?)");
            lt.setString(1, newID);
            lt.setInt(2,0);
            lt.executeUpdate();
            lt.close();

            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO client "
                            + "(id, first_name, last_name, email, phone, password) "
                            + "VALUES(?,?,?,?,?,?)");

            st.setString(1, newID);
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
    } // insertClient

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
     * Checks whether the id already exists in the database.
     * @param id id that needs to be checked for ambiguity
     * @return
     */
    public void selectId(String id) {
        System.out.println("SELECT id FROM client WHERE id = '" + id + "'");
        try {
            PreparedStatement st = conn.prepareStatement(
                    "SELECT id FROM client WHERE id = '" + id + "'");

            //System.out.println("SQL = " + st.toString());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String res = rs.getString(1);
                System.out.println(res);

                if (res.equals(id)) {
                    newId();
                }
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates new random number and checks immediately for ambiguity.
     * @return the unique number
     */
    public String newId() {
        int random = (int)(Math.random() * 999 + 1);
        String check = random + "";
        selectId(check);

        return random + "";
    }
}