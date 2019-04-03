package application;

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
     * When clicking the button, a new friend is added to the Friends menu
     * To test how to add new menu items that have a method
     */
    @FXML public void addFriend(){
        MenuItem newFriend = new MenuItem("new Friend");

        friendMenu.getItems().add(newFriend);
        newFriend.setOnAction(e -> generate("Local produce", (int)(Math.random() * 100),
                "Temperature", (int)(Math.random() * 100)));

    }

    /**
     * Test data for addfriend() method
     * @param name
     * @param value
     * @param name2
     * @param value2
     */
    @FXML public void generate(String name, int value, String name2, int value2){
        ObservableList<PieChart.Data> friendData
                = FXCollections.observableArrayList(
                new PieChart.Data(name, value),
                new PieChart.Data(name2, value2));

        friendPieChart.setData(friendData);
    }

    /**
     * Test data to see if displaying data tied to menu item 1 works
     */
    @FXML public void showFriendData(){
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
        ObservableList<PieChart.Data> userPieChartData
                = FXCollections.observableArrayList(new PieChart.Data("VegMeal", 50),
                new PieChart.Data("Bike Ride", 120), new PieChart.Data("Temperature", 100));
        userPieChart.setData(userPieChartData);


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
     * Go to the Personal settings screen
     * @param actionEvent The click of the button
     * @throws IOException Throw if file is missing/corrupted/incomplete
     */
    public void goPersonal(ActionEvent actionEvent) throws IOException{
        go("PersonalSettings");
    }
}
