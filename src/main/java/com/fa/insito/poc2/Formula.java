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
        return sheet.getColInteger(columnName).get(currentIndex);
    }

    Integer getBeforeIntCell(String columnName) {
        return sheet.getColInteger(columnName).getBefore(currentIndex);
    }

    DateMidnight dateOfColumn(String columnName) {
        return sheet.getColDate(columnName).get(currentIndex);
    }

    DateMidnight lastDateOfColumn(String columnName) {
        return sheet.getColDate(columnName).getBefore(currentIndex);
    }

    Double doubleOfColumn(String columnName) {
        return sheet.getColDouble(columnName).get(currentIndex);
    }

    Double lastDoubleOfColumn(String columnName) {
        return sheet.getColDouble(columnName).getBefore(currentIndex);
    }

    Integer intOfStatic(String name) {
        return sheet.intOfStatic(name);
    }

    DateMidnight getStaticDate(String name) {
        return sheet.dateOfStatic(name);
    }

    Double doubleOfstatic(String name) {
        return sheet.doubleOfstatic(name);
    }


}
