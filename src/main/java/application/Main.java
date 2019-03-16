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
    public void start(Stage stage) {
        //setup the user class, for now just from string, in the future ideally from local storage
        setupUser();

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


    private void setupUser(){
        if(User.setServerURL("https://localhost:3000")){
            System.out.println("URL valid");
        }else{
            System.out.println("URL invalid");
        }
        User.setUsername("Andy");
        User.setPassword("hunter2");
    }
}
