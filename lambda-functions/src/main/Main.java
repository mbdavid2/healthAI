package main;

import ai_algorithms.*;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import data.CenterFinder;
import data.IncidentFinder;
import data.VehicleFinder;
import entities.Hospital;
import entities.MedicCenter;
import entities.Vehicle;

import java.util.*;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

public class Main implements RequestHandler<Map<String, Object>, String> {

    public static void main(String[] args) throws Exception {
        System.out.println((new Main()).handleRequest(new HashMap<>(), null));
    }

    private static void printInstrumentation(Properties properties) {
        for (Object o : properties.keySet()) {
            String key = (String) o;
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for (Object action : actions) {
            System.out.println(action);
        }
    }

    @Override
    public String handleRequest(Map<String, Object> in, Context context) {
        List<Vehicle> vehicles = VehicleFinder.findVehicles();
        List<Hospital> hospitals = CenterFinder.findHospitals();
        List<MedicCenter> medicCenters = CenterFinder.findMedicCenters();

        Random r = new Random();
        for (Vehicle v: vehicles) {
//            System.out.println(v.getVehicleType());
            if(r.nextBoolean()) {
                // random hospital
                v.setOrigin(hospitals.get(r.nextInt(hospitals.size())));
            } else {
                // random medic center
                v.setOrigin(medicCenters.get(r.nextInt(medicCenters.size())));
            }
        }

        State problemState = new State(vehicles, IncidentFinder.findIncidents(), hospitals, medicCenters);

        /*System.out.println(problemState.hospitals);
        System.out.println(problemState.medicCenters);
        System.out.println(parser.getVehicles());*/

        // Create the Problem
        Problem p = new Problem(problemState,
                new Succesors(),
                new Goal(),
                new Heuristic());

        // Instantiate the search algorithm
        Search alg = new HillClimbingSearch();

        // Instantiate the SearchAgent object
        SearchAgent agent = null;
        try {
             agent = new SearchAgent(p, alg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // We print the results of the search
        // System.out.println();
        // printActions(agent.getActions());
        // printInstrumentation(agent.getInstrumentation());

        State goalState = (State) alg.getGoalState();

        // System.out.println(goalState.toJsonStr());

        return goalState.toJsonStr();
    }
}
