package com.fa.insito.poc3.calcengine;

import org.joda.time.DateMidnight;

import java.util.Collection;


public class ColumnDate extends Column<DateMidnight> {

    public ColumnDate(String name) {
        super(name);
    }

    public ColumnDate(String name, Formula<? extends DateMidnight> formula) {
        super(name, formula);
    }

    public ColumnDate(String name, Collection<? extends DateMidnight> collection) {
        super(name, collection);
    }

    @Override
    public ColumnDate fillAllWith(int lines, DateMidnight dateMidnight) {
        super.fillAllWith(lines, dateMidnight);
        return this;
    }

    public String print(int index) {
        return get(index) == null ? "null" : (get(index)).toString("yyyyMMdd");
    }

}
