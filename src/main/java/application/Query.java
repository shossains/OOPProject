package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query extends Adapter {

    public void insertClient(int id, String first_name,
                             String last_name, String email, int phone, String password) {
        System.out.println("INSERT " + id + " $" + first_name + " $"
                + last_name + " $" + email + " $" + phone + " $" + password);
        try {
            PreparedStatement lt = conn.prepareStatement(
                    "INSERT INTO points "
                            + "(id, points) "
                            + "VALUES(?,?)");
            lt.setInt(1,id);
            lt.setInt(2,0);
            lt.executeUpdate();
            lt.close();

            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO client "
                            + "(id, first_name, last_name, email, phone, password) "
                            + "VALUES(?,?,?,?,?,?)");

            st.setInt(1, id);
            st.setString(2, first_name);
            st.setString(3, last_name);
            st.setString(4, email);
            st.setInt(5, phone);
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

    public String select(String query){
        System.out.println(query);
        try {
            PreparedStatement st = conn.prepareStatement(
                    query);

            //System.out.println("SQL = " + st.toString());
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                String res = rs.getString(1);
                System.out.println(res);
            }
            rs.close();
            st.close();
            return "\nDone!";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Nothing found!";
    }
}