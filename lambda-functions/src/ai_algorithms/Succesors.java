package ai_algorithms;

import aima.search.framework.SuccessorFunction;
import entities.Establishment;
import entities.Incident;
import entities.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Succesors implements SuccessorFunction {
    public List getSuccessors(Object o) {
        ArrayList<State> successors = new ArrayList<>();
        State state = (State) o;

        for (Vehicle v : state.unusedVehicles) {
            for (Incident i : state.unservedIncidents) {
                for (Establishment e : state.getEstablishments()) {
                    State copia = state.copiar();
                    copia.assignVehicleToIncidentAndDestination(v,i,e);
                    successors.add(copia);
                }
            }
        }

        for (Vehicle v : state.usedVehicles) {
            for (Establishment e : state.getEstablishments()) {
                State copia = state.copiar();
                copia.changeDestinationOfUsedIncident(v,e);
                successors.add(copia);
            }
        }


        for (Vehicle v1 : state.usedVehicles) {
            for (Vehicle v2 : state.usedVehicles) {
                if (v1 != v2) {
                    State copia = state.copiar();
                    copia.swapIncidentOfTwoVehicles(v1, v2);
                    successors.add(copia);
                }
            }
        }

        return successors;

    }

}
