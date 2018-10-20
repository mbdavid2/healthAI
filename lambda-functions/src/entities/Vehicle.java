package entities;


import java.awt.*;

public class Vehicle {
    private Type vehicleType;
    private Incident incident;
    private Establishment origin;
    private Establishment destination;

    public Vehicle(Type vehicleType, Establishment origin) {
        this.vehicleType = vehicleType;
        this.origin = origin;
    }

    public void setDestination(Establishment destination) {
        this.destination = destination;
    }

    public double getLatitudeOrigin() {
        return origin.getLatitude();
    }

    public double getLongitudeOrigin() {
        return origin.getLongitude();
    }


    public double getLatitudeDestination() {
        return destination.getLatitude();
    }

    public double getLongitudeDestination() {
        return destination.getLongitude();
    }

    public void assignIncident(Incident i) {
        this.incident = i;
    }

    public void assignDestination(Establishment e) { this.destination = e;}

    public void deattachIncident() {
        this.incident = null;
    }

    /*public double distance() {
        if (incident == null) return 0;
        return origin.getLocation().distance(incident.getLocation()) + incident.getLocation().distance(destination.getLocation());
    }*/

    public Establishment getDestination() {
        return destination;
    }

    public Incident getIncident() {
        return incident;
    }
}
