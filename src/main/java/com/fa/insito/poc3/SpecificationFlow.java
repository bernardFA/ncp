package com.fa.insito.poc3;


import com.fa.insito.poc3.framework.AbstractSpecification;
import org.joda.time.DateMidnight;

public class SpecificationFlow {

    public static AbstractSpecification<MoneyMove> isBefore(final DateMidnight date) {
        return new AbstractSpecification<MoneyMove>() {
            @Override
            public boolean isSatisfiedBy(MoneyMove moneyMove) {
                return moneyMove.isBefore(date);
            }
        };
    }

    public static AbstractSpecification<MoneyMove> isAfter(final DateMidnight date) {
        return new AbstractSpecification<MoneyMove>() {
            @Override
            public boolean isSatisfiedBy(MoneyMove moneyMove) {
                return moneyMove.isAfter(date);
            }
        };
    }

}
