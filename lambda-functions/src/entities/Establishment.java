package entities;

import java.awt.*;

public abstract class Establishment {

    private double latitude;

    private double longitude;

    public Establishment(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public double getLatitude () {
        return latitude;
    }

    public double getLongitude () {
        return longitude;
    }

    /*public Point getLocation() {
        return new Point(latitude, longitude);
    }*/

    public abstract boolean canAffordPacient(Incident i);
    public abstract void placePacient();
    public abstract void freePacient();
}
