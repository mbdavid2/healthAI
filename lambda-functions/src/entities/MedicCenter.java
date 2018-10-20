package entities;

public class MedicCenter extends Establishment {
    public MedicCenter(double lat, double lon) {
        super(lat, lon);
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
}
