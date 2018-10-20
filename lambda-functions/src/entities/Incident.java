package entities;

enum Level {
    red, green, blue, yellow, orange;
}

public class Incident {

    private int lat;
    private int lon;

    private Level gravity;

    public int getLat() {
        return lat;
    }

    public int getLon() {
        return lon;
    }
}
