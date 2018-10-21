package data;

import entities.Hospital;
import entities.Incident;
import entities.MedicCenter;

import java.util.Arrays;
import java.util.List;

public abstract class IncidentFinder {
    public static List<Incident> findIncidents() {
        return Arrays.asList(
            new Incident(40.419347, 0.309117, 1),
            new Incident(40.4244537, 0.38003520000000021, 3),
            new Incident(40.3244537,0.330035200000021, 4),
            new Incident(40.3244537, 0.3300352000000021,5)
        );
    }
}
