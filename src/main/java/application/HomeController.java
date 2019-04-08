package application;

import client.SecureClientNetworking;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        String request = "{'type' : 'Login', 'username' : '"+username+"', 'password' : '"+password+"'}";
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        if(scn.sendPostRequest(request).equals("{'login' : false}")){
            //invalid login
            System.out.println("Invalid login");
            //do an error message

            return;
        }

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
