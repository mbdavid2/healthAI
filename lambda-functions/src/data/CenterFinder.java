package data;

import entities.Hospital;
import entities.MedicCenter;

import java.util.Arrays;
import java.util.List;

public abstract class CenterFinder {
    public static List<Hospital> findHospitals() {
        return Arrays.asList(
                new Hospital(
                        40.4644537, 0.4500352000000021, "Vinaròs", 10, 3),
                new Hospital(
                        39.67387220000001, -0.2317180999999664, "Sagundo", 6, 4),
                new Hospital(
                        40.002782, -0.04192799999998442, "Castellón", 20, 5)
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
