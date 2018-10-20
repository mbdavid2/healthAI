package data;

import entities.Hospital;
import entities.Incident;
import entities.MedicCenter;

import java.util.Arrays;
import java.util.List;

public abstract class IncidentFinder {
    public static List<Incident> findIncidents() {
        return Arrays.asList(
            new Incident(40.419347, 0.419117, 1),
            new Incident(40.4244537, 0.49003520000000021, 3),
            new Incident(40.3244537,0.440035200000021, 4),
            new Incident(40.3244537, 0.4400352000000021,4)
        );
    }

    public static List<MedicCenter> findMedicCenters() {
        return Arrays.asList(
                new MedicCenter(40.4126493, 0.42430130000002464, "Benicarló"),
                new MedicCenter( 40.3593167, 0.36542039999994813, "Peñíscola"),
                new MedicCenter(40.4654987, 0.17876569999998537, "Sant_Mateo"),
                new MedicCenter(40.5244341, 0.2895942999999761, "Traiguera"),
                new MedicCenter(40.356701,0.025070499999969797, "Albocàsser")
        );
    }
}
