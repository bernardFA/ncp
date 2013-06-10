package com.fa.insito.poc3.specs;


import org.joda.time.DateMidnight;

public enum PaymentType {

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
