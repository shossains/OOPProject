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
    public Label invalidPhone;

    /**
     * Send the request to register to the db after clicking the button and go to veg meal page.
     * @param actionEvent The click of the button
     */
    public void button(ActionEvent actionEvent) throws IOException {
        if (proceed() == 1) {
            Query db = new Query();
            db.connect();

            db.insertClient(firstName.getText(), lastName.getText(),
                    email.getText(), phone.getText(), pass.getText());
            Main.statusText = "Registration Success!";
            status.setText(Main.statusText);

            db.disconnect();

            Parent tableViewParent = FXMLLoader.load(
                    getClass().getResource("/fxml/VegMealV1.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }

        else {
            status.setText(Main.statusText);
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

    /**
     * Check all input validation at once and check if account already exist.
     * @return return -1 is one of the constraints are not met, else return 1
     */
    public int proceed() {
        Query db = new Query();
        db.connect();

        if (db.checkExistence(email.getText())) {
            Main.statusText = "Account already exists";
            db.disconnect();
            return -1;
        }

        if (!isInt(phone.getText())) {
            db.disconnect();
            invalidPhone.setText("Invalid phone number!");
            Main.statusText = "Registration failed!";
            return -1;
        }

        else {
            db.disconnect();
            return 1;
        }
    }
}
