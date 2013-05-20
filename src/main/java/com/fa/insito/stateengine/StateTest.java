package com.fa.insito.stateengine;

import org.junit.Test;
import org.junit.Assert;

import static com.fa.insito.stateengine.StateEngine.Transition;

public class StateTest {

    class TestEngine extends StateEngine {
        TestEngine() {
            super("state1",
                    new Transition("state1", "tr1", "state2"),
                    new Transition("state1", "tr1", "state3"));
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void initialiseOK() {
        TestEngine testEngine = new TestEngine();
    }
}
