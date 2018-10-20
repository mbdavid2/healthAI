package entities;

enum Level {
    RED, GREEN, BLUE, YELLOW, ORANGE;
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
