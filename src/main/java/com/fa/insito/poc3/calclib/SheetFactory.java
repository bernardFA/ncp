package com.fa.insito.poc3.calclib;


import com.fa.insito.poc3.calcengine.Sheet;
import com.google.common.collect.ImmutableMap;

public class SheetFactory {

    static final ImmutableMap<String, Class> sheets =
            new ImmutableMap.Builder<String, Class>()
                    .put("Loan Fixed Rate", LoanFixedRate.class)
                    .put("Flow Extraction", MoneyMoveExtraction.class)
                    .build();

    public static Sheet newSheet(String name) {
        Class sheetType = sheets.get(name);
        if (sheetType == null)
            throw new IllegalArgumentException("sheet " + name + " doesn't exist in factory");
        try {
            return (Sheet) sheetType.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e); // TODO
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e); //TODO
        }
    }
}
