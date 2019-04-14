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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class SolarPanelController implements Initializable {
    public TextField kwh;
    public Label invalidKwh;
    public Text saved;
    public int kwhInt;
    @FXML
    ToolBar myToolbar;

    //configure the table
    @FXML private TableView<TableContents> tableView;
    @FXML private TableColumn<TableContents, LocalDate> dateColumn;
    @FXML private TableColumn<TableContents, Integer> pointsColumn;
    @FXML private TableColumn<TableContents, Integer> kwhColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //sets up columns of the table
        dateColumn.setCellValueFactory(new PropertyValueFactory<TableContents, LocalDate>("date"));
        kwhColumn.setCellValueFactory(
                new PropertyValueFactory<TableContents, Integer>("integer"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<TableContents, Integer>("points"))
        ;

        fetchData();
    }

    /**
     * returns an OberservableList of Content objects(date and points).
     */
    public ObservableList<TableContents> getContent() {
        ObservableList<TableContents> content = FXCollections.observableArrayList();
        content.add(new TableContents(40,kwh.getText()));
        return content;
    }

    /**
     * Request the date, process the data, and print it to the table.
     */
    public void fetchData() {
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        //request
        String request = "{'type' : 'Solar', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "',"
                + "'addSolar': false}";
        String response = scn.sendPostRequest(request);

        //response
        if (response != null) {
            String[] jsons = response.split(", ");
            for (int i = 0; i < jsons.length; i++) {
                jsons[i] = jsons[i].replaceAll("\\[|\\]", "");
                if (jsons[i].equals("null")) {
                    break;
                }
                String[] res = parseRow(jsons[i]);
                addToTable(Integer.parseInt(res[0]), Integer.parseInt(res[1]),
                        res[2].replaceAll("^\\\"|\\+\\d\\d\\\"$", ""));
            }
        }
    }

    public void addToTable(int points, int kwh, String datetime) {
        TableContents tablecontent = new TableContents(points,kwh,datetime);
        tableView.getItems().add(tablecontent);
    }

    /**
     * Takes the input and converts it from string to int.
     */
    public void intify() {
        kwhInt = Integer.parseInt(kwh.getText());
    }

    /**
     * Check if input is valid, only then proceed.
     */
    public void proceed(ActionEvent actionEvent) {
        boolean kwh = invalidKwh();

        if (!kwh) {
            intify();
            add(actionEvent);
        }
    }

    /**
     * Searches for a meal that matches the input.
     * @param actionEvent The click of the button
     */
    public void add(ActionEvent actionEvent) {
        // Gets username and password to send via json to the server
        // Which uses the CLIENT stored vars.
        //Don't use any server queries on the gosh darned client, that's only for the server
        //And please for the love of God don't just change server code vars to static just
        // to make it compile

        //send json request
        System.out.println("Running add");

        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        String request = "{'type' : 'Solar', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "', "
                + "'addSolar' : true, 'kwh' : " + kwhInt + "}";

        String response = scn.sendPostRequest(request);
        System.out.println(parsePoints(response));

        TableContents tablecontent = new TableContents(addedPoints(response), kwhInt);
        tableView.getItems().add(0,tablecontent);

        saved.setText("You've saved " + parseCo2(response) + " kg of CO2");
    }

    /**
     * Helper function that transform the json into array of variables.
     * @param responseJson the json that will be processed
     * @return A array with the values of the json
     */
    public String[] parseRow(String responseJson) {
        String[] res = new String[3];

        if (responseJson != null) {
            JsonObject json = null;
            try {
                json = new JsonParser().parse(responseJson).getAsJsonObject();
            } catch (IllegalStateException e) {
                System.out.println("Returned something that's not even JSON");
                return null;
            }
            try {
                res[0] = json.get("points").toString();
                res[1] = json.get("kwh").toString();
                res[2] = json.get("datetime").toString();
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
     * Helper function to parse response json.
     * @param responseJson The raw json response from the server
     * @return The current amount of points.
     */
    public int parsePoints(String responseJson) {
        if (responseJson != null) {
            //de-Json the response and update the amount of points.
            JsonObject json = null;
            try {
                json = new JsonParser().parse(responseJson).getAsJsonObject();
            } catch (IllegalStateException e) {
                System.out.println("Returned something that's not even JSON");
                return -1;
            }
            int points = -1;
            try {
                points = Integer.parseInt(json.get("points").toString());
            } catch (NumberFormatException e) {
                System.out.println(responseJson);
                System.out.println("Bad json format returned");
            }
            return points;
        } else {
            System.out.println("Null JSON returned");
            return -1;
        }
    }

    /**
     * Helper function to parse response json.
     * @param responseJson The raw json response from the server
     * @return The current amount of co2 saved.
     */
    public Double parseCo2(String responseJson) {
        if (responseJson != null) {
            //de-Json the response and update the amount of points.
            JsonObject json = null;
            try {
                json = new JsonParser().parse(responseJson).getAsJsonObject();
            } catch (IllegalStateException e) {
                System.out.println("Returned something that's not even JSON");
                return -1.0;
            }
            Double points = -1.0;
            try {
                points = Double.parseDouble(json.get("co2").toString());
            } catch (NumberFormatException e) {
                System.out.println(responseJson);
                System.out.println("Bad json format returned");
            }
            return points;
        } else {
            System.out.println("Null JSON returned");
            return -1.0;
        }
    }

    /**
     * Helper function to parse response json.
     * @param responseJson The raw json response from the server
     * @return The added amount of points.
     */
    public int addedPoints(String responseJson) {
        if (responseJson != null) {
            //de-Json the response and update the amount of points.
            JsonObject json = null;
            try {
                json = new JsonParser().parse(responseJson).getAsJsonObject();
            } catch (IllegalStateException e) {
                System.out.println("Returned something that's not even JSON");
                return -1;
            }
            int points = -1;
            try {
                points = Integer.parseInt(json.get("added").toString());
            } catch (NumberFormatException e) {
                System.out.println(responseJson);
                System.out.println("Bad json format returned");
            }
            return points;
        } else {
            System.out.println("Null JSON returned");
            return -1;
        }
    }

    /**
     * This method create the request for only points.
     * @param actionEvent opening a scene or clicking any given button
     */
    public void returnPoints(ActionEvent actionEvent) {
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        String request = "{'type' : 'Solar', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "',"
                + "'addMeal': false}";

        String response = scn.sendPostRequest(request);

        System.out.println(parsePoints(response));
    }

    /**
     * Check whether Phone textField are integers only or empty.
     * @return true if empty or invalid
     */
    public boolean invalidKwh() {
        if (kwh.getText().equals("")) {
            invalidKwh.setText("Please enter a valid number");
            return true;

        }

        if (!isInt(kwh.getText()) || Integer.parseInt(kwh.getText()) >= 1000) {
            invalidKwh.setText("Please enter a valid number");
            return true;
        } else {
            invalidKwh.setText("");
            return false;
        }
    }

    /**
     * Check whether input is an integer.
     * @param input the input that needs to be checked
     * @return True or false
     */
    public static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
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
     * Go to the User Stats screen.
     * @param actionEvent The click of the button
     * @throws IOException Throw if chart is invalid
     */
    public void goStats(ActionEvent actionEvent) throws IOException {
        go("StatsPieChart");
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

    /**
     * Go back to the Home screen
     * @param actionEvent The click of the button
     * @throws IOException Throws if file is missing/corrupted/incomplete
     */
    public void goHome(ActionEvent actionEvent) throws IOException{
        go("HomeScreen");
    }
}
