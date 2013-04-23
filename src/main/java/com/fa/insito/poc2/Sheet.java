package com.fa.insito.poc2;


import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Ranges;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.func.Reducer;
import org.apache.tapestry5.func.Worker;
import org.joda.time.DateMidnight;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Sheet extends LinkedHashMap<String, Column> {

    public static final String INDEX = "Index";

    Map<String, Object> staticsDatas = new HashMap<String, Object>();

    Sheet(Column... columns) {
        F.flow(columns).each(new Worker<Column>() {
            @Override
            public void work(Column column) {
                put(column.getName(), column);
            }
        });
    }

    public Sheet withStatic(String name, Object data) {
        if (staticsDatas.get(name) != null)
            throw new IllegalArgumentException("the static data " + name + " already exist in the sheet");
        staticsDatas.put(name, data);
        return this;
    }

    public Sheet addIntColumn(String columnName, Formula<Integer> formula) {
        put(columnName, new ColumnInteger(columnName, formula));
        return this;
    }

    public Sheet addIntColumn(String columnName, Collection<? extends Integer> collection) {
        put(columnName, new ColumnInteger(columnName, collection));
        return this;
    }

    public Sheet addDateColumn(String columnName, Formula<DateMidnight> formula) {
        put(columnName, new ColumnDate(columnName, formula));
        return this;
    }

    public Sheet addDateColumn(String columnName, Collection<? extends DateMidnight> collection) {
        put(columnName, new ColumnDate(columnName, collection));
        return this;
    }

    public Sheet addDoubleColumn(String columnName, Formula<Double> formula) {
        put(columnName, new ColumnDouble(columnName, formula));
        return this;
    }

    public Sheet addDoubleColumn(String columnName, Collection<? extends Double> collection) {
        put(columnName, new ColumnDouble(columnName, collection));
        return this;
    }

    public ColumnInteger getColInteger(String columnName) {
        return (ColumnInteger)get(columnName);
    }

    public ColumnDouble getColDouble(String columnName) {
        return (ColumnDouble)get(columnName);
    }

    public ColumnDate getColDate(String columnName) {
        return (ColumnDate)get(columnName);
    }

    public int getMaxIndex() {
        return F.flow(values()).reduce(new Reducer<Integer, Column>() {
            @Override
            public Integer reduce(Integer max, Column column) {
                return column.size() > max ?  column.size() : max;
            }
        }, 0);
    }

    public Integer intOfStatic(String name) {
        return (Integer)staticsDatas.get(name);
    }

    public DateMidnight dateOfStatic(String name) {
        return (DateMidnight)staticsDatas.get(name);
    }

    public Double doubleOfstatic(String name) {
        return (Double)staticsDatas.get(name);
    }

    public Sheet map(Mapper<Sheet, Sheet> mapper) {
        return mapper.map(this);
    }

    public <A> A reduce(Reducer<A, Sheet> reducer, A a) {
        return reducer.reduce(a, this);
    }

    public Sheet run() {
        for (Column column : values()) {
            if (column.getFormula() != null) {
                column.fillAllWith(getColInteger(INDEX).size(), null);
            }
        }
        for (Integer index : getColInteger(INDEX)) {
            for (Column column : values()) {
                Formula formula = column.getFormula();
                if (formula != null) {
                    formula.setContext(Sheet.this, column, index);
                    if (index==0)
                        column.set(0, formula.funcFirst());
                    else
                        column.set(index, formula.func());
                }
                //System.out.println("Column: " + column.name + " index : " + index + " datas : " + column);
            }
            //System.out.println(print());
        }
        return this;
    }

    public Sheet addIndexColum(int nbLignes) {
        addIntColumn(INDEX, Ranges.closedOpen(0, nbLignes).asSet(DiscreteDomains.integers()));
        return this;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        for (Integer index : getColInteger(INDEX)) {
            for (Column column : values()) {
                sb.append(column.print(index)).append('|');
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
