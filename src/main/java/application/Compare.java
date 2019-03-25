package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Compare {
    private final SimpleStringProperty categories = new SimpleStringProperty("");
    public SimpleIntegerProperty userPoints = new SimpleIntegerProperty(0);
    public  SimpleIntegerProperty friendPoints = new SimpleIntegerProperty(0);

    public Compare() {
        this("", 0, 0);
    }

    public Compare(String categories, int userPoints, int friendPoints) {
        setCategories (categories);
        setUserPoints(userPoints);
        setFriendPoints(friendPoints);
    }

    public String getCategories() {
        return categories.get();
    }

    public void setCategories(String category) {
        categories.set(category);
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
