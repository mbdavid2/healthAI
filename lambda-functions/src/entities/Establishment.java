package entities;

import java.awt.*;
import java.util.ArrayList;

public class Establishment {
    private int latitude;
    private int longitude;

    private ArrayList<Vehicle> vehicles;

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
