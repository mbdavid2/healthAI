package entities;

public class Hospital extends Establishment {

    private int numBeds;
    private int freeBeds;

    public Hospital(int lat, int lon, int numBeds, int freeBeds) {
        super(lat, lon);
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
