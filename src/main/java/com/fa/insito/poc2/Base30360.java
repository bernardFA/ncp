package com.fa.insito.poc2;

import org.jfin.date.daycount.DaycountCalculator;
import org.jfin.date.daycount.DaycountCalculatorFactory;
import org.joda.time.DateMidnight;

public class Base30360 implements Base {

    DaycountCalculator daycountCalculator = DaycountCalculatorFactory.newInstance().getEU30360();

    public double calculateDaycountFraction(DateMidnight d1, DateMidnight d2) {
        return daycountCalculator.calculateDaycountFraction(d1.toGregorianCalendar(), d2.toGregorianCalendar());
    }
}

