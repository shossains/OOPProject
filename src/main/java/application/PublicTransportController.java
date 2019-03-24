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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class PublicTransportController implements Initializable {
    public TextField distance;
    public Label invalidDistance;
    public Label invalidRadio;
    public int distanceInt;
    public String vehicleType;
    @FXML ToolBar myToolbar;
    @FXML private RadioButton train;
    @FXML private RadioButton bus;


    //configure the table
    @FXML private TableView<TableContents> tableView;
    @FXML private TableColumn<TableContents, LocalDate> dateColumn;
    @FXML private TableColumn<TableContents, Integer> pointsColumn;
    @FXML private TableColumn<TableContents, Integer> distanceColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //sets up columns of the table
        dateColumn.setCellValueFactory(new PropertyValueFactory<TableContents, LocalDate>("date"));
        distanceColumn.setCellValueFactory(
                new PropertyValueFactory<TableContents, Integer>("integer"));
        pointsColumn.setCellValueFactory(
                new PropertyValueFactory<TableContents, Integer>("points"));
    }

    /**
     * returns an OberservableList of Content objects(date and points).
     */
    public ObservableList<TableContents> getContent() {
        ObservableList<TableContents> content = FXCollections.observableArrayList();
        content.add(new TableContents(40,distance.getText()));
        return content;
    }

    /**
     * General method to add something to the table.
     * @param points amount of points
     * @param weight of bought groceries
     */
    public void addToTable(int points, int weight) {
        TableContents tablecontent = new TableContents(points,weight);
        tableView.getItems().add(tablecontent);
    }

    /**
     * Determine if the which radiobutton was pressed.
     * @param actionEvent The click of the button
     */
    public boolean radioButton(ActionEvent actionEvent) {
        if (bus.isSelected()) {
            vehicleType = "bus";
            invalidRadio.setText("");
            return true;
        } else if (train.isSelected()) {
            vehicleType = "train";
            invalidRadio.setText("");
            return true;
        } else {
            invalidRadio.setText("Please select one!");
            return false;
        }
    }

    /**
     * Takes the input and converts it from string to int.
     */
    public void intify() {
        distanceInt = Integer.parseInt(distance.getText());
    }

    /**
     * Check if input is valid, only then proceed.
     */
    public void proceed(ActionEvent actionEvent) {
        boolean distance = invalidDistance();
        boolean radioButton = radioButton(actionEvent);

        if (!distance && radioButton) {
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

        String request = "{'type' : 'PublicTransport', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "', "
                + "'addPublic' : true, 'distance' : " + distanceInt
                + ", 'vehicle' : '" + vehicleType + "'}";

        String response = scn.sendPostRequest(request);
        System.out.println(parsePoints(response));

        TableContents tablecontent = new TableContents(addedPoints(response),distanceInt);
        tableView.getItems().add(tablecontent);
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

        String request = "{'type' : 'BikeRide', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "',"
                + "'addBike': false}";

        String response = scn.sendPostRequest(request);

        System.out.println(parsePoints(response));
    }

    /**
     * Check whether Phone textField are integers only or empty.
     * @return true if empty or invalid
     */
    public boolean invalidDistance() {
        if (distance.getText().equals("")) {
            invalidDistance.setText("Please enter a valid number");
            return true;

        }

        if (!isInt(distance.getText())) {
            invalidDistance.setText("Please enter a valid number");
            return true;
        } else {
            invalidDistance.setText("");
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
}
