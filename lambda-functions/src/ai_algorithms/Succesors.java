package ai_algorithms;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import entities.Establishment;
import entities.Incident;
import entities.Vehicle;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.List;

public class Succesors implements SuccessorFunction {
    public List getSuccessors(Object o) {
        ArrayList<Successor> successors = new ArrayList<>();
        State state = (State) o;

        State copia = state.copy();
        for (Vehicle v : copia.unusedVehicles) {
            for (Incident i : copia.unservedIncidents) {
                for (Establishment e : copia.getEstablishments()) {
                    copia.assignVehicleToIncidentAndDestination(v,i,e);
                    successors.add(new Successor("ASSIGN VEHICLE: " + v + " to " + i.getLocation() + "  -> " + i, copia));
                    copia = state.copy();
                }
            }
        }

        for (Vehicle v : copia.usedVehicles) {
            for (Establishment e : copia.getEstablishments()) {
                copia.changeDestinationOfUsedIncident(v,e);
                successors.add(new Successor("CHANGE DESTINATION" + v + " to " + e.getLocation(), copia));
                copia = state.copy();
            }
        }


        for (Vehicle v1 : copia.usedVehicles) {
            for (Vehicle v2 : copia.usedVehicles) {
                if (v1 != v2) {
                    copia.swapIncidentOfTwoVehicles(v1, v2);
                    successors.add(new Successor("SWAP INCIDENTS" + v1 + " and " + v2, copia));
                    copia = state.copy();
                }
            }
        }

//        System.out.println(state.getEstablishments().size());
//        System.out.println(state.getHospitals().size());
//        System.out.println(state.getMedicCenters().size());
//        System.out.println(state.usedVehicles.size());
//        System.out.println(state.unusedVehicles.size());
//        System.out.println("=====================================================");
         System.out.println(state.heuristic());
//        for (Successor s: successors) {
//            System.out.println(" -->" + ((State) s.getState()).heuristic());
//        }

        return successors;

    }

}
