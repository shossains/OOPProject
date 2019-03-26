package application;

import client.SecureClientNetworking;
import com.google.gson.JsonElement;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import org.json.*;

import java.io.IOException;
import application.VegController;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StatsController implements Initializable {

    @FXML ToolBar myToolbar;

    @FXML private PieChart userPieChart;
    @FXML private PieChart friendPieChart;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //VegController vegController = new VegController();
        //LocalProduceController localProduceController = new LocalProduceController();
        //BikeController bikeController = new BikeController();
        //int VegMealValue = vegController.returnPoints();
        //int LocProdValue = .returnPoints();
        //int BikeValue = bikeController.returnPoints();
        request();
        ObservableList<PieChart.Data> userPieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("VegMeal", 20),
                        new PieChart.Data("Bike Ride", 120),
                        new PieChart.Data("Temperature", 80),
                        new PieChart.Data("SolarPanels", 50),
                        new PieChart.Data("LocalProduce", 25),
                        new PieChart.Data("Public Transport", 10));
        userPieChart.setData(userPieChartData);

        ObservableList<PieChart.Data> friendPieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("VegMeal", 60),
                        new PieChart.Data("Bike Ride", 120),
                        new PieChart.Data("Temperature", 80),
                        new PieChart.Data("SolarPanels", 50),
                        new PieChart.Data("LocalProduce", 25),
                        new PieChart.Data("Public Transport", 10));
        friendPieChart.setData(friendPieChartData);
    }

    public int[] request() {
        int[] ints = new int[10]; //size = amount of categories TODO: change size to actually do that
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        String request = "{'type' : 'Combined', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "'}";

        String response = scn.sendPostRequest(request);

        System.out.println(parseVegPoints(response));
        ints[0] = parseVegPoints(response);
        return ints;
    }


    public JsonObject parseJson(String responseJson) {
        if (responseJson != null) {
            //de-Json the response and update the amount of points.
            //JsonElement jsonElement = new JsonParser().parse(responseJson);
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

}
