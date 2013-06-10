package com.fa.insito.poc3.calcengine;

import com.fa.insito.poc3.MoneyMove;
import com.fa.insito.poc3.MoneyMoveSet;
import com.fa.insito.poc3.MoveItem;
import com.fa.insito.poc3.ProductSpec;
import com.google.common.collect.Sets;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.func.Reducer;
import org.apache.tapestry5.func.Worker;
import org.joda.time.DateMidnight;

import java.util.*;


public abstract class Sheet extends LinkedHashMap<String, Column> {

    public Sheet() {
    }

    public Sheet(Column... columns) {
        F.flow(columns).each(new Worker<Column>() {
            @Override
            public void work(Column column) {
                put(column.getName(), column);
            }
        });
    }

    private Sheet addColumns(Set<Column> columns) {
        F.flow(columns).each(new Worker<Column>() {
            @Override
            public void work(Column column) {
                put(column.getName(), column);
            }
        });
        return this;
    }

    private Sheet addColumn(Column column) {
        put(column.getName(), column);
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

    public ColumnInteger intColumn(String columnName) {
        return (ColumnInteger)get(columnName);
    }

    public ColumnDouble doubleColumn(String columnName) {
        return (ColumnDouble)get(columnName);
    }

    public ColumnDate dateColumn(String columnName) {
        return (ColumnDate)get(columnName);
    }

    public Column getColumn(String columnName) {
        return get(columnName);
    }

    public Sheet map(Mapper<Sheet, Sheet> mapper) {
        return mapper.map(this);
    }

    public <A> A reduce(Reducer<A, Sheet> reducer, A a) {
        return reducer.reduce(a, this);
    }

    public Sheet prepareColumn(Set<Column> columns) {
        addColumns(columns);
        return this;
    }

    public abstract Sheet prepareData(ProductSpec productSpec);

    /**
     * the calc engine at the sheet level
     */
    public Sheet calculate() {
        int index = 0;
        while (stopCondition()) {
            addLine(index);
            calculateLine(index);
            index++;
        }
        return this;
    }

    protected abstract boolean stopCondition();

    public Set<Column> extractColumn(List<String> output) {
        Set<Column> res = Sets.newHashSet();
        for (String colName : output)
            res.add(getColumn(colName));
        return res;
    }

    private void addLine(int index) {
        for (Column column : values())
            column.fillWith(index, null);
    }

    private void calculateLine(int index) {
        for (Column column : values()) {
            Formula formula = column.getFormula();
            if (formula != null) {
                formula.setContext(this, column, index);
                if (index==0)
                    column.set(0, formula.funcFirst());
                else
                    column.set(index, formula.func());
            }
        }
    }


    public MoneyMoveSet extractFlows(ProductSpec productSpec) {
        // TODO : rajouter les autres type de flux
        List<String> colNames = productSpec.get("PAYMENT");
        return extractPaymentFlows(dateColumn(colNames.get(0)), doubleColumn(colNames.get(1)), doubleColumn(colNames.get(2)), doubleColumn(colNames.get(3)));
    }

    public MoneyMoveSet extractPaymentFlows(ColumnDate paymentDates, ColumnDouble capitals, ColumnDouble interests, ColumnDouble fees) {
        MoneyMoveSet flows = new MoneyMoveSet();
        for (int line=0; line<paymentDates.size(); line++) {
            MoveItem moveItem = new MoveItem(capitals.get(line), interests.get(line), fees.get(line));
            flows.add(new MoneyMove(MoneyMove.Type.PAYMENT, paymentDates.get(line), Sets.newHashSet(moveItem)));
        }
        return flows;
    }

//    public int getMaxIndex() {
//        return F.flow(values()).reduce(new Reducer<Integer, Column>() {
//            @Override
//            public Integer reduce(Integer max, Column column) {
//                return column.size() > max ?  column.size() : max;
//            }
//        }, 0);
//    }



    public String print() {
        StringBuilder sb = new StringBuilder();
        for (Integer index : intColumn(INDEX)) {
            for (Column column : values()) {
                sb.append(column.print(index)).append('|');
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    */

}
