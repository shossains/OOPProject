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
import javafx.scene.chart.PieChart;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatsController implements Initializable {

    @FXML ToolBar myToolbar;

    @FXML private PieChart userPieChart;
    @FXML private PieChart friendPieChart;

  @Override
    public void initialize(URL url, ResourceBundle rb) {
    int[] allPoints = request();
    int vegMealValue = allPoints[0];
    int locProdValue = allPoints[1];
    int bikeValue = allPoints[2];
    int pubTransValue = allPoints[3];

    ObservableList<PieChart.Data> userPieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("VegMeal", vegMealValue),
                        new PieChart.Data("LocalProduce", locProdValue),
                        new PieChart.Data("Bike Ride", bikeValue),
                        new PieChart.Data("Public Transport", pubTransValue),
                        new PieChart.Data("Temperature", 80),
                        new PieChart.Data("SolarPanels", 50));
    userPieChart.setData(userPieChartData);
//TODO: for the friend values temporarily add a global average -> send query to sum all points from all the users and divide by the amount of users
      ObservableList<PieChart.Data> friendPieChartData
              = FXCollections.observableArrayList(
              new PieChart.Data("VegMeal", 60),
              new PieChart.Data("LocalProduce", 25),
              new PieChart.Data("Bike Ride", 120),
              new PieChart.Data("Public Transport", 10),
              new PieChart.Data("Temperature", 80),
              new PieChart.Data("SolarPanels", 50));
      friendPieChart.setData(friendPieChartData);
  }


  public int[] request() {
    SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

    String request = "{'type' : 'Combined', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "'}";

    String response = scn.sendPostRequest(request);

    //TODO: delete this
    System.out.println(parseVegPoints(response));
    System.out.println(parseLocProdPoints(response));
    System.out.println(parseBikePoints(response));
    System.out.println(parsePubTransPoints(response));

    int[] ints = new int[6];
    ints[0] = parseVegPoints(response);
    ints[1] = parseLocProdPoints(response);
    ints[2] = parseBikePoints(response);
    ints[3] = parsePubTransPoints(response);

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
