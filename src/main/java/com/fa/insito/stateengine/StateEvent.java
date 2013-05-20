package com.fa.insito.stateengine;


public class StateEvent {

    private String token;
    private Object source;
    private Object context;

    public StateEvent(String token, Object source, Object context) {
        this.token = token;
        this.source = source;
        this.context = context;
    }

    public StateEvent(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public Object getSource() {
        return source;
    }

    public Object getContext() {
        return context;
    }

}
