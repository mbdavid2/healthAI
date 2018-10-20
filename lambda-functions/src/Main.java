import ai_algorithms.State;

import entities.Incident;
import entities.Vehicle;
import testJSON.CreatorJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {

        //TODO: los news estos son provisionales
        State problemState = new State(new ArrayList<Vehicle>(), new ArrayList<Incident>());

        //Read contents from JSON file and store the information in the state
        CreatorJSON parser = new CreatorJSON("./lambda-functions/src/testJSON/input.json");

        problemState.hospitals = parser.getHospitals();
        problemState.medicCenters = parser.getMedicCenters();

        System.out.println(problemState.hospitals);
        System.out.println(problemState.medicCenters);

        /*State board = new State(prob, sol);

        // Create the Problem object
        Problem p = new Problem(board,
                new Succesors(),
                new Goal(),
                new Heuristic());

        // Instantiate the search algorithm
        // AStarSearch(new GraphSearch()) or IterativeDeepeningAStarSearch()
        Search alg = new AStarSearch(new GraphSearch());

        // Instantiate the SearchAgent object
        SearchAgent agent = new SearchAgent(p, alg);

        // We print the results of the search
        System.out.println();
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());

        // You can access also to the goal state using the
        // method getGoalState of class Search
*/
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
