package com.fa.insito.poc3.framework;

public interface UnitOfWork<T> {

    T execute();

}
