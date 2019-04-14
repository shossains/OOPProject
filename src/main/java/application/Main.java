package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.Server;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //setup the user class, for now just from string, in the future ideally from local storage
        setupUser();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/HomeScreen.fxml"));
        Scene scene = new Scene(root,720,480); //Dimensions of the app
        stage.setScene(scene);
        stage.setTitle("#GoGreen"); //App title
        stage.getIcons().add(new Image("/icon.png")); //App logo
        stage.setResizable(false); //Disable maximizing the app
        stage.show();
    }

    /**Main entry point for the application. Currently launches both server and client for demos.
     * @param args system args
     */
    public static void main(String[] args) {

        //setup server
        Server server;
        String serverpassword = "password";
        server = new Server(3000, Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("testkey.jks"),
                serverpassword.toCharArray());


        launch(args);
    }

    private void setupUser() {
        if (User.setServerUrl("https://localhost:3000")) {
            System.out.println("URL valid");
        } else {
            System.out.println("URL invalid");
        }
    }
}