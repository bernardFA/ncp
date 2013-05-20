package com.fa.insito.stateengine;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class StateEngine {

    private Map<String,State> states = Maps.newHashMap();
    private List<Trans> transes = Lists.newArrayList();
    private State initialState;

    private State currentState;
    private List<StateChangeListener> stateChangeListeners = Lists.newArrayList();

    protected StateEngine (String initialStateName, Transition... transitions) {
        // create states and build the engine references
        for (Transition transition: transitions) {
            State from = states.get(transition.fromState);
            if (from == null) {
                from = new State(transition.fromState);
                states.put(from.getName(), from);
            }
            State to = states.get(transition.toState);
            if (to == null) {
                to = new State(transition.toState);
                states.put(to.getName(), to);
            }
            Trans trans = new Trans(from, transition.token, to);
            from.addTransition(trans);
        }
        // initialize the stateEngine
        initialState = states.get(initialStateName);
        checkArgument(initialState != null, "bad initial state : " + initialStateName + " doesnt exist");
        changeState(initialState, null);
    }

    protected synchronized void onToken(StateEvent stateEvent) {
        checkNotNull(stateEvent);
        checkNotNull(stateEvent.getToken());
        State nextState = currentState.getNextState(stateEvent.getToken());
        if (nextState  != null)
            changeState(nextState, stateEvent);
    }

    private void changeState(State nextState, StateEvent stateEvent) {
        currentState = nextState;
        postStateChangeReflex(stateEvent);
        publishStateChange(stateEvent);
    }

    private void publishStateChange(StateEvent stateEvent) {
        // (carefull about long callbacks)
        for (StateChangeListener stateChangeListener : stateChangeListeners)
            stateChangeListener.onState(currentState, stateEvent);
    }

    private void postStateChangeReflex(StateEvent stateEvent) {
        try {
            Method method = this.getClass().getDeclaredMethod(currentState.getName(), StateEvent.class);
            method.invoke(this, new Object[]{stateEvent});
        } catch (NoSuchMethodException e) {
            // do nothing
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized State getCurrentState() {
        return this.currentState;
    }

    public void addStateChangeListener(StateChangeListener stateChangeListener) {
        stateChangeListeners.add(stateChangeListener);
    }

    public static class Transition {

        String fromState;
        String toState;
        String token;

        public Transition(String fromState, String token, String toState) {
            this.token = token;
            this.fromState = fromState;
            this.toState = toState;
        }

    }
}