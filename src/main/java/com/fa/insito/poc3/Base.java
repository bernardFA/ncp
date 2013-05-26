package com.fa.insito.poc3;

import org.jfin.date.daycount.DaycountCalculator;
import org.jfin.date.daycount.DaycountCalculatorFactory;
import org.joda.time.DateMidnight;

public enum Base {

    base_30360 {
        @Override
        DaycountCalculator getDaycountCalculator() {
            return DaycountCalculatorFactory.newInstance().getEU30360();
        }
    },
    base_Exact360 {
        @Override
        DaycountCalculator getDaycountCalculator() {
            return DaycountCalculatorFactory.newInstance().getActual360();
        }
    };

    public double calculateDaycountFraction(DateMidnight d1, DateMidnight d2) {
        return getDaycountCalculator().calculateDaycountFraction(d1.toGregorianCalendar(), d2.toGregorianCalendar());
    }

    abstract DaycountCalculator getDaycountCalculator();

}
