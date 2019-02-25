package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query extends Adapter {

    public void insertClient(String first_name,
                             String last_name, String email, String phone, String password) {
        String newID = newId();
        System.out.println("INSERT " + newID + " $" + first_name + " $"
                + last_name + " $" + email + " $" + phone + " $" + password);
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
            st.setString(2, first_name);
            st.setString(3, last_name);
            st.setString(4, email);
            st.setString(5, phone);
            st.setString(6, password);
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // insertClient

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

    public String selectId(String id){
        System.out.println("SELECT id FROM client WHERE id = '" + id + "'");
        try {
            PreparedStatement st = conn.prepareStatement(
                    "SELECT id FROM client WHERE id = '" + id + "'");

            //System.out.println("SQL = " + st.toString());
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                String res = rs.getString(1);
                System.out.println(res);

                if(res.equals(id)) {
                    newId();
                }
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "/nDone";
    }

    public String newId() {
        int random = (int)(Math.random() * 999 + 1);
        String check = random + "";
        selectId(check);

        return random + "";
    }
}