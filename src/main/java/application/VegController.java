package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class VegController {
    public TextField insert;

    public void add(ActionEvent actionEvent) throws IOException {
//        Query db = new Query();
//        db.connect();
//
//        db.insertClient(firstName.getText(),lastName.getText(),
//                email.getText(),phone.getText(), pass.getText());
//        Main.statusText = "Registration Success!";
//        status.setText(Main.statusText);
//
//        db.disconnect();

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

        System.out.println("test");
    }
}
