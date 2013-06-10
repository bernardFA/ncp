package com.fa.insito.poc3.calclib;


import com.fa.insito.poc3.MoneyMoveSet;
import com.fa.insito.poc3.ProductSpec;
import com.fa.insito.poc3.calcengine.Formula;
import com.fa.insito.poc3.calcengine.Sheet;
import com.fa.insito.poc3.specs.Base;
import com.fa.insito.poc3.specs.PaymentPeriodicity;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Currency;
import java.util.Map;

public class LoanFixedRate extends Sheet {

    public static final String CURRENCY = "currency";
    private Currency currency;

    public static final String BASE = "base";
    private Base base;

    public static final String PAYMENT_PERIODICITY = "payment periodicity";
    private PaymentPeriodicity paymentPeriodicity;

    public static final String NUMBER_OF_PERIOD = "number of period";
    private long numberOfPeriod;

    public static final String INITIAL_AMOUNT = "initial amount";
    private double initialAmount;

    public static final String RENT = "rent";
    private double rent;

    public static final String RATE_ = "rate";
    private double rate;

    public static final String START_DATE = "Start Date";
    private DateMidnight startDate;

    // colmuns

    public static final String INTEREST_START = "Interest Start";
    public static final String INTEREST_END = "Interest End";
    public static final String PAYMENT_DATE = "Payment Date";
    public static final String INTEREST_PERIOD = "Interest Period";
    public static final String OUTSTANDING = "Outstanding";
    public static final String RATE = "Rate";
    public static final String PAYMENT = "Payment";
    public static final String INTEREST = "Interest";
    public static final String CAPITAL = "Capital";
    public static final String CRD = "CRD";

    private static DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

    public Sheet prepareData(ProductSpec productSpec) {

        StandardLoanSpec spec = (StandardLoanSpec)productSpec;
        currency = spec.getCurrency();//Currency.getInstance((String) productSpec.get(CURRENCY));
        base = spec.getBase(); //Base.valueOf((String)productSpec.get(BASE));
        paymentPeriodicity = spec.paymentPeriodicity();   //getPayPaymentPeriodicity.valueOf((String)productSpec.get(PAYMENT_PERIODICITY));
        numberOfPeriod = spec.getNumberOfPeriod(); //(Long)productSpec.get(NUMBER_OF_PERIOD);
        initialAmount = spec.getInitialAmount(); //(Double)productSpec.get(INITIAL_AMOUNT);
        rent = spec.getRent();//(Double)productSpec.get(RENT);
        rate = spec.getRate();//(Double)productSpec.get(RATE_);
        startDate = spec.getStartDate(); new DateMidnight(); //fmt.parse((String)input.get(START_DATE));

        addDateColumn(INTEREST_START, new Formula<DateMidnight>() {
            @Override
            public DateMidnight funcFirst() {
                return startDate;
            }

            @Override
            public DateMidnight func() {
                return dateLastCell(INTEREST_END);
            }
        });
        addDateColumn(INTEREST_END, new Formula<DateMidnight>() {
            @Override
            public DateMidnight func() {
                return paymentPeriodicity.nextDate(dateCell(INTEREST_START));
            }
        });
        addDateColumn(PAYMENT_DATE, new Formula<DateMidnight>() {
            @Override
            public DateMidnight func() {
                return dateCell(INTEREST_END);
            }
        });
        addDoubleColumn(INTEREST_PERIOD, new Formula<Double>() {
            @Override
            public Double func() {
                return base.calculateDaycountFraction(dateCell(INTEREST_START), dateCell(INTEREST_END));
            }
        });
        addDoubleColumn(OUTSTANDING, new Formula<Double>() {
            @Override
            public Double funcFirst() {
                return initialAmount;
            }
            @Override
            public Double func() {
                return doubleLastCell(CRD);
            }
        });
        addDoubleColumn(RATE, new Formula<Double>() {
            @Override
            public Double func() {
                return rate;
            }
        });
        addDoubleColumn(PAYMENT, new Formula<Double>() {
            @Override
            public Double func() {
                return rent;
            }
        });
        addDoubleColumn(INTEREST, new Formula<Double>() {
            @Override
            public Double func() {
                return doubleCell(RATE) * doubleCell(INTEREST_PERIOD) * doubleCell(OUTSTANDING);
            }
        });
        addDoubleColumn(CAPITAL, new Formula<Double>() {
            @Override
            public Double func() {
                return doubleCell(PAYMENT) - doubleCell(INTEREST);
            }
        });
        addDoubleColumn(CRD, new Formula<Double>() {
            @Override
            public Double func() {
                return doubleCell(OUTSTANDING) - doubleCell(CAPITAL);
            }
        });
        return this;
    }

    @Override
    protected boolean stopCondition() {
        Double crd = doubleColumn(CRD).last();
        return crd == null ? true : crd == 0;
    }

    @Override
    public MoneyMoveSet extractFlows(Map output) {
        return null;
    }

}