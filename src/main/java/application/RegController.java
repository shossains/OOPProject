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

    /**
     * Send the request to register to the db after clicking the button and go to veg meal page.
     * @param actionEvent The click of the button
     */
    public void button(ActionEvent actionEvent) throws IOException {
        if (isInt(phone.getText())) {

//        Query db = new Query();
//        db.connect();
//
//        db.insertClient(firstName.getText(), lastName.getText(), email.getText(), phone.getText(), pass.getText());
//        Main.statusText = "Registration Success!";
//        status.setText(Main.statusText);
//
//        db.disconnect();

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/VegMealV1.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
        else {
            System.out.println("Please fix errors above");
        }
    }

    public boolean isInt(String input){
        try {
            int check = Integer.parseInt(input);
            return true;
        }
        catch(NumberFormatException e) {
            System.out.println(input + " is not a number");
            return false;
        }
    }
}
