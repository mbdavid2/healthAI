package entities;

import java.awt.*;
import java.util.ArrayList;

public class Establishment {

    private int latitude;

    private int longitude;

    private ArrayList<Vehicle> vehicles;

    public Establishment(int lat, int lon, ArrayList<Vehicle> vehicles) {
        this.latitude = lat;
        this.longitude = lon;
        this.vehicles = vehicles;
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
}
