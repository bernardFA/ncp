package com.fa.insito.poc3.framework;

public class Holder<T> {

    private T holdValue;

    public Holder(T holdValue) {
        this.holdValue = holdValue;
    }

    public Holder() {
    }

    public T get() {
        return holdValue;
    }

    public void put(T value) {
        this.holdValue = value;
    }
}
