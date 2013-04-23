package com.fa.insito.poc2;


import org.jfin.date.daycount.DaycountCalculator;
import org.jfin.date.daycount.DaycountCalculatorFactory;

public class BaseExact360 extends Base {

    DaycountCalculator daycountCalculator = DaycountCalculatorFactory.newInstance().getActual360();

    @Override
    DaycountCalculator getDaycountCalculator() {
        return daycountCalculator;
    }
}
