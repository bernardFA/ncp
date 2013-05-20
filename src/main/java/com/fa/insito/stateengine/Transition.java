package com.fa.insito.stateengine;


public class Transition {

    private State from;
    private String token;
    private State to;
    private String permission;

    public Transition(State from, String token, State to) {
        this.from = from;
        this.token = token;
        this.to = to;
    }

    public Transition(State from, String token, State to, String permission) {
        this(from, token, to);
        this.permission = permission;
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

    public String getPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return token;
    }
}
