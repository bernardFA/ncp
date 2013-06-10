package com.fa.insito.poc3.sandbox;

import com.fa.insito.poc3.stateengine.State;
import com.fa.insito.poc3.stateengine.StateEngine;
import com.fa.insito.poc3.stateengine.Transition;

public class LiveCycleState extends StateEngine {

    // states
    private static final String DRAFT = "Draft";
    private static final String ALIVE = "Alive";
    private static final String SIMULATED = "Simulated";
    private static final String DEAD = "Dead";

    private static final String DESTROY = "Destroy";
    private static final String SIMULATE = "Simulate";
    private static final String GODRAFT = "Draft";
    private static final String GOLIVE = "Go live";

    public LiveCycleState() {
        super(new State(DRAFT),
                new Transition(new State(DRAFT), GOLIVE, new State(ALIVE)),
                new Transition(new State(DRAFT), DESTROY, new State(DEAD)),
                new Transition(new State(DRAFT), SIMULATE, new State(SIMULATED)),

                new Transition(new State(ALIVE), DESTROY, new State(DEAD)),
                new Transition(new State(ALIVE), GODRAFT, new State(DRAFT)),
                new Transition(new State(ALIVE), SIMULATE, new State(SIMULATED)),

                new Transition(new State(SIMULATED), GOLIVE, new State(ALIVE)),
                new Transition(new State(SIMULATED), GODRAFT, new State(DRAFT)),
                new Transition(new State(SIMULATED), DESTROY, new State(DEAD)),

                new Transition(new State(DEAD), GOLIVE, new State(ALIVE)),
                new Transition(new State(DEAD), GODRAFT, new State(DRAFT)),
                new Transition(new State(DEAD), SIMULATE, new State(SIMULATED)));
    }
}