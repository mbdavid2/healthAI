package entities;

import ai_algorithms.State;

import java.util.Objects;

public class Vehicle {
    private Type vehicleType;

    private static int ID_COUNTER = 0;
    private int id;

    public Type getVehicleType() {
        return vehicleType;
    }

    private Incident incident;
    private Establishment origin;
    private Establishment destination;

    public Vehicle(Type vehicleType, Establishment origin) {
        this(vehicleType, ID_COUNTER++, null, origin, null);
    }

    public void setOrigin(Establishment origin) {
        this.origin = origin;
    }

    private Vehicle(Type vehicleType, int id, Incident incident, Establishment origin, Establishment destination) {
        this.vehicleType = vehicleType;
        this.id = id;
        this.incident = incident;
        this.origin = origin;
        this.destination = destination;
    }

    public void setDestination(Establishment destination) {
        this.destination = destination;
    }

    public Vehicle copy(State state) {
        if(incident != null) {
            for(Incident next: state.getIncidents()) {
                if(next.getId() == incident.getId()) {
                    incident = next;
                    break;
                }
            }
        }
        if(origin != null) {
            for (Establishment next : state.getEstablishments()) {
                if (next.getId() == origin.getId()) {
                    origin = next;
                    break;
                }
            }
        }
        if(destination != null) {
            for (Establishment next : state.getEstablishments()) {
                if (next.getId() == destination.getId()) {
                    destination = next;
                    break;
                }
            }
        }
        return new Vehicle(vehicleType, id, incident, origin, destination);
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

    public double distance() {
        if (incident == null) return 0;
        return origin.getLocation().distance(incident.getLocation()) + incident.getLocation().distance(destination.getLocation());
    }

    public Establishment getDestination() {
        return destination;
    }

    public Incident getIncident() {
        return incident;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
