package com.fa.insito.poc3.framework;

public interface CumulativeFunction<F, T> {

    void apply(F f, ResultSet<T> resultSet);

    public static interface ResultSet<T> {
        void add(T t);
    }
}
