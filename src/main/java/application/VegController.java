package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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

        GridPane grid = new GridPane();
        Label test = new Label("testerdietets");
        grid.add(test,0,0) ;


//        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));
//        Scene tableViewScene = new Scene(tableViewParent);
//
//        //This line gets the Stage information
//        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//
//        window.setScene(tableViewScene);
//        window.show();

    }
}
