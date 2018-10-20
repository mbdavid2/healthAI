package entities;

public class MedicCenter extends Establishment {
    public MedicCenter(double lat, double lon, String name) {
        super(lat, lon, name);
    }

    @Override
    public boolean canAffordPacient(Incident i) {
        return false;
    }

    @Override
    public void placePacient() {

    }

    @Override
    public void freePacient() {

    }

    @Override
    public int getNumBeds() {
        return 0;
    }

    @Override
    public int getFreeBeds() {
        return 0;
    }
}
