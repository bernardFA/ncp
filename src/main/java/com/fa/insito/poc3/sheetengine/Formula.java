package com.fa.insito.poc3.sheetengine;


import org.joda.time.DateMidnight;

public abstract class Formula<T> {

    protected Sheet sheet;
    protected Column<T> column;
    protected int currentIndex;

    /* calcul context injection by calc engine */
    public void setContext(Sheet sheet, Column column, Integer index) {
        this.sheet = sheet;
        this.column = column;
        this.currentIndex = index;
    }

    Sheet getSheet() {
        return sheet;
    }

    Column<T> getColumn() {
        return column;
    }

    int getCurrentIndex() {
        return currentIndex;
    }

    /* default implem */
    public T funcFirst() {
        return func();
    }

    public abstract T func();


    public Integer intCell(String columnName) {
        return sheet.intColumn(columnName).get(currentIndex);
    }

    public Integer intLastCell(String columnName) {
        return sheet.intColumn(columnName).getBefore(currentIndex);
    }

    public DateMidnight dateCell(String columnName) {
        return sheet.dateColumn(columnName).get(currentIndex);
    }

    public DateMidnight dateLastCell(String columnName) {
        return sheet.dateColumn(columnName).getBefore(currentIndex);
    }

    public Double doubleCell(String columnName) {
        return sheet.doubleColumn(columnName).get(currentIndex);
    }

    public Double doubleLastCell(String columnName) {
        return sheet.doubleColumn(columnName).getBefore(currentIndex);
    }

}
