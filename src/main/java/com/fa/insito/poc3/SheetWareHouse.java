package com.fa.insito.poc3;


import com.fa.insito.poc3.sheetengine.Sheet;
import com.fa.insito.poc3.sheets.Leasing_in_arrears;
import com.google.common.collect.ImmutableMap;

public class SheetWareHouse {

    static final ImmutableMap<String, Class> sheets =
            new ImmutableMap.Builder<String, Class>()
                    .put("Leasing in arrears", Leasing_in_arrears.class)
                    .build();

    public static Sheet getInstance(Object name) {
        Class sheetType = sheets.get(name);
        if (sheetType == null)
            throw new IllegalArgumentException("sheet " + name + " doesn't exist in warehouse");
        try {
            return (Sheet) sheetType.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e); // TODO
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e); //TODO
        }
    }
}
