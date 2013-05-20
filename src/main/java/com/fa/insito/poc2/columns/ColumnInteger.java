package com.fa.insito.poc2.columns;

import java.util.Collection;

class ColumnInteger extends Column<Integer> {

    public ColumnInteger(String name) {
        super(name);
    }

    public ColumnInteger(String name, Formula<Integer> formula) {
        super(name, formula);
    }

    public ColumnInteger(String name, Collection<? extends Integer> collection) {
        super(name, collection);
    }

    @Override
    public ColumnInteger fillAllWith(int lines, Integer integer) {
        super.fillAllWith(lines, integer);
        return this;
    }

}
