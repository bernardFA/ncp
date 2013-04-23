package com.fa.insito.poc2;

import org.joda.time.DateMidnight;

interface Base {
    abstract double calculateDaycountFraction(DateMidnight d1, DateMidnight d2);
}
