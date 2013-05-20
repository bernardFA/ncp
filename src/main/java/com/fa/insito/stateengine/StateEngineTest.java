package com.fa.insito.stateengine;


import org.junit.Assert;
import org.junit.Test;

public class StateEngineTest {

    class TestEngine extends StateEngine {
        TestEngine() {
            super("state1",
                    new Transition("state1", "tr1", "state2"),
                    new Transition("state2", "tr2", "state3"));
        }
    }

    class TestEngineWithReflex extends TestEngine {

        void state2(StateEvent stateEvent) {
            System.out.println("calling state2 " + stateEvent.getToken());
            onToken(new StateEvent("tr2"));
        }
    }

    @Test
    public void initialState() {
        TestEngine testEngine = new TestEngine();
        Assert.assertEquals(testEngine.getCurrentState().getName(), "state1");
    }

    @Test
    public void transition() {
        TestEngine testEngine = new TestEngine();
        testEngine.onToken(new StateEvent("tr1"));
        Assert.assertEquals(testEngine.getCurrentState().getName(), "state2");
    }

    @Test
    public void notransition() {
        TestEngine testEngine = new TestEngine();
        testEngine.onToken(new StateEvent("fake"));
        Assert.assertEquals(testEngine.getCurrentState().getName(), "state1");
    }

    @Test
    public void reflex() {
        TestEngineWithReflex testEngine = new TestEngineWithReflex();
        testEngine.onToken(new StateEvent("tr1"));
        Assert.assertEquals(testEngine.getCurrentState().getName(), "state3");
    }

    public void observer() {
        final TestEngineWithReflex testEngine = new TestEngineWithReflex();
        testEngine.addStateChangeListener(new StateChangeListener() {
            @Override
            public void onState(State currentState, StateEvent stateEvent) {
                Assert.assertEquals(currentState, testEngine.getCurrentState());
            }
        });
        testEngine.onToken(new StateEvent("tr1"));

    }
}
