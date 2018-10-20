package entities;

import java.util.ArrayList;

public class Establishment {

    private String name;

    private int lat;
    private int lon;

    private ArrayList<Vehicle> vechicles;

    public int getLat () {
        return lat;
    }

    public int getLon () {
        return lon;
    }
}
