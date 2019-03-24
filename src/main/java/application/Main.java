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

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/HomeScreen.fxml"));
        Scene scene = new Scene(root,720,480);
        stage.setTitle("#GoGreen");
        stage.getIcons().add(new Image("/icon.png"));
        stage.setScene(scene);
        stage.show();
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