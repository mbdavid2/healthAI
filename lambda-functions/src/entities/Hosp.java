package entities;

import java.util.ArrayList;

public class Hosp extends Establishment {

    private int freeBeds;

    public Hosp(int lat, int lon, ArrayList<Vehicle> vehicles, int freeBeds) {
        super(lat, lon, vehicles);
        this.freeBeds = freeBeds;
    }


    public int getFreeBeds() {
        return freeBeds;
    }
}
