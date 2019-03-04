package application;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField phone;
    public PasswordField pass;
    public Label status;

    /**
     * Send the request to register to the db after clicking the button.
     * @param actionEvent The cilck of the button
     */
    public void button(ActionEvent actionEvent) {
        Query db = new Query();
        db.connect();

        db.insertClient(firstName.getText(),lastName.getText(),email.getText(),phone.getText(), pass.getText());
        Main.statusText = "Registration Success!";
        status.setText(Main.statusText);

        db.disconnect();
    }
}
