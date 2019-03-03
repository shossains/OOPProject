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

import java.io.IOException;

public class RegController {
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField phone;
    public PasswordField pass;
    public Label status;
    public Label invalidFirstName;
    public Label invalidLastName;
    public Label invalidEmail;
    public Label invalidPhone;
    public Label invalidPass;
    public String statusText;

    /**
     * Send the request to register to the db after clicking the button and go to veg meal page.
     * @param actionEvent The click of the button
     */
    public void button(ActionEvent actionEvent) throws IOException {
        if (proceed()) {
//            Query db = new Query();
//            db.connect();
//            db.insertClient(firstName.getText(), lastName.getText(),
//                    email.getText(), phone.getText(), pass.getText());
//            Main.statusText = "Registration Success!";
//            status.setText(Main.statusText);
//            db.disconnect();
//
            Parent tableViewParent = FXMLLoader.load(
                    getClass().getResource("/fxml/VegMealV1.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }

        else {
            status.setText(statusText);
            status.setStyle("-fx-text-fill: #a12020;");
        }
    }

    /**
     * Check whether input is an integer.
     * @param input the input that needs to be checked
     * @return True or false
     */
    public boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean proceed() {
        if (!emptyFirstName() && !emptyLastName() && !emptyEmail() &&
                !invalidPhone() && !checkAccount() && !emptyPass()) {
            return true;
        }

        else {
            return false;
        }
    }

    public boolean emptyFirstName() {
        if (firstName.getText().equals("")) {
            invalidFirstName.setText("First name can't be empty");
            return true;
        }

        else {
            invalidFirstName.setText("");
            return false;
        }
    }

    public boolean emptyLastName() {
        if (lastName.getText().equals("")) {
            invalidLastName.setText("Last name can't be empty");
            return true;
        }

        else {
            invalidLastName.setText("");
            return false;
        }
    }

    public boolean emptyEmail() {
        if (email.getText().equals("")) {
            invalidEmail.setText("Email can't be empty");
            return true;
        }

        else {
            invalidEmail.setText("");
            return false;
        }
    }

    public boolean invalidPhone(){
        if (phone.getText().equals("")) {
            invalidPhone.setText("Phone can't be empty");
            return true;

        }

        if (!isInt(phone.getText())) {
            invalidPhone.setText("Invalid phone number!");
            return true;
        }

        else {
            invalidPhone.setText("");
            return false;
        }
    }

    public boolean emptyPass(){
        if (pass.getText().equals("")) {
            invalidPass.setText("Email can't be empty");
            return true;
        }

        else {
            invalidPass.setText("");
            return false;
        }
    }

    public boolean checkAccount() {
        Query db = new Query();
        db.connect();

        if (db.checkExistence(email.getText())) {
            statusText = "Account already exists";
            db.disconnect();
            return true;
        }

        else {
            status.setText("");
            db.disconnect();
            return false;
        }
    }
}
