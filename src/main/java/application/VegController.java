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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class VegController implements Initializable {
    public String mealType;
    public Text saved;
    @FXML
    ToolBar myToolbar;

    //configure the table
    @FXML private TableView<TableContents> tableView;
    @FXML private TableColumn<TableContents, LocalDate> dateColumn;
    @FXML private TableColumn<TableContents, Integer> pointsColumn;
    @FXML private TableColumn<TableContents, String> typeColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //sets up columns of the table
        dateColumn.setCellValueFactory(new PropertyValueFactory<TableContents, LocalDate>("date"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<TableContents, String>("type"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<TableContents, Integer>("points"))
                ;

        fetchData();
    }

    /**
     * returns an OberservableList of Content objects(date and points).
     */
    public ObservableList<TableContents> getContent() {
        ObservableList<TableContents> content = FXCollections.observableArrayList();
        content.add(new TableContents(50,mealType));

        return content;
    }

    /**
     * Request the date, process the data, and print it to the table.
     */
    public void fetchData() {
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        //request
        String request = "{'type' : 'VegMeal', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "',"
                + "'addMeal': false}";
        String response = scn.sendPostRequest(request);

        //response
        String[] jsons = response.split(", ");
        for (int i = 0; i < 20; i++) {
            jsons[i] = jsons[i].replaceAll("\\[|\\]", "");
            String[] res = parseRow(jsons[i]);
            addToTable(Integer.parseInt(res[0]), res[1].replaceAll("^\"|\"$", ""),
                    res[2].replaceAll("^\\\"|\\+\\d\\d\\\"$", ""));
        }
    }

    /**
     * After clicking vegan button client will receive 60 points.
     * @param actionEvent Click of the button
     */
    public void vegan(ActionEvent actionEvent) {
        mealType = "vegan";

        TableContents tablecontent = new TableContents(60, mealType);
        tableView.getItems().add(0,tablecontent);

        add(actionEvent);
    }

    /**
     * After clicking vegetarian button client will receive 50 points.
     * @param actionEvent Click of the button
     */
    public void vegetarian(ActionEvent actionEvent) {
        mealType = "vegetarian";

        TableContents tablecontent = new TableContents(50,mealType);
        tableView.getItems().add(0,tablecontent);

        add(actionEvent);
    }

    /**
     * Helper function that transform the json into array of variables.
     * @param responseJson the json that will be processed
     * @return A array with the values of the json
     */
    public String[] parseRow(String responseJson) {
        if (responseJson != null) {
            //de-Json the response and update the amount of points.
            JsonObject json = null;
            try {
                json = new JsonParser().parse(responseJson).getAsJsonObject();
            } catch (IllegalStateException e) {
                System.out.println("Returned something that's not even JSON");
                return null;
            }
            String[] res = new String[3];
            try {
                res[0] = json.get("points").toString();
                res[1] = json.get("type").toString();
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

    public void addToTable(int points, String type, String datetime) {
        TableContents tablecontent = new TableContents(points,type,datetime);
        tableView.getItems().add(tablecontent);
    }

    /**
     * Searches for a meal that matches the input.
     * @param actionEvent The click of the button
     */
    public void add(ActionEvent actionEvent) {
        // Gets username and password to send via json to the server
        // Which uses the CLIENT stored vars.
        //  Don't use any server queries on the gosh darned client, that's only for the server
        //  And please for the love of God don't just change server code vars to static just
        // to make it compile

        //send json request

        System.out.println("Running add");
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        String request = "{'type' : 'VegMeal', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "',"
                + "'addMeal': true, 'mealType' : '" + mealType + "'}";

        String response = scn.sendPostRequest(request);
        System.out.println(parsePoints(response));

        saved.setText("You saved: " + parseCo2(response) + " kg of CO2");
    }

    /**
     * Helper function to parse response json.
     *
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
     * This method create the request for only points.
     */
    public int returnPoints() {
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        String request = "{'type' : 'VegMeal', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "',"
                + "'addMeal': false, 'mealType' : '" + mealType + "'}";

        String response = scn.sendPostRequest(request);

        System.out.println(parsePoints(response));
        return parsePoints(response);
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
     * Go to the User Stats screen.
     * @param actionEvent Click of the button.
     * @throws IOException Throw if chart is invalid
     */
    public void goStats(ActionEvent actionEvent) throws IOException {
        go("StatsPieChart");
    }
}
