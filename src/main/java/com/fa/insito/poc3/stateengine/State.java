package com.fa.insito.poc3.stateengine;


import com.google.common.collect.Maps;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Predicate;

import java.util.Collection;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class State {

    private String name;
    private Map<String,Transition> transitions = Maps.newHashMap(); // key = transition's token

    public State(String name) {
        this.name = name;
    }

    public void addTransition(Transition transition) {
        checkArgument(transitions.get(transition.getToken()) == null,
            "State " + name + " have already a transition token " + transition.getToken());
        transitions.put(transition.getToken(), transition);
    }

    public State getNextState(String token) {
        Transition trans = transitions.get(token);
        if (trans == null)
            return null;
        if (trans.getPermission() != null) {
            // TODO : add security here
            // "module:domain:nesteddomain:action"
        }
        return trans.getTo();
    }

    public Collection<Transition> getNextTransitions() {
        return F.flow(transitions.values()).filter(new Predicate<Transition>() {
            @Override
            public boolean accept(Transition element) {
                // TODO : add security here
                // "module:domain:nesteddomain:action"
                return true;
            }
        }).toSet();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
