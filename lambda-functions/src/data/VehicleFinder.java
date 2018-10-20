package data;

import entities.Type;
import entities.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class VehicleFinder {
    public static List<Vehicle> findVehicles() {
        return Arrays.asList(
                new Vehicle(Type.bravo, null),
                new Vehicle(Type.samu, null),
                new Vehicle(Type.tna, null),
                new Vehicle(Type.helicoptero, null));
    }
}
