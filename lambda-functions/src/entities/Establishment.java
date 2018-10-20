package entities;

import javax.json.Json;
import javax.json.JsonObject;
import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Establishment {

    private double latitude;

    private double longitude;

    private String name;

    private static int ID_COUNTER = 0;
    private int id;

    public Establishment(double lat, double lon, String name) {
        this.name = name;
        this.latitude = lat;
        this.longitude = lon;
        this.id = ID_COUNTER++;
    }

    public double getLatitude () {
        return latitude;
    }

    public double getLongitude () {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public Point2D.Double getLocation() {
        return new Point2D.Double(latitude, longitude);
    }

    public abstract boolean canAffordPacient(Incident i);
    public abstract void placePacient();
    public abstract void freePacient();

    public int getId() {
        return id;
    }

    public abstract int getNumBeds();
    public abstract int getFreeBeds();

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                        .add("position", Json.createObjectBuilder()
                                .add("lat", this.getLatitude())
                                .add("lng", this.getLongitude())
                                .build()
                        )
                        .add("type", this instanceof Hospital ? "hospital" : "medical_center")
                        .add("totalBeds", this.getNumBeds())
                        .add("freeBeds", this.getFreeBeds())
                        .build();
    }
}
