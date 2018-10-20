package entities;

import java.awt.*;


public class Incident {

    private int latitude;
    private int longitude;

    // 1: important -> 5: not important
    private int gravity;

    public int getGravity() {
        return gravity;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public Point getLocation() {
        return new Point(latitude, longitude);
    }
}
