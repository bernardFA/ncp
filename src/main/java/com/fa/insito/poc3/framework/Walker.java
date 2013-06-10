package com.fa.insito.poc3.framework;

public interface Walker<T> {

    public enum ProceedOption {
        STOP, CONTINUE
    }

    ProceedOption walkThrough(T target);
}
