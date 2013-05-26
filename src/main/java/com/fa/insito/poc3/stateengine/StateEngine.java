package com.fa.insito.poc3.stateengine;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class StateEngine {

    private Map<String,State> states = Maps.newHashMap();
    private State initialState;
    private State currentState;
    private List<StateChangeListener> stateChangeListeners = Lists.newArrayList();

    public StateEngine (State initialState, Transition... transitions) {
        // reference states and build the engine references
        checkNotNull(transitions);
        for (Transition transition: transitions) {
            State from = states.get(transition.getFrom().getName());
            if (from == null) {
                from = transition.getFrom();
                states.put(from.getName(), from);
            }
            from.addTransition(transition);
            State to = states.get(transition.getTo().getName());
            if (to == null) {
                to = transition.getTo();
                states.put(to.getName(), to);
            }
        }
        // initialize the stateEngine
        checkNotNull(initialState);
        checkArgument(states.get(initialState.getName()) != null, "bad initial state : " + initialState + " doesnt exist in this stateengine");
        this.initialState = initialState;
    }

    public void init() {
        changeState(initialState, null);
    }

    public synchronized void onToken(String token) {
        onToken(new StateEvent(token));
    }

    protected synchronized void onToken(StateEvent stateEvent) {
        checkNotNull(stateEvent);
        checkNotNull(stateEvent.getToken());
        State nextState = currentState.getNextState(stateEvent.getToken());
        if (nextState != null)
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

    public Collection<Transition> getPossibleTransitions() {
        return getCurrentState().getNextTransitions();
    }

    public void forceState(String stateName) {
        State state2Force = states.get(stateName);
        if (state2Force == null)
            throw new IllegalArgumentException("state " + stateName + " doesn't exist");
        currentState = state2Force;
    }

}