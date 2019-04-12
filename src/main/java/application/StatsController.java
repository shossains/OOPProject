package application;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import client.SecureClientNetworking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatsController implements Initializable {

    @FXML ToolBar myToolbar;

    @FXML private PieChart userPieChart;
    @FXML private PieChart friendPieChart;
    @FXML private MenuButton friendMenu;


    /**
     * When clicking the button, a new friend is added to the Friends menu.
     * To test how to add new menu items that have a method.
     */
    @FXML public void addFriend() {
        MenuItem newFriend = new MenuItem("new Friend");

        friendMenu.getItems().add(newFriend);
        newFriend.setOnAction(e -> generate("Local produce", (int)(Math.random() * 100),
                "Temperature", (int)(Math.random() * 100)));

    }

    /**
     * Test data for addfriend() method.
     * @param name Name of the friend
     * @param value The value fot the pie chart
     * @param name2 Name of the second friend
     * @param value2 The value of the second friend
     */
    @FXML public void generate(String name, int value, String name2, int value2) {
        ObservableList<PieChart.Data> friendData
                = FXCollections.observableArrayList(
                new PieChart.Data(name, value),
                new PieChart.Data(name2, value2));

        friendPieChart.setData(friendData);
    }

    /**
     * Test data to see if displaying data tied to menu item 1 works.
     */
    @FXML public void showFriendData() {
        ObservableList<PieChart.Data> friendPieChartData
                = FXCollections.observableArrayList(
                new PieChart.Data("VegMeal", 120),
                new PieChart.Data("Bike Ride", 120),
                new PieChart.Data("Temperature", 80),
                new PieChart.Data("SolarPanels", 50),
                new PieChart.Data("LocalProduce", 25),
                new PieChart.Data("Public Transport", 0)
        );
        friendPieChart.setData(friendPieChartData);
    }

    @Override
      public void initialize(URL url, ResourceBundle rb) {
        int[] allPoints = request();
        int vegMealValue = allPoints[0];
        int locProdValue = allPoints[1];
        int bikeValue = allPoints[2];
        int pubTransValue = allPoints[3];
        int tempValue = allPoints[4];
        int solarValue = allPoints[5];

        int[] average = averageRequest();

        ObservableList<PieChart.Data> userPieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("VegMeal", vegMealValue),
                        new PieChart.Data("LocalProduce", locProdValue),
                        new PieChart.Data("Bike Ride", bikeValue),
                        new PieChart.Data("Public Transport", pubTransValue),
                        new PieChart.Data("Temperature", tempValue),
                        new PieChart.Data("SolarPanels", solarValue));
        userPieChart.setData(userPieChartData);

        ObservableList<PieChart.Data> friendPieChartData
              = FXCollections.observableArrayList(
              new PieChart.Data("VegMeal", average[0]),
              new PieChart.Data("LocalProduce", average[1]),
              new PieChart.Data("Bike Ride", average[2]),
              new PieChart.Data("Public Transport", average[3]),
              new PieChart.Data("Temperature", average[4]),
              new PieChart.Data("SolarPanels", average[5]));
        friendPieChart.setData(friendPieChartData);
    }

    /**
     * returns an integer array of the user's points per category.
     */
    public int[] request() {
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        String request = "{'type' : 'Combined', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "'}";

        String response = scn.sendPostRequest(request);

        int[] ints = new int[6];
        ints[0] = parseVegPoints(response);
        ints[1] = parseLocProdPoints(response);
        ints[2] = parseBikePoints(response);
        ints[3] = parsePubTransPoints(response);
        ints[4] = parseTempPoints(response);
        ints[5] = parseSolarPoints(response);

        return ints;
    }

    /**
     * returns an integer array of the global average amount of points.
     */
    public int[] averageRequest() {
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        String request = "{'type' : 'Average'}";

        String response = scn.sendPostRequest(request);

        int users = parseUsers(response);
        int[] ints = new int[6];
        ints[0] = parseVegPoints(response);
        ints[1] = parseLocProdPoints(response);
        ints[2] = parseBikePoints(response);
        ints[3] = parsePubTransPoints(response);
        ints[4] = parseTempPoints(response);
        ints[5] = parseSolarPoints(response);

        for (int i = 0; i < ints.length; i++) {
            ints[i] = ints[i] / users;
        }

        return ints;
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
    * Helper function to parse the response json.
    * @param responseJson The raw json response from the server
    * @return The current amount of points earned by eating vegetarian meals.
    */
    public int parseVegPoints(String responseJson) {
        JsonObject json = parseJson(responseJson);
        int vegPoints = -1;
        try {
            vegPoints = Integer.parseInt(json.get("vegPoints").toString());
        } catch (NumberFormatException e) {
            System.out.println(responseJson);
            System.out.println("Bad json format returned");
        }
        return vegPoints;
    }

    /**
     * Helper function to parse the response json.
     * @param responseJson The raw json response from the server
     * @return The current amount of points earned by buying local produce.
     */
    public int parseLocProdPoints(String responseJson) {
        JsonObject json = parseJson(responseJson);
        int locProdPoints = -1;
        try {
            locProdPoints = Integer.parseInt(json.get("locProdPoints").toString());
        } catch (NumberFormatException e) {
            System.out.println(responseJson);
            System.out.println("Bad json format returned");
        }
        return locProdPoints;
    }

    /**
     * Helper function to parse the response json.
     * @param responseJson The raw json response from the server
     * @return The current amount of points earned by biking.
     */
    public int parseBikePoints(String responseJson) {
        JsonObject json = parseJson(responseJson);
        int bikePoints = -1;
        try {
            bikePoints = Integer.parseInt(json.get("bikePoints").toString());
        } catch (NumberFormatException e) {
            System.out.println(responseJson);
            System.out.println("Bad json format returned");
        }
        return bikePoints;
    }

    /**
     * Helper function to parse the response json.
     * @param responseJson The raw json response from the server
     * @return The current amount of points earned by using public transport.
     */
    public int parsePubTransPoints(String responseJson) {
        JsonObject json = parseJson(responseJson);
        int pubTransPoints = -1;
        try {
            pubTransPoints = Integer.parseInt(json.get("pubTransPoints").toString());
        } catch (NumberFormatException e) {
            System.out.println(responseJson);
            System.out.println("Bad json format returned");
        }
        return pubTransPoints;
    }

    /**
     * Helper function to parse the response json.
     * @param responseJson The raw json response from the server
     * @return The current amount of points earned by lowering the temperature.
     */
    public int parseTempPoints(String responseJson) {
        JsonObject json = parseJson(responseJson);
        int tempPoints = -1;
        try {
            tempPoints = Integer.parseInt(json.get("tempPoints").toString());
        } catch (NumberFormatException e) {
            System.out.println(responseJson);
            System.out.println("Bad json format returned");
        }
        return tempPoints;
    }

    /**
     * Helper function to parse the response json.
     * @param responseJson The raw json response from the server
     * @return The current amount of points earned by using solar power.
     */
    public int parseSolarPoints(String responseJson) {
        JsonObject json = parseJson(responseJson);
        int solarPoints = -1;
        try {
            solarPoints = Integer.parseInt(json.get("solarPoints").toString());
        } catch (NumberFormatException e) {
            System.out.println(responseJson);
            System.out.println("Bad json format returned");
        }
        return solarPoints;
    }

    /**
     * Helper function to parse the response json.
     * @param responseJson The raw json response from the server
     * @return The current amount of users.
     */
    public int parseUsers(String responseJson) {
        JsonObject json = parseJson(responseJson);
        int users = -1;
        try {
            users = Integer.parseInt(json.get("users").toString());
        } catch (NumberFormatException e) {
            System.out.println(responseJson);
            System.out.println("Bad json format returned");
        }
        return users;
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
     *Go to the Vegetarian Meal screen
     * @param actionEvent The click of the button
     * @throws IOException Throw if file is missing/corrupted/incomplete
     */
    public void goVeg(ActionEvent actionEvent) throws IOException{
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
     * Go to the Personal settings screen.
     * @param actionEvent The click of the button
     * @throws IOException Throw if file is missing/corrupted/incomplete
     */
    public void goPersonal(ActionEvent actionEvent) throws IOException {
        go("PersonalSettings");
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
