package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.db.Query;

import java.io.IOException;
import java.sql.SQLException;

public class RegController {
    public TextField username;
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField phone;
    public PasswordField pass;
    public Label status;
    public Label invalidUsername;
    public Label invalidFirstName;
    public Label invalidLastName;
    public Label invalidEmail;
    public Label invalidPhone;
    public Label invalidPass;
    public String statusText = "";

    /**
     * Send the request to register to the db after clicking the button and go to veg meal page.
     * @param actionEvent The click of the button
     */
    public void button(ActionEvent actionEvent) throws IOException, SQLException {
        if (proceed()) {
            Query db = new Query();
            db.connect();
            /*db.insertClient(username.getText(), firstName.getText(), lastName.getText(),
                    email.getText(), phone.getText(), pass.getText());*/
            statusText = "To be implemented... soon ish";
            status.setText(statusText);
            db.disconnect();

            Parent tableViewParent = FXMLLoader.load(
                    getClass().getResource("/fxml/VegMeal.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else {
            status.setText(statusText);
            status.setStyle("-fx-text-fill: #a12020;");
        }
    }

    /**
     * Do all input validation at once and finally check if username is taken.
     * @return return true if all holds else return false
     */
    public boolean proceed() {
        boolean username = emptyUsername();
        boolean firstName = invalidFirstName();
        boolean lastName = invalidLastName();
        boolean email = emptyEmail();
        boolean phone = invalidPhone();
        boolean pass = emptyPass();

        if (!username && !firstName && !lastName && !email && !phone && !pass) {
            return false;
            //return !checkAccount();
        } else {
            return false;
        }
    }

    /**
     * Check whether Username textField is not empty.
     * @return true if empty
     */
    public boolean emptyUsername() {
        if (username.getText().equals("")) {
            invalidUsername.setText("Username can't be empty");
            return true;
        } else {
            invalidUsername.setText("");
            return false;
        }
    }

    /**
     * Check whether FirstName textField is not empty or contains other character than letters.
     * @return true if it does not violate constraints
     */
    public boolean invalidFirstName() {
        if (firstName.getText().equals("")) {
            invalidFirstName.setText("First name can't be empty");
            return true;
        } else {
            if (!isLetters(firstName.getText())) {
                invalidFirstName.setText("Invalid field!");
                return true;
            } else {
                invalidFirstName.setText("");
                return false;
            }
        }
    }

    /**
     * Check whether LastName textField is not empty or contains other character than letters.
     * @return true if it does not violate constraints
     */
    public boolean invalidLastName() {
        if (lastName.getText().equals("")) {
            invalidLastName.setText("Last name can't be empty");
            return true;
        } else {
            if (!isLetters(lastName.getText())) {
                invalidLastName.setText("Invalid field!");
                return true;
            } else {
                invalidLastName.setText("");
                return false;
            }
        }
    }

    /**
     * Check whether Email textField is not empty.
     * @return true if empty
     */
    public boolean emptyEmail() {
        if (email.getText().equals("")) {
            invalidEmail.setText("Email can't be empty");
            return true;
        } else {
            invalidEmail.setText("");
            return false;
        }
    }

    /**
     * Check whether Phone textField are integers only or empty.
     * @return true if empty or invalid
     */
    public boolean invalidPhone() {
        if (phone.getText().equals("")) {
            invalidPhone.setText("Phone can't be empty");
            return true;
        }

        if (!isInt(phone.getText())) {
            invalidPhone.setText("Invalid phone number!");
            return true;
        } else {
            invalidPhone.setText("");
            return false;
        }
    }

    /**
     * Check whether Password textField is not empty.
     * @return true if empty
     */
    public boolean emptyPass() {
        if (pass.getText().equals("")) {
            invalidPass.setText("Password can't be empty");
            return true;
        } else {
            invalidPass.setText("");
            return false;
        }
    }

    /**
     * Check whether username is already used in database.
     * @return true is username is already used
     * DEPRECATED - TODO: Rewrite with the new DB query system
     */
    /*public boolean checkAccount() {
        Query db = new Query();
        db.connect();

        if (db.checkExistence(username.getText())) {
            statusText = "Username is already taken";
            db.disconnect();
            return true;
        } else {
            statusText = "";
            db.disconnect();
            return false;
        }
    }*/

    /**
     * Check whether input is an integer.
     * @param input the input that needs to be checked
     * @return True or false
     */
    public static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check is input contains only letters.
     * @param string input that needs to be checked
     * @return true is input contains only letters
     */
    public static boolean isLetters(String string) {
        char[] chars = string.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Goes back to the homescreen.
     * @param actionEvent The click of the button
     * @throws IOException If the fxml is invalid or corrupted throw this
     */
    public void back(ActionEvent actionEvent) throws IOException {
        Parent tableViewParent = FXMLLoader.load(
                getClass().getResource("/fxml/HomeScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}