package application;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {
    public TextField first_name;
    public TextField last_name;
    public TextField email	;
    public TextField phone;
    public PasswordField pass;
    public Label status;

    public void button(ActionEvent actionEvent) {

        Query db = new Query();
        db.connect();

        db.insertClient(54,first_name.getText(),last_name.getText(),email.getText(),065, pass.getText());
        Main.statusText = "Registration Success!";
        status.setText(Main.statusText);

        db.disconnect();
    }
}
