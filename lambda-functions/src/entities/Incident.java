package entities;

import java.awt.*;
import java.awt.geom.Point2D;

public class Incident {

    private double latitude;
    private double longitude;

    // 1: important -> 5: not important
    private int gravity;

    public Incident(double latitude, double longitude, int gravity) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.gravity = gravity;
    }

    public int getGravity() {
        return gravity;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Point2D.Double getLocation() {
        return new Point2D.Double(latitude, longitude);
    }
}
