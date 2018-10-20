package ai_algorithms;

import aima.search.framework.HeuristicFunction;

public class Heuristic implements HeuristicFunction {

    public int getHeuristicValue(Object n) {
        return ((State) n).heuristic();
    }
}
