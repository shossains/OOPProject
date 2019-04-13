package application;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import client.SecureClientNetworking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToolBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonalSettingsController implements Initializable {

    @FXML ToolBar myToolbar;

    @FXML private Label oldpassword;
    @FXML private Label newpassword;
    @FXML private PasswordField oldpasswordField;
    @FXML private PasswordField newpasswordField;
    @FXML private Button button;
    @FXML private Text email;
    @FXML private Text points;
    @FXML private Text username;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        //request
        String request = "{'type' : 'Settings', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "'}";
        String response = scn.sendPostRequest(request);

        if (response != null) {
            String[] res = parseRow(response);

            email.setText(res[0].replace("\"", ""));
            points.setText(res[1]);
        }

        username.setText(User.getUsername());

    }

    /**
     * Change the users current password.
     */
    public void changepassword() {
        oldpassword.setVisible(true);
        newpassword.setVisible(true);
        oldpasswordField.setVisible(true);
        newpasswordField.setVisible(true);
        button.setVisible(true);
    }

    /**
     * Helper function that transform the json into array of variables.
     * @param responseJson the json that will be processed
     * @return A array with the values of the json
     */
    public String[] parseRow(String responseJson) {
        String[] res = new String[2];

        if (responseJson != null) {
            JsonObject json = null;
            try {
                json = new JsonParser().parse(responseJson).getAsJsonObject();
            } catch (IllegalStateException e) {
                System.out.println("Returned something that's not even JSON");
                return null;
            }
            try {
                res[0] = json.get("email").toString();
                res[1] = json.get("co2").toString();

            } catch (NumberFormatException e) {
                System.out.println(responseJson);
                System.out.println("Bad json format returned");
            }
            return res;
        } else {
            System.out.println("Null JSON returned");
            return null;
        }
    }

    /**
     * The general go method which will switch to a specific scene.
     * @param fileName The name of the file where we want to go
     * @throws IOException TThrow if file is missing/corrupted/incomplete
     */
    public void go(String fileName) throws IOException {
        Parent hmParent = FXMLLoader.load(getClass().getResource("/fxml/" + fileName + ".fxml"));
        Scene hmScene = new Scene(hmParent);

        Stage window = (Stage) myToolbar.getScene().getWindow();
        window.setScene(hmScene);
        window.show();
    }

    /**
     * Go to the Vegetarian meal screen.
     * @param actionEvent The click of the button
     * @throws IOException Throw if file is missing/corrupted/incomplete
     */
    public void goVeg(ActionEvent actionEvent) throws IOException {
        go("VegMeal");
    }

    /**
     * Go to the Local Produce screen.
     * @param actionEvent The click of the button
     * @throws IOException Throw if file is missing/corrupted/incomplete
     */
    public void goLocal(ActionEvent actionEvent) throws IOException {
        go("LocalProduce");
    }

    /**
     * Go to the Bike screen.
     * @param actionEvent The click of the button
     * @throws IOException Throw if file is missing/corrupted/incomplete
     */
    public void goBike(ActionEvent actionEvent) throws IOException {
        go("BikeRide");
    }

    /**
     * Go to the Public transport screen.
     * @param actionEvent The click of the button
     * @throws IOException Throw if file is missing/corrupted/incomplete
     */
    public void goPublic(ActionEvent actionEvent) throws IOException {
        go("PublicTransport");
    }

    /**
     * Go to the Temperature adjustment screen.
     * @param actionEvent The click of the button
     * @throws IOException Throw if file is missing/corrupted/incomplete
     */
    public void goTemp(ActionEvent actionEvent) throws IOException {
        go("Temperature");
    }

    /**
     * Go to the Solar panel screen.
     * @param actionEvent The click of the button
     * @throws IOException Throw if file is missing/corrupted/incomplete
     */
    public void goSolar(ActionEvent actionEvent) throws IOException {
        go("SolarPanels");
    }

    /**
     * Go to the Stats screen.
     * @param actionEvent The click of the button
     * @throws IOException Throw if file is missing/corrupted/incomplete
     */
    public void goStats(ActionEvent actionEvent) throws IOException {
        go("StatsPiechart");
    }

    /**
     * Go to the Achievements screen.
     * @param actionEvent The click of the button
     * @throws IOException Throws if file is missing/corrupted/incomplete
     */
    public void goAchievements(ActionEvent actionEvent) throws IOException {
        go("Achievements");
    }

}
