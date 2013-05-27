package com.fa.insito.poc3;

import com.fa.insito.poc3.stateengine.State;
import com.fa.insito.poc3.stateengine.StateEngine;
import com.fa.insito.poc3.stateengine.Transition;

public class LiveCycleState extends StateEngine {

    // states
    private static final String ALIVE = "Alive";
    private static final String DEAD = "Dead";
    private static final String HIBERNANT = "Hibernant";

    private static final String DESTROY = "Destroy";
    private static final String RESUR = "Resur";
    private static final String HIBERN = "Hibern";
    private static final String AWAKE = "Awake";
    private static final String RESURTOHIBERN = "ResurToHibern";

    LiveCycleState() {
        super(new State(ALIVE),
                new Transition(new State(ALIVE), DESTROY, new State(DEAD)),
                new Transition(new State(ALIVE), HIBERN, new State(HIBERNANT)),
                new Transition(new State(HIBERNANT), AWAKE, new State(ALIVE)),
                new Transition(new State(HIBERNANT), DESTROY, new State(DEAD)),
                new Transition(new State(DEAD), RESUR, new State(ALIVE)),
                new Transition(new State(DEAD), RESURTOHIBERN, new State(HIBERNANT)));
    }
}