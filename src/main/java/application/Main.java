package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public Stage primaryStage;

    @Override
    public void start(Stage x) {
        try {
            primaryStage = x;
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));
            Scene scene = new Scene(root,400,400);
//            scene.getStylesheets().add("/application.css");
            x.setTitle("#GoGreen");
            x.setScene(scene);
            x.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes you to the Vegetarian Meal Scene after clicking the Register button.
     * @param vmevent: Clicking the Register button
     */
    public void toVegMeal(ActionEvent vmevent) throws IOException {
        Parent vmParent = FXMLLoader.load(getClass().getResource("/fxml/VegMealV1.fxml"));
        Scene vmScene = new Scene(vmParent);
        primaryStage.setScene(vmScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
