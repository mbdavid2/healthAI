package entities;

import java.awt.*;

public abstract class Establishment {

    private int latitude;

    private int longitude;

    public Establishment(int lat, int lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public int getLatitude () {
        return latitude;
    }

    public int getLongitude () {
        return longitude;
    }

    public Point getLocation() {
        return new Point(latitude, longitude);
    }

    public abstract boolean canAffordPacient(Incident i);
    public abstract void placePacient();
    public abstract void freePacient();
}
