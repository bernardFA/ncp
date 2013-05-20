package com.fa.insito.poc2.columns;


import java.util.ArrayList;
import java.util.Collection;

abstract class Column<T> extends ArrayList<T> {

    protected String name;
    protected Formula formula;
    protected T initialiser;

    public Column(String name) {
        this.name = name;
    }

    public Column(String name, Collection<? extends T> collection) {
        this.name = name;
        addAll(collection);
    }

    public Column(String name, Formula<? extends T> formula) {
        this.name = name;
        this.formula = formula;
    }

    public Column(String name, Formula<T> formula, T initialiser) {
        this.name = name;
        this.formula = formula;
        this.initialiser = initialiser;
    }

    String getName() {
        return name;
    }

    Formula getFormula() {
        return formula;
    }

    T getInitialiser() {
        return initialiser;
    }

    public T getBefore(int index) {
        return index == 0 ? initialiser : get(index-1);
    }

    public Column<T> initialiseWith(T t) {
        initialiser = t;
        return this;
    }

    public Column<T> fillWith(int i, T t) {
        add(i, t);
        return this;
    }

    public Column<T> fillAllWith(int lines, T t) {
        for (int i=0; i<lines; i++)
            add(i, t);
        return this;
    }

    public T last() {
        return isEmpty() ? null : get(size()-1);
    }

    public T lastOrInitialiser() {
        return isEmpty() ? (initialiser == null ? null : initialiser) : get(size()-1);
    }

    public String print(int index) {
        return get(index) == null ? "null" : get(index).toString();
    }

}
