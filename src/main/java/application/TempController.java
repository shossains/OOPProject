package application;

import calculator.TemperatureCalculator;
import client.SecureClientNetworking;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

//TODO
//Change all the VegMeal things into temperature things

public class TempController {
    public TextField insert;
    public int inputTemp;
    @FXML
    TextField input;

    /**
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
        if(RegController.isInt(input.getText())) {
            inputTemp = Integer.parseInt(input.getText());
            request(true, inputTemp);
        } else {
            //TODO: send a message to the user saying "this is not a valid input"
            return;
        }

    }

    /**
     * This method creates the request for just points.
     * @param actionEvent opening a scene or clicking any given button
     */
    public void getTotal(ActionEvent actionEvent) {
        request(false, 0);
    }

    public void request(boolean added, int inputTemp) {
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        String request = "{'type' : 'TempAdj', 'username' : '"
                + User.getUsername() + "', 'password':'" + User.getPassword() + "',"
                + "'addTemp':" + added + "', 'temp':'" + inputTemp + "'}'";

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
}
