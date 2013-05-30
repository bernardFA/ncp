package com.fa.insito.poc3;

import com.fa.insito.poc3.stateengine.State;
import com.fa.insito.poc3.stateengine.StateEngine;
import com.fa.insito.poc3.stateengine.Transition;

public class LiveCycleState extends StateEngine {

    // states
    private static final String ALIVE = "Alive";
    private static final String SIMULATED = "Simulated";
    private static final String DEAD = "Dead";
    private static final String HIBERNANT = "Hibernant";

    private static final String DESTROY = "Destroy";
    private static final String SIMULATE = "Simulate";
    private static final String HIBERN = "Hibern";
    private static final String GOLIVE = "Go live";

    LiveCycleState() {
        super(new State(ALIVE),
                new Transition(new State(ALIVE), DESTROY, new State(DEAD)),
                new Transition(new State(ALIVE), HIBERN, new State(HIBERNANT)),
                new Transition(new State(ALIVE), SIMULATE, new State(SIMULATED)),

                new Transition(new State(SIMULATED), GOLIVE, new State(ALIVE)),
                new Transition(new State(SIMULATED), HIBERN, new State(HIBERNANT)),
                new Transition(new State(SIMULATED), DESTROY, new State(DEAD)),

                new Transition(new State(HIBERNANT), GOLIVE, new State(ALIVE)),
                new Transition(new State(HIBERNANT), DESTROY, new State(DEAD)),
                new Transition(new State(HIBERNANT), SIMULATE, new State(SIMULATED)),

                new Transition(new State(DEAD), GOLIVE, new State(ALIVE)),
                new Transition(new State(DEAD), HIBERN, new State(HIBERNANT)),
                new Transition(new State(DEAD), SIMULATE, new State(SIMULATED)));
    }
}