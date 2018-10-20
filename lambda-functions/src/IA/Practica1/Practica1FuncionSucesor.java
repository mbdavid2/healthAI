package IA.Practica1;

import Clases.Camion;
import Clases.Peticion;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static IA.Practica1.Practica1Estado.rand;

/**
 * Created by bejar on 17/01/17
 */
public class Practica1FuncionSucesor implements SuccessorFunction {

    public List getSuccessors(Object estado_) {
        ArrayList<Successor> retval = new ArrayList<>();
        Practica1Estado estado = ((Practica1Estado) estado_).copy();

        double probabilidad = rand.nextInt(1000) / 1000.0;
        //System.out.println(probabilidad);
        String mensaje;
        // anadirUnaPeticionYVolver
        if (probabilidad < 0.33) {
            mensaje = "operador 1 y no hace puta mierda";
            if (estado.getNumPeticionesPendientes() >= 1) {
                Camion camion = estado.getCamionRandom();
                Peticion peticionGasolinera = estado.getPeticionGasolineraRandom();
                Peticion peticionCentro = estado.getPeticionCentroRandom();
                estado.anadirUnaPeticionYVolver(camion, peticionGasolinera, peticionCentro);
                mensaje = "operador 1 - " + camion.toString();
            }
        } else if (probabilidad < 0.67) {
            mensaje = "operador 2";
            if (estado.getNumPeticionesPendientes() >= 2) {
                Camion camion = estado.getCamionRandom();
                Peticion peticionGasolinera1 = estado.getPeticionGasolineraRandom();
                Peticion peticionGasolinera2 = estado.getPeticionGasolineraRandom();
                Peticion peticionCentro = estado.getPeticionCentroRandom();
                estado.anadirDosPeticionesYVolver(camion, peticionGasolinera1, peticionGasolinera2, peticionCentro);
                mensaje = "operador 2 - " + camion.toString();
            }
        } else {
            Camion camion = estado.getCamionRandom();
            camion.eliminarUltimaSalidaDeCentro();
            mensaje = "operador 3 - " + camion.toString();
        }

        retval.add(new Successor(mensaje + " " + String.valueOf(estado.heuristic()), estado));

        // Nos aseguramos de que SOLO devolvemos
        // un nodo
        assert retval.size() == 1;
        return retval;

    }

}
