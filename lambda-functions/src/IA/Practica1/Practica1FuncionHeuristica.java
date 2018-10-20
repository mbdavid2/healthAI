package IA.Practica1;

import aima.search.framework.HeuristicFunction;

public class Practica1FuncionHeuristica implements HeuristicFunction {
    public int getHeuristicValue(Object estado) {
        // Le metemos el menos porque queremos maximizar
        return -((Practica1Estado) estado).heuristic();
    }
}
