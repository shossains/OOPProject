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

    /**
     * Searches for a meal that matches the input.
     * @param actionEvent The click of the button
     * @throws IOException Throw exception
     */
    public void add(ActionEvent actionEvent) throws IOException {
        GridPane grid = new GridPane();
        Label test = new Label("testerdietets");
        grid.add(test,0,0) ;


        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/RegisterForm.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

    }
}
