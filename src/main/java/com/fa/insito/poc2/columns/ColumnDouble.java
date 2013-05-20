package com.fa.insito.poc2.columns;

import java.util.Collection;


public class ColumnDouble extends Column<Double> {

    public ColumnDouble(String name) {
        super(name);
    }

    public ColumnDouble(String name, Collection<? extends Double> collection) {
        super(name, collection);
    }

    public ColumnDouble(String name, Formula<? extends Double> formula) {
        super(name, formula);
    }

    @Override
    public ColumnDouble fillWith(int lineIndex, Double aDouble) {
        super.fillWith(lineIndex, aDouble);
        return this;
    }

    @Override
    public ColumnDouble fillAllWith(int lines, Double aDouble) {
        super.fillAllWith(lines, aDouble);
        return this;
    }

    public ColumnDouble setFirst(Double aDouble) {
        super.add(0, aDouble);
        return this;
    }

    @Override
    public ColumnDouble initialiseWith(Double aDouble) {
        super.initialiseWith(aDouble);
        return this;
    }

    public double sum() {
        double res = 0;
        for (double aDouble : this)
            res += aDouble;
        return res;
    }
}
