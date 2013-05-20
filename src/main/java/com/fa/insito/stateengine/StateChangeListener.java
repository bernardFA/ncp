package com.fa.insito.stateengine;


public interface StateChangeListener {

    void onState(State currentState, StateEvent stateEvent);
}
