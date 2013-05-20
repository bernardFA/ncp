package com.fa.insito.stateengine;


import com.google.common.collect.Maps;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class State {

    private String name;
    private Map<String,State> nextStates = Maps.newHashMap();

    public State(String name) {
        this.name = name;
    }

    public void addTransition(Trans trans) {
        checkArgument(nextStates.get(trans.getToken()) == null, "State " + name + " have already a trans token " + trans.getToken());
        nextStates.put(trans.getToken(), trans.getTo());
    }

    public State getNextState(String token) {
        return nextStates.get(token);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
