package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class HomeController {

    public TextField usernameField;
    public PasswordField passwordField;

    /**
     * This method brings you to the vegetarian meal screen.
     * @param event The mouseclick
     * @throws IOException Throw exception if file is not found or corrupted
     */
    public void toMainMenu(ActionEvent event) throws IOException {
        Parent hmParent = FXMLLoader.load(getClass().getResource("/fxml/StatsPiechart.fxml"));
        Scene hmScene = new Scene(hmParent);

        //get passwords
        String username = usernameField.getText();
        String password = passwordField.getText();

        //prepare request
        String request = "";

        //set user vars
        User.setUsername(username);
        User.setPassword(password);

        //go to first page
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(hmScene);
        window.show();
    }

    /**
     * This method brings you to the register form screen.
     * @param event The mouseclick
     * @throws IOException Throw exception if file is not found or corrupted
     */
    public void toRegister(ActionEvent event) throws IOException {
        Parent hmParent = FXMLLoader.load(getClass().getResource("/fxml/RegisterForm.fxml"));
        Scene hmScene = new Scene(hmParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(hmScene);
        window.show();
    }

}
