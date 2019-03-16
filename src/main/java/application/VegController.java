package application;

import client.SecureClientNetworking;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;

public class VegController {
    public TextField insert;

    /**
     * Searches for a meal that matches the input.
     * @param actionEvent The click of the button
     * @throws IOException Throw exception
     */
    public void add(ActionEvent actionEvent) throws IOException {
        // Gets username and password to send via json to the server
        // Which uses the CLIENT stored vars.
        String username = "Dont use any server queries on the gosh darned client, thats only for the server";
        String password = "And please for the love of god dont just change server code vars to static just to make it compile";
        //send json request
        SecureClientNetworking scn = new SecureClientNetworking(new URL("https://localhost:3000"));
        String request = "{\"type\" : \"VegMeal\", \"username\" : \"" + username + "\", \"password\" : \"" + password + "\", \"addMeal\", \"false\"}";
        String response = scn.sendPostRequest(request);
        //de-Json the response and update the amount of points.
        JsonObject json = new JsonParser().parse(response).getAsJsonObject();

        int points = Integer.parseInt(json.get("points").toString());
        System.out.println(points);
    }
}
