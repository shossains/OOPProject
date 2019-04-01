package application;

import client.SecureClientNetworking;

import java.net.URL;
import java.util.ResourceBundle;

public class AchievementsController {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(request())
    }

    public int request() {
        SecureClientNetworking scn = new SecureClientNetworking(User.getServerUrl());

        String request = "{'type' : 'Combined', 'username' : '"
                + User.getUsername() + "', 'password' : '" + User.getPassword() + "'}";

        String response = scn.sendPostRequest(request);
        //parse respone
    }
}
