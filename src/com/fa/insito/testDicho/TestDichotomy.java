package com.fa.insito.testDicho;


import com.google.common.collect.FluentIterable;
import com.google.common.collect.Ranges;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.func.Reducer;
import org.apache.tapestry5.func.Worker;
import org.jfin.date.daycount.DaycountCalculator;
import org.jfin.date.daycount.DaycountCalculatorFactory;
import org.joda.time.DateMidnight;

import java.util.*;

import static com.google.common.collect.DiscreteDomains.integers;
import static java.lang.Math.abs;

public class TestDichotomy {

    public static void main(String[] args) {
        new TestDichotomy().assemblage(
                PaymentType.IN_ARREARS,
                20,
                new DateMidnight(2013, 1, 1),
                4,
                DaycountCalculatorFactory.newInstance().getEU30360(),
                25000d,
                1250d,
                2000d);

    }

    private static final double delta = 10e-7d;

    ColumnGroup assemblage(final PaymentType paymentType,
                    final int numberOfPeriods,
                    final DateMidnight startDate,
                    final int payFrequency,
                    final DaycountCalculator daycountCalculator,
                    final double initialAmount,
                    final double rent,
                    final double residualValue) {

        final ColumnInteger colIndex = new ColumnInteger(ColumnType.PeriodIndex,
               //F.series(0, numberOfPeriods).toList());
               Ranges.closedOpen(0, numberOfPeriods).asSet(integers()));

        final ColumnDate colInterestStart = new ColumnDate(ColumnType.InterestStart,
                F.flow(colIndex).map(new Mapper<Integer, DateMidnight>() {
                    @Override
                    public DateMidnight map(Integer periodIndex) {
                        return startDate.plusMonths(periodIndex * (12 / payFrequency));
                    }
                }).toList());

        final ColumnDate colInterestEnd = new ColumnDate(ColumnType.InterestEnd,
                F.flow(colIndex).map(new Mapper<Integer, DateMidnight>() {
                    @Override
                    public DateMidnight map(Integer periodIndex) {
                        return startDate.plusMonths((periodIndex+1) * (12 / payFrequency));
                    }
                }).toList());

        final ColumnDouble colInterestYearFraction = new ColumnDouble(ColumnType.InterestYearFraction,
                F.flow(colIndex).map(new Mapper<Integer, Double>() {
                    @Override
                    public Double map(Integer index) {
                        return daycountCalculator.calculateDaycountFraction(
                                colInterestStart.get(index).toGregorianCalendar(),
                                colInterestEnd.get(index).toGregorianCalendar());
                    }
                }).toList());

        final ColumnDate colPaymentDate = new ColumnDate(ColumnType.PaymentDate,
                F.flow(colIndex).map(new Mapper<Integer, DateMidnight>() {
                    @Override
                    public DateMidnight map(Integer index) {
                        return paymentType.paymentDate(colInterestStart.get(index), colInterestEnd.get(index));
                    }
                }).toList());

        final ColumnDouble colPayment = new ColumnDouble(ColumnType.Payment).fillAllWith(numberOfPeriods, rent);

        final ColumnGroup colStatics = new ColumnGroup(
                colIndex,
                colInterestYearFraction,
                colPaymentDate,
                colPayment,
                new ColumnDouble(ColumnType.Outstanding),
                new ColumnDouble(ColumnType.CRD).initialiseWith(initialAmount));

        Mapper<Double, Double> leasingCalcul = new Mapper<Double, Double>() {
            @Override
            public Double map(Double rate) {
                return colStatics.putColumn(new ColumnDouble(ColumnType.Rate).fillAllWith(numberOfPeriods, rate))
                        .map(new LeasingCalculator())
                        .reduce(new ZeroReducer(residualValue), initialAmount);
            }
        };
        double rate = dichotomy(0.0d, 1.0d, 30, leasingCalcul);

        return colStatics.putColumn(new ColumnDouble(ColumnType.Rate).fillAllWith(numberOfPeriods, rate));
    }

    double dichotomy(double up, double down, int nbIter, Mapper<Double, Double> function) {
        double mid = up*2; // crazy value
        for (int i=0; i<nbIter; i++) {
            mid = (up+down)/2;
            double zeroTarget = function.map(mid);
            if (zeroTarget > 0)
                up = mid;
            else
                down = mid;
            System.out.println(mid);
        }
        return mid;
    }


//InterestPeriod.is(Cal.YearFraction(InterestStart, InterestEnd))
//
//tant que sum(Capital + ResidualValue) = initialAmount
//
//Outstanding = Last(CRD) == Null ? Last(CRD) : initialAmount
//Interest = Outstanding x InterestPeriod x Rate
//Capital = Payment - Interest
//CRD = Outstanding - Capital

class ZeroReducer implements Reducer<Double, ColumnGroup> {

    double residualValue;

    ZeroReducer(double residualValue) {
        this.residualValue = residualValue;
    }

    @Override
    public Double reduce(Double initialAmount, ColumnGroup columnGroup) {
        return initialAmount - (columnGroup.getColDouble(ColumnType.Capital).sum() + residualValue);
    }

};

class LeasingCalculator implements Mapper<ColumnGroup, ColumnGroup> {

    public ColumnGroup map(ColumnGroup cols) {

            // statics cols
            final ColumnInteger colIndex = cols.getColInteger(ColumnType.PeriodIndex);
            final ColumnDouble colInterestYearFraction = cols.getColDouble(ColumnType.InterestYearFraction);
            final ColumnDate colPaymentDate = cols.getColDate(ColumnType.PaymentDate);
            final ColumnDouble colOutstanding = cols.getColDouble(ColumnType.Outstanding);
            final ColumnDouble colRate = cols.getColDouble(ColumnType.Rate);
            final ColumnDouble colPayment = cols.getColDouble(ColumnType.Payment);
            final ColumnDouble colCRD = cols.getColDouble(ColumnType.CRD);

            // results cols
            final ColumnDouble colCapital = new ColumnDouble(ColumnType.Capital);
            final ColumnDouble colInterest = new ColumnDouble(ColumnType.Interest);


            F.flow(colIndex).each(new Worker<Integer>() {
                @Override
                public void work(Integer lineIdx) {
                    colOutstanding.fillWith(lineIdx, colCRD.getBefore(lineIdx));
                    colInterest.fillWith(lineIdx, colOutstanding.get(lineIdx) * colInterestYearFraction.get(lineIdx) * colRate.get(lineIdx));
                    colCapital.fillWith(lineIdx, colPayment.get(lineIdx) - colInterest.get(lineIdx));
                    colCRD.fillWith(lineIdx, colOutstanding.get(lineIdx) - colCapital.get(lineIdx));

                    //System.out.format("%tD %f %f %f%n", colPaymentDate.get(lineIdx).toDate(), colCapital.get(lineIdx), colInterest.get(lineIdx), colCRD.get(lineIdx));
                }
            });
            // System.out.println("somme capital : " + colCapital.sum());
            return new ColumnGroup(colPaymentDate, colPayment, colCapital, colInterest, colCRD, colRate);

        }
    }


}




enum PaymentType {

    IN_ADVANCE {
        @Override
        DateMidnight paymentDate(DateMidnight start, DateMidnight end) {
            return start;
        }
    },
    IN_ARREARS {
        @Override
        DateMidnight paymentDate(DateMidnight start, DateMidnight end) {
            return end;
        }
    };

    abstract DateMidnight paymentDate(DateMidnight start, DateMidnight end);
}


enum ColumnType{
    PeriodIndex,
    InterestBound,
    Interval,
    InterestStart,
    InterestEnd,
    InterestYearFraction,
    PaymentDate,
    Payment,
    InterestPeriod,
    Outstanding,
    Rent,
    Rate,
    ForcedPayment,
    Capital,
    CRD,
    Interest

}


class Column<T> extends ArrayList<T> {

    ColumnType columnType;
    T initialiser;
    Formula<T> formula;

    Column(ColumnType columnType) {
        this.columnType = columnType;
    }

    Column(Collection<? extends T> collection, ColumnType columnType) {
        super(collection);
        this.columnType = columnType;
    }

    public T getBefore(int index) {
        if (index == 0)
            return initialiser;
        else
            return get(index-1);
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

    private void setFormula(Formula<T> formula) {
        this.formula = formula;
    }
}

interface Formula<T> {
    T func();
}


class ColumnDate extends Column<DateMidnight> {

    ColumnDate(ColumnType columnType) {
        super(columnType);
    }

    ColumnDate(ColumnType columnType, Collection<? extends DateMidnight> collection) {
        super(collection, columnType);
    }

    ColumnDate(ColumnType columnType, FluentIterable<DateMidnight> dates) {
        super(columnType);
        addAll(dates.toImmutableList());
    }

    @Override
    public ColumnDate fillAllWith(int lines, DateMidnight dateMidnight) {
        super.fillAllWith(lines, dateMidnight);
        return this;
    }
}

class ColumnDouble extends Column<Double> {

    ColumnDouble(ColumnType columnType) {
        super(columnType);
    }

    ColumnDouble(ColumnType columnType, Collection<? extends Double> collection) {
        super(collection, columnType);
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
        for (double dble : this)
            res += dble;
        return res;
    }
}

class ColumnInteger extends Column<Integer> {

    ColumnInteger(ColumnType columnType) {
        super(columnType);
    }

    ColumnInteger(ColumnType columnType, Collection<? extends Integer> collection) {
        super(collection, columnType);
    }

    @Override
    public ColumnInteger fillAllWith(int lines, Integer integer) {
        super.fillAllWith(lines, integer);
        return this;
    }
}

class ColumnGroup extends HashMap<ColumnType, Column> {

    ColumnGroup(Column... columns) {
        F.flow(columns).each(new Worker<Column>() {
            @Override
            public void work(Column column) {
                put(column.columnType, column);
            }
        });
    }

    public ColumnGroup putColumn(Column column) {
        put(column.columnType, column);
        return this;
    }

    public ColumnInteger getColInteger(ColumnType columnType) {
        return (ColumnInteger)get(columnType);
    }

    public ColumnDouble getColDouble(ColumnType columnType) {
        return (ColumnDouble)get(columnType);
    }

    public ColumnDate getColDate(ColumnType columnType) {
        return (ColumnDate)get(columnType);
    }

    public ColumnGroup map(Mapper<ColumnGroup, ColumnGroup> mapper) {
        return mapper.map(this);
    }

    public <A> A reduce(Reducer<A, ColumnGroup> reducer, A a) {
        return reducer.reduce(a, this);
    }
}

//
//class ColumnInterval extends Column<Interval> {
//    ColumnInterval(ColumnType columnType) {
//        super(columnType);
//    }
//
//    ColumnInterval(ColumnType columnType, Collection<? extends Interval> collection) {
//        super(collection, columnType);
//    }
//}
//
//class FixedRent extends ColumnDouble {
//
//    Double rent;
//
//    FixedRent(Double rent) {
//        super(ColumnType.Rent);
//        this.rent = rent;
//    }
//}
//
//class PeriodsIndex extends ColumnInteger {
//
//    PeriodsIndex(final int numberOfPeriods) {
//        super(ColumnType.PeriodIndex,
//                Ranges.closedOpen(0, numberOfPeriods).asSet(integers()));
//    }
//}
//
//class RateStrategy {
//
//    double rate;
//
//    public RateStrategy(double rate) {
//        this.rate = rate;
//    }
//
//
//    public Double rate(Integer index) {
//        return rate;
//    }
//}



