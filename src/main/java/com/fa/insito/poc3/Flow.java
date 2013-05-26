package com.fa.insito.poc3;


import org.joda.time.DateMidnight;

import java.util.Currency;

public class Flow {

    enum Type { INTEREST, CAPITAL }

    private Type type;
    private Currency currency;

    private DateMidnight date;
    private double amount;

    public Flow(Type type, Currency currency, DateMidnight date, double amount) {
        this.type = type;
        this.currency = currency;
        this.date = date;
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public Currency getCurrency() {
        return currency;
    }

    public DateMidnight getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}
