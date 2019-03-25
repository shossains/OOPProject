package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //setup the user class, for now just from string, in the future ideally from local storage
        setupUser();

<<<<<<< HEAD
        try {
            primaryStage = stage;
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/TEMPCompareScreen.fxml"));
            Scene scene = new Scene(root, 720, 480);
            stage.setTitle("#GoGreen");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) { // TODO: don't catch like that, andy will crucify us
            e.printStackTrace();
        }
=======
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/HomeScreen.fxml")); //Home screen
        Scene scene = new Scene(root,720,480); //Dimensions of the app
        stage.setTitle("#GoGreen"); //App title
        stage.getIcons().add(new Image("/icon.png")); //App logo
        stage.setResizable(false); //Disable maximizing the app
        stage.setScene(scene);
        stage.show();
>>>>>>> ce0005132969c49b9d5918fc91fc828625501d24
    }

    /**Main entry point for the application. Currently launches both server and client for demos.
     * @param args system args
     */
    public static void main(String[] args) {

        //setup server
        Server server;
        String serverpassword = "password";
        try {
            server = new Server(3000, new FileInputStream("testkey.jks"),
                    serverpassword.toCharArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        launch(args);
    }

    private void setupUser() {
        if (User.setServerUrl("https://localhost:3000")) {
            System.out.println("URL valid");
        } else {
            System.out.println("URL invalid");
        }
        User.setUsername("shossain");
        User.setPassword("test123");
    }
}