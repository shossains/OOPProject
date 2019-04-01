package application;

import client.SecureClientNetworking;

import java.net.URL;
import java.util.ResourceBundle;

public class AchievementsController {
    StatsController statsController = new StatsController();
    int[] points = statsController.request();

    public void initialize(URL url, ResourceBundle rb) {
    }

    public boolean check() {
        if(points[1] > 1) {
            System.out.println("Getting started");
        }
    }
}
