package application;

import client.SecureClientNetworking;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VegController implements Initializable {
    public TextField insert;

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

    /**
     * Adds the date of when a vegatarian meal was eaten
     * and the number of points gained when clicking the button
     * @param tableEvent
     */
    public void addToTable(ActionEvent tableEvent){
        tableView.setItems(getContent());

    }


}
