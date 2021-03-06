package com.fa.insito.poc2;

import org.jfin.date.daycount.DaycountCalculator;
import org.jfin.date.daycount.DaycountCalculatorFactory;

public class Base30360 extends Base {

    DaycountCalculator daycountCalculator = DaycountCalculatorFactory.newInstance().getEU30360();

    @Override
    DaycountCalculator getDaycountCalculator() {
        return daycountCalculator;
    }
}