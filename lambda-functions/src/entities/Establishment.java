package entities;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Establishment {

    private double latitude;

    private double longitude;

    private String name;

    public Establishment(double lat, double lon, String name) {
        this.name = name;
        this.latitude = lat;
        this.longitude = lon;
    }

    public double getLatitude () {
        return latitude;
    }

    public double getLongitude () {
        return longitude;
    }

    public Point2D.Double getLocation() {
        return new Point2D.Double(latitude, longitude);
    }

    public abstract boolean canAffordPacient(Incident i);
    public abstract void placePacient();
    public abstract void freePacient();
}
