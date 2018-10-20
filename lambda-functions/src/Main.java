import ai_algorithms.*;

import entities.Hospital;
import entities.Incident;
import entities.MedicCenter;
import entities.Vehicle;

import testJSON.CreatorJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

public class Main {

    public static void main(String[] args) throws Exception {

        //Read contents from JSON file and store the information in the state
        CreatorJSON parser = new CreatorJSON("./lambda-functions/src/testJSON/input.json");

        ArrayList<Vehicle> vehicles = parser.getVehicles();
        ArrayList<Hospital> hospitals = parser.getHospitals();
        ArrayList<MedicCenter> medicCenters = parser.getMedicCenters();

        for (Vehicle v: vehicles) {
            v.setEstablishmentOrigin(hospitals, medicCenters);
        }

        State problemState = new State(vehicles, parser.getIncidents());

        problemState.hospitals = hospitals;
        problemState.medicCenters = medicCenters;

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
        SearchAgent agent = new SearchAgent(p, alg);

        // We print the results of the search
        System.out.println();
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());
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

}
