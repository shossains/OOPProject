package application;

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
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class SolarPanelController implements Initializable {

    public TextField output;
    public int outputInt;

    @FXML ToolBar myToolbar;

    //configures the table
    @FXML private TableView<TableContents> tableView;
    @FXML private TableColumn<TableContents, Date> dateColumn;
    @FXML private TableColumn<TableContents, Integer> outputColumn;
    @FXML private TableColumn<TableContents, Integer> pointsColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        dateColumn.setCellValueFactory(new PropertyValueFactory<TableContents, Date>("date"));
        outputColumn.setCellValueFactory(new PropertyValueFactory<TableContents, Integer>("output"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<TableContents, Integer>("points"));

        //tableView.setItems(getContent());
    }

    /**
     * returns an ObservableList of Content objects (date, output and point)
     */
    public ObservableList<TableContents> getContent(){
        ObservableList<TableContents> content = FXCollections.observableArrayList();
        content.add(new TableContents(50, output.getText()));

        return content;
    }

    /**
     * Add content to table
     * @param output
     */
    public void addToTable(int output){
        TableContents tablecontent = new TableContents(50, output);
        tableView.getItems().add(tablecontent);
    }

    /**
     * Takes the input and converts it from string to int.
     */
    public void intify() {
        outputInt = Integer.parseInt(output.getText());
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
     * Go to the Local Produce screen.
     * @param actionEvent The click of the button
     * @throws IOException Throw if file is missing/corrupted/incomplete
     */
    public void goLocal(ActionEvent actionEvent) throws IOException {
        go("LocalProduce");
    }



}
