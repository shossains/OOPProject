package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    public void toMainMenu(ActionEvent event) throws IOException {
        Parent hmParent = FXMLLoader.load(getClass().getResource("/fxml/VegMealV1.fxml"));
        Scene hmScene = new Scene(hmParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(hmScene);
        window.show();
    }

}
