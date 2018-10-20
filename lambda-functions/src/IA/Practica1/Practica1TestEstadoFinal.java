package IA.Practica1;

import aima.search.framework.GoalTest;

public class Practica1TestEstadoFinal implements GoalTest {
    public boolean isGoalState(Object state) {
        return ((Practica1Estado) state).is_goal();
    }
}
