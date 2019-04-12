package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AchievementsController implements Initializable {
    @FXML ToolBar myToolbar;

    @FXML ImageView vegTrophy0;
    @FXML ImageView vegTrophy1;
    @FXML ImageView vegTrophy2;

    @FXML ImageView locProdTrophy0;
    @FXML ImageView locProdTrophy1;
    @FXML ImageView locProdTrophy2;

    @FXML ImageView bikeTrophy0;
    @FXML ImageView bikeTrophy1;
    @FXML ImageView bikeTrophy2;

    @FXML ImageView pubTransTrophy0;
    @FXML ImageView pubTransTrophy1;
    @FXML ImageView pubTransTrophy2;

    @FXML ImageView tempTrophy0;
    @FXML ImageView tempTrophy1;
    @FXML ImageView tempTrophy2;

    @FXML ImageView solarTrophy0;
    @FXML ImageView solarTrophy1;
    @FXML ImageView solarTrophy2;


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

    boolean temp0 = false;
    boolean temp1 = false;
    boolean temp2 = false;

    boolean solar0 = false;
    boolean solar1 = false;
    boolean solar2 = false;

    StatsController statsController = new StatsController();
    int[] points = statsController.request();
    int vegPoints = points[0];
    int locProdPoints = points[1];
    int bikePoints = points[2];
    int pubTransPoints = points[3];
    int tempPoints = points[4];
    int solarPoints = points[5];

    int total = vegPoints + locProdPoints + bikePoints + pubTransPoints + tempPoints; //TODO

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ColorAdjust enable = new ColorAdjust();
        enable.setBrightness(0);

        vegAchieve0(enable);
        vegAchieve1(enable);
        vegAchieve2(enable);
        locProdAchieve0(enable);
        locProdAchieve1(enable);
        locProdAchieve2(enable);
        bikeAchieve0(enable);
        bikeAchieve1(enable);
        bikeAchieve2(enable);
        pubTransAchieve0(enable);
        pubTransAchieve1(enable);
        pubTransAchieve2(enable);
        tempAchieve0(enable);
        tempAchieve1(enable);
        tempAchieve2(enable);
        solarAchieve0(enable);
        solarAchieve1(enable);
        solarAchieve2(enable);
    }

    /**
     * Checks whether the first vegetarian achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void vegAchieve0(ColorAdjust colorAdjust) {
        if (vegPoints >= 100 && veg0 == false) {
            vegTrophy0.setEffect(colorAdjust);
            veg0 = true;
        }
    }

    /**
     * Checks whether the second vegetarian achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void vegAchieve1(ColorAdjust colorAdjust) {
        if (vegPoints >= 1000 && veg1 == false) {
            vegTrophy1.setEffect(colorAdjust);
            veg1 = true;
        }
    }

    /**
     * Checks whether the third vegetarian achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void vegAchieve2(ColorAdjust colorAdjust) {
        if (vegPoints >= 10000 && veg2 == false) {
            vegTrophy2.setEffect(colorAdjust);
            veg2 = true;
        }
    }

    /**
     * Checks whether the first local produce achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void locProdAchieve0(ColorAdjust colorAdjust) {
        if (locProdPoints >= 100 && locProd0 == false) {
            locProdTrophy0.setEffect(colorAdjust);
            locProd0 = true;
        }
    }

    /**
     * Checks whether the second local produce achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void locProdAchieve1(ColorAdjust colorAdjust) {
        if (locProdPoints >= 1000 && locProd1 == false) {
            locProdTrophy1.setEffect(colorAdjust);
            locProd1 = true;
        }
    }

    /**
     * Checks whether the third local produce achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void locProdAchieve2(ColorAdjust colorAdjust) {
        if (locProdPoints >= 10000 && locProd2 == false) {
            locProdTrophy2.setEffect(colorAdjust);
            locProd2 = true;
        }
    }

    /**
     * Checks whether the first bike achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void bikeAchieve0(ColorAdjust colorAdjust) {
        if (bikePoints >= 100 && bike0 == false) {
            bikeTrophy0.setEffect(colorAdjust);
            bike0 = true;
        }
    }

    /**
     * Checks whether the second bike achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void bikeAchieve1(ColorAdjust colorAdjust) {
        if (bikePoints >= 1000 && bike1 == false) {
            bikeTrophy1.setEffect(colorAdjust);
            bike1 = true;
        }
    }

    /**
     * Checks whether the third bike achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void bikeAchieve2(ColorAdjust colorAdjust) {
        if (bikePoints >= 10000 && bike2 == false) {
            bikeTrophy2.setEffect(colorAdjust);
            bike2 = true;
        }
    }

    /**
     * Checks whether the first public transport achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void pubTransAchieve0(ColorAdjust colorAdjust) {
        if (pubTransPoints >= 100 && pubTrans0 == false) {
            pubTransTrophy0.setEffect(colorAdjust);
            pubTrans0 = true;
        }
    }

    /**
     * Checks whether the second public transport achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void pubTransAchieve1(ColorAdjust colorAdjust) {
        if (pubTransPoints >= 1000 && pubTrans1 == false) {
            pubTransTrophy1.setEffect(colorAdjust);
            pubTrans1 = true;
        }
    }

    /**
     * Checks whether the third public transport achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void pubTransAchieve2(ColorAdjust colorAdjust) {
        if (pubTransPoints >= 10000 && pubTrans2 == false) {
            pubTransTrophy2.setEffect(colorAdjust);
            pubTrans2 = true;
        }
    }

    /**
     * Checks whether the first temperature achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void tempAchieve0(ColorAdjust colorAdjust) {
        if (tempPoints >= 100 && temp0 == false) {
            tempTrophy0.setEffect(colorAdjust);
            temp0 = true;
        }
    }

    /**
     * Checks whether the second temperature achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void tempAchieve1(ColorAdjust colorAdjust) {
        if (tempPoints >= 1000 && temp1 == false) {
            tempTrophy1.setEffect(colorAdjust);
            temp1 = true;
        }
    }

    /**
     * Checks whether the third temperature achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void tempAchieve2(ColorAdjust colorAdjust) {
        if (tempPoints >= 10000 && temp2 == false) {
            tempTrophy2.setEffect(colorAdjust);
            temp2 = true;
        }
    }

    /**
     * Checks whether the first solar panel achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void solarAchieve0(ColorAdjust colorAdjust) {
        if (solarPoints >= 100 && solar0 == false) {
            solarTrophy0.setEffect(colorAdjust);
            solar0 = true;
        }
    }

    /**
     * Checks whether the second solar panel achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void solarAchieve1(ColorAdjust colorAdjust) {
        if (solarPoints >= 1000 && solar1 == false) {
            solarTrophy1.setEffect(colorAdjust);
            solar1 = true;
        }
    }

    /**
     * Checks whether the third solar panel achievement has been achieved.
     * Sets the achievement true/false.
     */
    public void solarAchieve2(ColorAdjust colorAdjust) {
        if (solarPoints >= 1000 && solar2 == false) {
            solarTrophy2.setEffect(colorAdjust);
            solar2 = true;
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
     * @throws IOException Throw if file is missing/corrupted/incomplete
     */
    public void goStats(ActionEvent actionEvent) throws IOException {
        go("StatsPiechart");
    }

    /**
     * Go to the Personal settings screen.
     * @param actionEvent The click of the button
     * @throws IOException Throw if chart is invalid
     */
    public void goPersonal(ActionEvent actionEvent) throws IOException {
        go("PersonalSettings");
    }

}