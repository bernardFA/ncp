package com.fa.insito.poc3.calclib;


import com.fa.insito.poc3.ProductSpec;
import com.fa.insito.poc3.specs.Base;
import com.fa.insito.poc3.specs.PaymentPeriodicity;
import org.joda.time.DateMidnight;

import java.util.Currency;

public class StandardLoanSpec extends ProductSpec {

    private Currency currency;
    private Base base;
    private long numberOfPeriod;
    private double initialAmount;
    private double rent;
    private double rate;
    private DateMidnight startDate;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public PaymentPeriodicity paymentPeriodicity() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public long getNumberOfPeriod() {
        return numberOfPeriod;
    }

    public void setNumberOfPeriod(long numberOfPeriod) {
        this.numberOfPeriod = numberOfPeriod;
    }

    public double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(double initialAmount) {
        this.initialAmount = initialAmount;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


    public DateMidnight getStartDate() {
        return startDate;
    }

    public void setStartDate(DateMidnight startDate) {
        this.startDate = startDate;
    }
}
