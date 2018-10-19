package ai_algorithms;

/**
 * Created by bejar on 17/01/17.
 */

import aima.search.framework.HeuristicFunction;

public class ProbIA5HeuristicFunction implements HeuristicFunction {

    public int getHeuristicValue(Object n) {

        return ((ProbIA5Board) n).heuristic();
    }
}
