package com.fa.insito.stateengine;


public class Trans {

    private State from;
    private String token;
    private State to;

    public Trans(State from, String token, State to) {
        this.from = from;
        this.token = token;
        this.to = to;
    }

    public State getFrom() {
        return from;
    }

    public String getToken() {
        return token;
    }

    public State getTo() {
        return to;
    }
}
