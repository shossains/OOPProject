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

    @FXML Circle locProdCircle0;
    @FXML Circle locProdCircle1;
    @FXML Circle locProdCircle2;

    @FXML Circle bikeCircle0;
    @FXML Circle bikeCircle1;
    @FXML Circle bikeCircle2;

    @FXML Circle pubTransCircle0;
    @FXML Circle pubTransCircle1;
    @FXML Circle pubTransCircle2;

    boolean veg0 = false;
    boolean veg1 = false;
    boolean veg2 = false;

    boolean locProd0 = false;
    boolean locProd1 = false;
    boolean locProd2 = false;

    boolean bike0 = false;
    boolean bike1 = false;
    boolean bike2 = false;

    boolean pubTrans0 = false;
    boolean pubTrans1 = false;
    boolean pubTrans2 = false;

    StatsController statsController = new StatsController();
    int[] points = statsController.request();
    int vegPoints = points[0];
    int locProdPoints = points[1];
    int bikePoints = points[2];
    int pubTransPoints = points[3];

    int total = vegPoints + locProdPoints + bikePoints + pubTransPoints; //TODO

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vegAchieve0();
        vegAchieve2();
        locProdAchieve0();
        locProdAchieve1();
        locProdAchieve2();
        bikeAchieve0();
        bikeAchieve1();
        bikeAchieve2();
        pubTransAchieve0();
        pubTransAchieve1();
        pubTransAchieve2();
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
            vegCircle1.setFill(Paint.valueOf("#d0d0d0"));
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
            vegCircle2.setFill(Paint.valueOf("#ecec09"));
            veg2 = true;
        }
    }

    /**
     * Checks whether the first local produce achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void locProdAchieve0() {
        if (locProdPoints > 33 && locProd0 == false) {
            locProdCircle0.setFill(Paint.valueOf("#ca784b"));
            locProd0 = true;
        }
    }

    /**
     * Checks whether the second local produce achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void locProdAchieve1() {
        if (locProdPoints > 32800 && locProd1 == false) {
            locProdCircle1.setFill(Paint.valueOf("#d0d0d0"));
            locProd1 = true;
        }
    }

    /**
     * Checks whether the third local produce achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void locProdAchieve2() {
        if (locProdPoints > 1000 && locProd2 == false) {
            locProdCircle2.setFill(Paint.valueOf("#ecec09"));
            locProd2 = true;
        }
    }

    /**
     * Checks whether the first bike achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void bikeAchieve0() {
        if (bikePoints > 13 && bike0 == false) {
            bikeCircle0.setFill(Paint.valueOf("#ca784b"));
            bike0 = true;
        }
    }

    /**
     * Checks whether the second bike achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void bikeAchieve1() {
        if (bikePoints > 13377 && bike1 == false) {
            bikeCircle1.setFill(Paint.valueOf("#d0d0d0"));
            bike1 = true;
        }
    }

    /**
     * Checks whether the third bike achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void bikeAchieve2() {
        if (bikePoints >= 1000 && bike2 == false) {
            bikeCircle2.setFill(Paint.valueOf("#ecec09"));
            bike2 = true;
        }
    }

    /**
     * Checks whether the first public transport achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void pubTransAchieve0() {
        if (pubTransPoints >= 1 && pubTrans0 == false) {
            pubTransCircle0.setFill(Paint.valueOf("#ca784b"));
            pubTrans0 = true;
        }
    }

    /**
     * Checks whether the second public transport achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void pubTransAchieve1() {
        if (pubTransPoints >= 126 && pubTrans1 == false) {
            pubTransCircle1.setFill(Paint.valueOf("#d0d0d0"));
            pubTrans1 = true;
        }
    }

    /**
     * Checks whether the third public transport achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void pubTransAchieve2() {
        if (pubTransPoints >= 1000 && pubTrans2 == false) {
            pubTransCircle2.setFill(Paint.valueOf("#ecec09"));
            pubTrans2 = true;
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