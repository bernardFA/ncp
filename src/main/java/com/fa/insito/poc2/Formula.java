package com.fa.insito.poc2;


import org.joda.time.DateMidnight;

abstract class Formula<T> {

    protected Sheet sheet;
    protected Column<T> column;
    protected int currentIndex;

    /* context injection by engine */
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
    T funcFirst() {
        return func();
    }

    abstract T func();

    /* Tools */

    T before() {
        return column.getBefore(currentIndex);
    }

    Integer intOfColumn(String columnName) {
        return sheet.intColumn(columnName).get(currentIndex);
    }

    Integer getBeforeIntCell(String columnName) {
        return sheet.intColumn(columnName).getBefore(currentIndex);
    }

    DateMidnight dateCell(String columnName) {
        return sheet.dateColumn(columnName).get(currentIndex);
    }

    DateMidnight lastDateOfColumn(String columnName) {
        return sheet.dateColumn(columnName).getBefore(currentIndex);
    }

    Double doubleCell(String columnName) {
        return sheet.doubleColumn(columnName).get(currentIndex);
    }

    Double doubleLastCell(String columnName) {
        return sheet.doubleColumn(columnName).getBefore(currentIndex);
    }

    Integer intStatic(String name) {
        return sheet.intStatic(name);
    }

    DateMidnight dateStatic(String name) {
        return sheet.dateStatic(name);
    }

    Double doubleStatic(String name) {
        return sheet.doubleStatic(name);
    }


}
