package application;

import client.SecureClientNetworking;
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
import java.net.URL;

public class VegController {
    public TextField insert;

    /**
     * Searches for a meal that matches the input.
     * @param actionEvent The click of the button
     * @throws IOException Throw exception
     */
    public void add(ActionEvent actionEvent) throws IOException {
        //send json request
        SecureClientNetworking scn = new SecureClientNetworking(new URL("https://localhost:3000"));
        String request = "put json here";
        String response = scn.sendPostRequest(request);
        //de-Json the response and update the amount of points.

        System.out.println(response);
    }
}
