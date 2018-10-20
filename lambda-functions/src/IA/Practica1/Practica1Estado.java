package IA.Practica1;

import Clases.Camion;
import Clases.Peticion;
import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;

import java.util.ArrayList;
import java.util.Random;

public class Practica1Estado {
    // Invariables
    private static Gasolineras gasolineras;
    private static CentrosDistribucion centrosDistribucion;
    private static long semilla;

    protected static Random rand = new Random(semilla);

    public static long getSemilla() {
        return semilla;
    }

    public static Gasolineras getGasolineras() {
        return gasolineras;
    }

    public static CentrosDistribucion getCentrosDistribucion() {
        return centrosDistribucion;
    }


    // Variables
    private ArrayList<Peticion> peticionesServidas;
    private ArrayList<Peticion> peticionesPendientes;
    private ArrayList<Camion> camiones;

    /* Constructor */

    // Constructor SOLO Para llamar desde el main
    public Practica1Estado(Gasolineras gasolineras, CentrosDistribucion centrosDistribucion, long semilla) {
        Practica1Estado.gasolineras = gasolineras;
        Practica1Estado.centrosDistribucion = centrosDistribucion;
        Practica1Estado.semilla = semilla;

        peticionesServidas = new ArrayList<>();
        peticionesPendientes = new ArrayList<>();
        camiones = new ArrayList<>();

        inicializarPeticiones();
        inicializarCamiones();
    }

    // Constructor para copias
    private Practica1Estado(ArrayList<Peticion> peticionesServidas, ArrayList<Peticion> peticionesPendientes, ArrayList<Camion> camiones) {
        this.peticionesServidas = peticionesServidas;
        this.peticionesPendientes = peticionesPendientes;
        this.camiones = camiones;
    }

    public ArrayList<Camion> getCamiones() {
        return camiones;
    }

    Camion getCamionRandom() {
        return camiones.get(rand.nextInt(camiones.size()));
    }

    Peticion getPeticionGasolineraRandom() {
        // Asegurate de que siempre tengo las mismas
        int cuantas = peticionesPendientes.size() + peticionesServidas.size();

        assert peticionesPendientes.size() > 0;

        Peticion peticion = peticionesPendientes.get(rand.nextInt(peticionesPendientes.size()));
        Peticion nueva = new Peticion(peticion.getCoordX(), peticion.getCoordY(), peticion.getNumDias(), peticion.isVaACentroDeDistribucion());
        peticionesPendientes.remove(peticion);
        peticionesServidas.add(nueva);

        assert cuantas == peticionesPendientes.size() + peticionesServidas.size();

        return nueva;
    }

    Peticion getPeticionCentroRandom() {
        Distribucion distribucion = centrosDistribucion.get(rand.nextInt(centrosDistribucion.size()));
        return new Peticion(distribucion.getCoordX(), distribucion.getCoordY(), 0, true);
    }


    /**
     * Insertar todas las peticiones de todas las
     * gasolineras a las peticiones pendientes
     */
    private void inicializarPeticiones() {
        for (Gasolinera gasolinera : gasolineras) {
            int coordX = gasolinera.getCoordX();
            int coordY = gasolinera.getCoordY();
            for (int peticion : gasolinera.getPeticiones()) {
                Peticion p = new Peticion(coordX, coordY, peticion);
                peticionesPendientes.add(p);
            }
        }
    }

    private void inicializarCamiones() {
        for (Distribucion distribucion : centrosDistribucion) {
            int coordX = distribucion.getCoordX();
            int coordY = distribucion.getCoordY();
            Camion camion = new Camion(coordX, coordY);
            camiones.add(camion);
        }
    }


    // Operadores

    /**
     * 1- Afegir una petició i & tornar al centre de distribució j.
     * 2- Afegir dues peticions i, i' diferents & tornar al centre de distribució j.
     * 3- Eliminar última sortida desde centre de distribució.
     */

    void anadirUnaPeticionYVolver(Camion camion, Peticion peticionGasolinera, Peticion peticionCentro) {
        if (camion.getNumPeticiones() <= 3 && camion.getNumTanques() >= 1) {
            camion.aniadirPeticion(peticionGasolinera);
            camion.gastarTanque();
            camion.aniadirPeticion(peticionCentro);
            camion.refill();
        }
    }

    void anadirDosPeticionesYVolver(Camion camion, Peticion peticionGasolinera1, Peticion peticionGasolinera2, Peticion peticionCentro) {
        if (camion.getNumPeticiones() <= 2 && camion.getNumTanques() == 2) {
            camion.aniadirPeticion(peticionGasolinera1);
            camion.gastarTanque();
            camion.aniadirPeticion(peticionGasolinera2);
            camion.gastarTanque();
            camion.aniadirPeticion(peticionCentro);
            camion.refill();
        }
    }

    void eliminarUltimaSalidaDeCentro(Camion camion) {
        camion.eliminarUltimaSalidaDeCentro();
    }

    int getNumPeticionesPendientes() {
        return peticionesPendientes.size();
    }




    /* Heuristic function */

    /**
     * La heuristica es basicamente la cantidad de dinero que tenemos
     * Esto es beneficio - gasto
     */
    int heuristic() {
        int ingresos = 0;
        int gastos = 0;

        for (Peticion peticion : peticionesServidas) {
            ingresos += peticion.evaluar();
        }
        for (Peticion peticion : peticionesPendientes) {
            ingresos += peticion.evaluarManana();
        }

        for (Camion camion : camiones) {
            gastos += 2 * camion.getNumKilometros();
        }

        return ingresos - gastos;
    }

    /* Goal test */
    boolean is_goal() {
        return true;
    }


    Practica1Estado copy() {
        return new Practica1Estado(peticionesPendientes, peticionesServidas, camiones);
    }

     /* auxiliary functions */

    // Some functions will be needed for creating a copy of the state

    /* ^^^^^ TO COMPLETE ^^^^^ */

}
