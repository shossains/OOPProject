package application;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AchievementsController implements Initializable {
    StatsController statsController = new StatsController();
    int[] points = statsController.request();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void update() {
        achievements();
    }

    public void achievements() {
        if(points[1] > 1) {
            System.out.println("Getting started");
        }
    }
}
