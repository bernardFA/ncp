package com.fa.insito.poc3.calcengine;


public class CalculatorFactory {

    /*
    static final ImmutableMap<String, Class> calculators =
            new ImmutableMap.Builder<String, Class>()
                    .put("MultiSheetCalculator", MultiSheetCalculator.class)
                    .build();
    */

    public static Calculator newCalculator(String calculatorName) {
        return new MultiSheetCalculator();

    }
}
