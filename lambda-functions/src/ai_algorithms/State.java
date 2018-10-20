package ai_algorithms;

import entities.Hospital;
import entities.Incident;
import entities.MedicCenter;
import entities.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bejar on 17/01/17.
 */
public class State {
    /* Class independent from AIMA classes
       - It has to implement the state of the problem and its operators
     *
    */

    private static List<Hospital> hospitals;
    private static List<MedicCenter> medicCenters;
    private final List<Vehicle> usedVehicles = new ArrayList<>();
    private final List<Incident> servedIncidents = new ArrayList<>();
    private final int NUMBER_OF_VEHICLES;
    private final int NUMBER_OF_INCIDENTS;
    private List<Vehicle> unusedVehicles;
    private List<Incident> unservedIncidents;

    public State(List<Incident> incidents, List<Hospital> hospitals, List<MedicCenter> medicCenters, List<Vehicle> vehicles) {
        this.unservedIncidents = new ArrayList<>(incidents);
        State.hospitals = new ArrayList<>(hospitals);
        State.medicCenters = new ArrayList<>(medicCenters);
        this.unusedVehicles = new ArrayList<>(vehicles);
        NUMBER_OF_VEHICLES = unusedVehicles.size();
        NUMBER_OF_INCIDENTS = unservedIncidents.size();
    }


    /* Heuristic function */
    public int heuristic() {
        double a = incidentsHeuristic();
        double b = kmVehiclesHeuristic();
        double c = mobilizedVehiclesHeuristic();
        return (int) (10000.d * (3 * a + b - c));

    }

    private double mobilizedVehiclesHeuristic() {
        return ((double) usedVehicles.size() / (double) NUMBER_OF_VEHICLES + 25.) / 30.;
    }

    private double kmVehiclesHeuristic() {
        double t = 0;
        for (Vehicle v : usedVehicles) {
            t += v.distance();
        }
        return t / ((double) NUMBER_OF_VEHICLES * 100.);
    }

    private double incidentsHeuristic() {
        int t = 0;
        for (Incident i : servedIncidents) {
            t += 6 - i.getGravity();
        }

        for (Incident i : unservedIncidents) {
            int x = 6 - i.getGravity();
            t -= x * x;
        }

        return ((double) t / (double) NUMBER_OF_INCIDENTS + 25.) / 30.;

    }

    /* Goal test */
    public boolean is_goal() {
        return false;
    }
}
