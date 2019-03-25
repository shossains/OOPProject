package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Compare {
    private final SimpleStringProperty features = new SimpleStringProperty("");
    public SimpleIntegerProperty userPoints = new SimpleIntegerProperty(0);
    public  SimpleIntegerProperty friendPoints = new SimpleIntegerProperty(0);

    public Compare() {
        this("", 0, 0);
    }

    public Compare(String feautures, int userPoints, int friendPoints) {
        setFeatures(feautures);
        setUserPoints(userPoints);
        setFriendPoints(friendPoints);
    }

    public String getFeatures() {
        return features.get();
    }

    public void setFeatures(String feature) {
        features.set(feature);
    }

    public int getUserPoints() {
        return userPoints.get();
    }

    public void setUserPoints(int points) {
        userPoints.set(points);
    }


    public int getFriendPoints() {
        return friendPoints.get();
    }

    public void setFriendPoints(int points) {
        friendPoints.set(points);
    }
}
