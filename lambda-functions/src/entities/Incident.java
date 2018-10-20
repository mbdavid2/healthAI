package entities;

enum Level {
    RED, GREEN, BLUE, YELLOW, ORANGE;
}


public class Incident {

    private int latitude;
    private int longitude;

    private Level gravity;

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }
}
