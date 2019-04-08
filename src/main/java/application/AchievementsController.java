package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AchievementsController implements Initializable {
    @FXML ToolBar myToolbar;
    @FXML Circle vegCircle0;
    @FXML Circle vegCircle1;
    @FXML Circle vegCircle2;

    boolean veg0 = false;
    boolean veg1 = false;
    boolean veg2 = false;

    boolean locProd0 = false;
    boolean locProd1 = false;
    boolean locProd2 = false;
    boolean locProd3 = false;

    StatsController statsController = new StatsController();
    int[] points = statsController.request();
    int vegPoints = points[0];
    int locProdPoints = points[1];

    int total = vegPoints + locProdPoints; //TODO

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vegAchieve0();
        vegAchieve2();
        locProdAchieve0();
        locProdAchieve1();
        locProdAchieve2();
        locProdAchieve3();
    }

    /**
     * Checks whether the first vegetarian achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void vegAchieve0() {
        if (vegPoints > 0 && veg0 == false) {
            System.out.println("Getting started"); //TODO: delete
            vegCircle0.setFill(Paint.valueOf("#ca784b"));
            veg0 = true;
        }
    }

    /**
     * Checks whether the second vegetarian achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void vegAchieve1() {
        if (veg1 == false) {
            System.out.println("Achievement 1"); //TODO: delete
            vegCircle0.setFill(Paint.valueOf("#d0d0d0"));
            veg1 = true;
        }
    }

    /**
     * Checks whether the third vegetarian achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void vegAchieve2() {
        if (vegPoints > 1000 && veg2 == false) {
            System.out.println("Earned 1000 points"); //TODO: delete
            vegCircle0.setFill(Paint.valueOf("#ecec09"));
            veg2 = true;
        }
    }

    /**
     * Checks whether the first local produce achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void locProdAchieve0() {
        if (locProdPoints > 0 && locProd0 == false) {
            System.out.println("Ate local produce for 1st time"); //TODO: delete
            locProd0 = true;
        }
    }

    /**
     * Checks whether the second vegetarian achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void locProdAchieve1() {
        if (locProdPoints > 12345 && locProd1 == false) { //TODO: calculate the exact amount of points that eating 1 kg of local produce gets you
            System.out.println("Ate 1kg of local produce"); //TODO: delete
            locProd1 = true;
        }
    }

    /**
     * Checks whether the third vegetarian achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void locProdAchieve2() {
        if (locProdPoints > 12345 && locProd2 == false) { //TODO: calculate the exact amount of points that eating 1000 kg of local produce gets you
            System.out.println("Ate local produce for 1st time"); //TODO: delete
            locProd2 = true;
        }
    }

    /**
     * Checks whether the fourth vegetarian achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void locProdAchieve3() {
        if (locProdPoints > 1000 && locProd3 == false) {
            System.out.println("Ate local produce for 1st time"); //TODO: delete
            locProd3 = true;
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

}