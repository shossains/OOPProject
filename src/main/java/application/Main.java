package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public Stage primaryStage;

    @Override
    public void start(Stage stage) {
        try {
            primaryStage = stage;
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/HomeScreen.fxml"));
            Scene scene = new Scene(root,720,480);
            stage.setTitle("#GoGreen");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
