package com.fa.insito.poc2;

import org.jfin.date.daycount.DaycountCalculator;
import org.joda.time.DateMidnight;

abstract class Base {

    public double calculateDaycountFraction(DateMidnight d1, DateMidnight d2) {
        return getDaycountCalculator().calculateDaycountFraction(d1.toGregorianCalendar(), d2.toGregorianCalendar());
    }

    abstract DaycountCalculator getDaycountCalculator();

}
