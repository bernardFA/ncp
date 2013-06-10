package com.fa.insito.poc3.calcengine;


import com.fa.insito.poc3.MoneyMoveSet;
import com.fa.insito.poc3.calclib.SheetFactory;
import com.google.common.collect.Sets;

import java.util.*;

public class MultiSheetCalculator implements Calculator {

    public MoneyMoveSet calculate(Map specifications) {
        MoneyMoveSet moneyMoveSet = null;
        List sheetSpecs = (List)specifications.get("specs");

        Set<Column> chainedColumns = Sets.newHashSet();

        for (Iterator it = sheetSpecs.iterator(); it.hasNext(); ) {

            // get the sheet's calculation
            Map sheetSpec = (Map)it.next();
            Sheet sheet = SheetFactory.newSheet((String) sheetSpec.get("sheet"));

            // get I/O spec of the sheet
            Map input = (Map)sheetSpec.get("input");
            Object output = sheetSpec.get("output");

            // chaining sheet's calculation
            sheet.prepareColumn(chainedColumns);
            sheet.prepareData(input);
            sheet.calculate();
            if (it.hasNext())
                chainedColumns = sheet.extractColumn((List<String>)output);
            else
                moneyMoveSet = sheet.extractFlows((Map)output);
        }

        return moneyMoveSet;
    }

}
