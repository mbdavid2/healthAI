package entities;

import javax.json.Json;
import javax.json.JsonObject;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Objects;

public class Incident {

    private double latitude;
    private double longitude;

    // 1: important -> 5: not important
    private int gravity;

    private static int ID_COUNTER = 0;
    private int id;

    public Incident(double latitude, double longitude, int gravity) {
        this(latitude, longitude, gravity, ID_COUNTER++);
    }

    public Incident(double latitude, double longitude, int gravity, int id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.gravity = gravity;
        this.id = id;
    }

    public Incident copy() {
        return new Incident(latitude, longitude, gravity);
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

    public int getId() {
        return this.id;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("position", Json.createObjectBuilder()
                        .add("lat", this.getLatitude())
                        .add("lng", this.getLongitude())
                        .build()
                )
                .add("gravity", this.getGravity())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Incident incident = (Incident) o;
        return id == incident.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
