package com.fa.insito.poc3.framework;

import org.joda.time.DateMidnight;

public interface FinancialFunction<I, O> {

    O compute(DateMidnight dateMarche, I input);

}
