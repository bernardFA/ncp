package com.fa.insito.poc3.specs;

import org.joda.time.DateMidnight;
import org.joda.time.Period;

public enum PaymentPeriodicity {

    MONTHLY {
        @Override
        public Period getInterval() {
            return Period.months(1);
        }
    },
    QUARTERLY {
        @Override
        public Period getInterval() {
            return Period.months(3);
        }
    },
    SEMI_ANNUALLY {
        @Override
        public Period getInterval() {
            return Period.months(6);
        }
    },
    ANNUALLY {
        @Override
        public Period getInterval() {
            return Period.years(1);
        }
    };

    public abstract Period getInterval();

    public DateMidnight nextDate(DateMidnight currentDate) {
        return currentDate.plus(getInterval());
    }
}
