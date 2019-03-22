package application;

import javafx.beans.property.SimpleIntegerProperty;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TableContents {

    private SimpleIntegerProperty points;
    private String date;
    private String type;

    /**
     * Constructor for the data of the table.
     * @param points data of amount of points that will be projected
     * @param type of the meal that will be projected
     */
    public TableContents(int points, String type) {
        this.points = new SimpleIntegerProperty(points);
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        this.type = type;
    }

    public int getPoints() {
        return points.get();
    }

    public SimpleIntegerProperty pointsProperty() {
        return points;
    }

    public void setPoints(int points) {
        this.points.set(points);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
