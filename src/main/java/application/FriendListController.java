package application;

import client.SecureClientNetworking;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class FriendListController implements Initializable {
    @FXML ToolBar myToolbar;
    @FXML ListView friendList;
    @FXML TextField username;

    final ObservableList<String> listItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listItems.addAll(getFriends());
        friendList.setItems(listItems);
    }

    public void addFriend() {
        String  friend = username.getText();
        if(userCheck(friend) == true) {
            //send friendreq maybe
            addFriend(friend);
            listItems.add(friend);
            friendList.setItems(listItems);
        }
    }

    public static ArrayList<String> getFriends() {
        ArrayList<String> friends = new ArrayList<>();
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        String request = "{'type' : 'Friend', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "', 'friend' : '"
                + User.getUsername() + "', 'addFriend' : false, 'getFriends' : true}";

        String response = scn.sendPostRequest(request);
        friends.addAll(parseFriends(response));
        return friends;
    }

    public boolean userCheck(String username) {
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        String request = "{'type' : 'Friend', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "', 'friend' : '"
                + username + "', 'addFriend' : false, 'getFriends' : false}";

        String response = scn.sendPostRequest(request);
        if(response.equals("1")) {
            return true;
        } return false;
    }

    public void addFriend(String friendName) {
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());


        String request = "{'type' : 'Friend', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "', 'friend' : '"
                + friendName + "', 'addFriend' : true, 'getFriends' : false}";

        scn.sendPostRequest(request);
    }

    /**
     * Helper function to parse the response json.
     * @param responseJson The raw json response from the server
     * @return A boolean that's true if the user exist and vice versa.
     */
    public boolean parse(String responseJson) {
            JsonObject json = parseJson(responseJson);
            int exists;
            try {
                exists = Integer.parseInt(json.get("res").toString());
                if (exists == 1) {
                    return true;
                } return false;
            } catch (NumberFormatException e) {
                System.out.println(responseJson);
                System.out.println("Bad json format returned");
            }
            return false;
        }

        public static ArrayList<String> parseFriends(String responseJson) {
            //JsonObject json = parseJson(responseJson);
            ArrayList<String> friends = new ArrayList<>();
            try {
                Scanner scanner = new Scanner(responseJson).useDelimiter(", ");
                while (scanner.hasNext()) {
                    friends.add(scanner.next());
                }
                //friends.add(json.get("res").toString());
                return friends;
            } catch (NumberFormatException e) {
                System.out.println(responseJson);
                System.out.println("Bad json format returned");
            }
            return friends;
        }

    /**
     * Helper function to parse the response json.
     * @param responseJson The raw json response from the server
     * @return JsonObject.
     */
    public JsonObject parseJson(String responseJson) {
        if (responseJson != null) {
            //de-Json the response.
            JsonObject json;
            try {
                json = new JsonParser().parse(responseJson).getAsJsonObject();
                return json;
            } catch (IllegalStateException e) {
                System.out.println("Returned something that's not even JSON");
                return null;
            }
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
     * Go to the Vegetarian meal screen.
     * @param actionEvent The click of the button
     * @throws IOException Throw if file is missing/corrupted/incomplete
     */
    public void goVeg(ActionEvent actionEvent) throws IOException {
        go("VegMeal");
    }

    /**
     * Go to the User Stats screen.
     * @param actionEvent The click of the button
     * @throws IOException Throw if chart is invalid
     */
    public void goStats(ActionEvent actionEvent) throws IOException {
        go("StatsPieChart");
    }

    public void goPersonal(ActionEvent actionEvent) throws IOException {
        go("PersonalSettings");
    }

    /**
     * Go to the Achievements screen
     * @param actionEvent The click of the button
     * @throws IOException Throws if file is missing/corrupted/incomplete
     */
    public void goAchievements(ActionEvent actionEvent) throws IOException{
        go("Achievements");
    }
}
