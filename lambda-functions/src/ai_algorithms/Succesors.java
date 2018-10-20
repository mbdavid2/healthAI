package ai_algorithms;

import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bejar on 17/01/17
 */
public class Succesors implements SuccessorFunction {

    public List getSuccessors(Object state) {
        ArrayList retval = new ArrayList();
        State board = (State) state;

        // Some code here
        // (flip all the consecutive pairs of coins and generate new states
        // Add the states to retval as Succesor("flip i j", new_state)
        // new_state has to be a copy of state

        return retval;

    }

}
