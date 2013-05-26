package com.fa.insito.poc3.stateengine;


public interface StateChangeListener {

    void onState(State currentState, StateEvent stateEvent);
}
