package ai_algorithms;

/**
 * Created by bejar on 17/01/17.
 */

import aima.search.framework.HeuristicFunction;

public class Heuristic implements HeuristicFunction {

    public int getHeuristicValue(Object n) {

        return ((State) n).heuristic();
    }
}
