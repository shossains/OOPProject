package application;

import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDate;

public class TableContents {

    private SimpleIntegerProperty points;
    private LocalDate date;

    public TableContents(int points, LocalDate date) {
        this.points = new SimpleIntegerProperty(points);
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
