package entities;


import java.awt.*;

public class Vehicle {
    private Type vehicleType;
    private Incident incident;
    private Point origin;
    private Point destination;

    public Vehicle(Type vehicleType, Point origin) {
        this.vehicleType = vehicleType;
        this.origin = origin;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public int getLatitudeOrigin() {
        return origin.x;
    }

    public int getLongitudeOrigin() {
        return origin.y;
    }


    public int getLatitudeDestination() {
        return destination.x;
    }

    public int getLongitudeDestination() {
        return destination.y;
    }

    public void assignIncident(Incident i) {
        this.incident = i;
    }

    public void deattachIncident() {
        this.incident = null;
    }

    public double distance() {
        if (incident == null) return 0;
        return origin.distance(incident.getLocation()) + incident.getLocation().distance(destination);
    }

}
