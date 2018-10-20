package entities;

public class Hospital extends Establishment {

    private int numBeds;
    private int freeBeds;

    public int getNumBeds() {
        return numBeds;
    }

    public Hospital(double lat, double lon, String name, int numBeds, int freeBeds) {
        super(lat, lon, name);
        this.freeBeds = freeBeds;
    }

    public int getFreeBeds() {
        return freeBeds;
    }

    @Override
    public boolean canAffordPacient(Incident i) {
        return freeBeds >= 0;
    }

    public void placePacient() {
        --freeBeds;
    }

    @Override
    public void freePacient() {
        ++freeBeds;
    }
}
