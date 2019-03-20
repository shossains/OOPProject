package application;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import client.SecureClientNetworking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VegController implements Initializable {
    public TextField insert;
    public static String type;

    //configure the table
    @FXML private TableView<TableContents> tableView;
    @FXML private TableColumn<TableContents, LocalDate> dateColumn;
    @FXML private TableColumn<TableContents, Integer> pointsColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //sets up columns of the table
        dateColumn.setCellValueFactory(new PropertyValueFactory<TableContents, LocalDate>("date"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<TableContents, Integer>("points"));
    }

    /**
     * returns an OberservableList of Content objects(date and point)
     */
    public ObservableList<TableContents> getContent(){
        ObservableList<TableContents> content = FXCollections.observableArrayList();
        content.add(new TableContents(50, LocalDate.now()));

        return content;
    }

    public void vegan(ActionEvent actionEvent) {
        type = "vegan";
        add(actionEvent);
    }

    public void vegetarian(ActionEvent actionEvent) {
        type = "vegetarian";
        add(actionEvent);
    }

    /**
     * Searches for a meal that matches the input.
     *
     * @param actionEvent The click of the button
     */
    public void add(ActionEvent actionEvent) {
        // Gets username and password to send via json to the server
        // Which uses the CLIENT stored vars.
        //Don't use any server queries on the gosh darned client, that's only for the server
        //And please for the love of God don't just change server code vars to static just
        // to make it compile

        //send json request

        System.out.println("running add");
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        String request = "{'type' : 'VegMeal', 'username' : '"
                + User.getUsername() + "', 'password':'" + User.getPassword() + "',"
                + "'addMeal':true}";

        String response = scn.sendPostRequest(request);

        System.out.println(parsePoints(response));
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
     * This method create the request for only points.
     * @param actionEvent opening a scene or clicking any given button
     */
    public void returnPoints(ActionEvent actionEvent) {
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        String request = "{\"type\" : \"VegMeal\", \"username\" : \""
                + User.getUsername() + "\", \"password\" : \""
                + User.getPassword() + "\", \"addMeal\", false}";

        String response = scn.sendPostRequest(request);

        System.out.println(parsePoints(response));
    }
    public void addToTable(ActionEvent tableEvent){
        tableView.setItems(getContent());

    }
}
