package ai_algorithms;

import entities.*;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;
import java.util.List;

public class State {
    public List<Hospital> hospitals;
    public List<MedicCenter> medicCenters;
    List<Vehicle> usedVehicles;
    List<Incident> servedIncidents;
    List<Vehicle> unusedVehicles;
    List<Incident> unservedIncidents;

    public State(List<Vehicle> vehicles, List<Incident> incident, List<Hospital> hospitals, List<MedicCenter> medicCenters) {
        this(new ArrayList<>(), new ArrayList<>(), vehicles, incident, hospitals, medicCenters);
    }

    public State(List<Vehicle> usedVehicles, List<Incident> servedIncidents, List<Vehicle> unusedVehicles, List<Incident> unservedIncidents, List<Hospital> hospitals, List<MedicCenter> medicCenters) {
        this.usedVehicles = new ArrayList<>(usedVehicles);
        this.servedIncidents = new ArrayList<>(servedIncidents);
        this.unusedVehicles = new ArrayList<>(unusedVehicles);
        this.unservedIncidents = new ArrayList<>(unservedIncidents);
        this.hospitals = new ArrayList<>(hospitals);
        this.medicCenters = new ArrayList<>(medicCenters);
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public List<MedicCenter> getMedicCenters() {
        return medicCenters;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public void setMedicCenters(List<MedicCenter> medicCenters) {
        this.medicCenters = medicCenters;
    }

    public State copy() {
        ArrayList<Vehicle> copiaUsed = new ArrayList<>();
        for(Vehicle vehicle: usedVehicles) {
            copiaUsed.add(vehicle.copy());
        }
        ArrayList<Vehicle> copiaUnused = new ArrayList<>();
        for(Vehicle vehicle: unusedVehicles) {
            copiaUnused.add(vehicle.copy());
        }
        ArrayList<Incident> copiaServed = new ArrayList<>();
        for(Incident incident: servedIncidents) {
            copiaServed.add(incident.copy());
        }
        ArrayList<Incident> copiaUnserved = new ArrayList<>();
        for(Incident incident: unservedIncidents) {
            copiaUnserved.add(incident.copy());
        }
        return new State(copiaUsed, copiaServed, copiaUnused, copiaUnserved, hospitals, medicCenters);
    }

    private int number_of_vehicles() {
        return unusedVehicles.size() + usedVehicles.size();
    }

    private int number_of_incidents() {
        return servedIncidents.size() + unservedIncidents.size();
    }

    public List<Establishment> getEstablishments() {
        ArrayList<Establishment> t = new ArrayList<>(medicCenters);
        t.addAll(hospitals);
        return t;
    }

    public List<Incident> getIncidents() {
        ArrayList<Incident> t = new ArrayList<>(unservedIncidents);
        t.addAll(servedIncidents);
        return t;
    }

    public void assignVehicleToIncidentAndDestination(Vehicle v, Incident i, Establishment e) {
        assert (unusedVehicles.contains(v));
        assert (unservedIncidents.contains(i));
        if (e.canAffordPacient(i)) {
            //System.out.println(v + " ha entrat amb " + i);
            unusedVehicles.remove(v);
            usedVehicles.add(v);
            unservedIncidents.remove(i);
            servedIncidents.add(i);
            e.placePacient();
            v.assignIncident(i);
            v.assignDestination(e);
        }
    }

    public void changeDestinationOfUsedIncident(Vehicle v, Establishment e) {
        assert (usedVehicles.contains(v));
        if (e.canAffordPacient(v.getIncident())) {
            v.getDestination().freePacient();
            v.setDestination(e);
            e.placePacient();
        }
    }

    public void swapIncidentOfTwoVehicles(Vehicle v1, Vehicle v2) {
        assert (v1 != v2);
        Incident incident1 = v1.getIncident();
        Incident incident2 = v2.getIncident();
        v1.assignIncident(incident2);
        v2.assignIncident(incident1);
    }

    /* Heuristic function */
    public int heuristic() {
        double a = incidentsHeuristic();
        double b = kmVehiclesHeuristic();
        double c = mobilizedVehiclesHeuristic();
        double res = 3 * a - b - c;
        return (int) (10000. * res);

    }

    private double mobilizedVehiclesHeuristic() {
        return ((double) usedVehicles.size() / (double) number_of_vehicles() + 25.) / 30.;
    }

    private double kmVehiclesHeuristic() {
        double t = 0;
        for (Vehicle v : usedVehicles) {
            t += v.distance();
        }
        return t / ((double) number_of_vehicles() * 100.);
    }

    private double incidentsHeuristic() {
        int t = 0;
        for (Incident i : servedIncidents) {
            t += 6 - i.getGravity();
        }

        for (Incident i : unservedIncidents) {
            int x = 6 - i.getGravity();
            t -= Math.pow(x, 1.5);
        }

        return ((double) t / 8.0 * (double) number_of_incidents()) + 0.5;
    }

    public String toJsonStr() {
        JsonObjectBuilder vehicles = Json.createObjectBuilder();
        for (Vehicle v : usedVehicles) {
            vehicles.add("V-" + v.getId(), v.getVehicleType().toString());
        }
        for (Vehicle v : unusedVehicles) {
            vehicles.add("V-" + v.getId(), v.getVehicleType().toString());
        }

        JsonObjectBuilder centers = Json.createObjectBuilder();
        for (Establishment c : getEstablishments()) {
            centers.add("C-" + c.getId(), c.toJson());
        }


        JsonObjectBuilder incidences = Json.createObjectBuilder();
        for (Incident i : getIncidents()) {
            incidences.add(
                    "I-" + i.getId(),
                    Json.createObjectBuilder()
                            .add("position", Json.createObjectBuilder()
                                    .add("lat", i.getLatitude())
                                    .add("lng", i.getLongitude())
                                    .build()
                            )
                            .add("gravity", i.getGravity())
                            .build()
            );
        }

        JsonArrayBuilder routes = Json.createArrayBuilder();
        for (Vehicle v : usedVehicles) {
            routes.add(Json.createObjectBuilder()
                    .add("vehicle", "V-" + v.getId())
                    .add("origin", Json.createObjectBuilder()
                            .add("lat", v.getLatitudeOrigin())
                            .add("lng", v.getLongitudeOrigin())
                            .build()
                    )
                    .add("destination", Json.createObjectBuilder()
                            .add("lat", v.getLatitudeDestination())
                            .add("lng", v.getLongitudeDestination())
                            .build()
                    )
                    .add("km", v.distance())
                    .add("incidence", "I-" + v.getIncident().getId())
                    .build()
            );
        }


        JsonObject stateJson = Json.createObjectBuilder()
                .add("vehicles", vehicles.build())
                .add("centers", centers.build())
                .add("incidences", incidences.build())
                .add("routes", routes.build())
                .build();
        return stateJson.toString();
    }
}
