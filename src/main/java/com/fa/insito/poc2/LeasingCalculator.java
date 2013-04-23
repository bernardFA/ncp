package com.fa.insito.poc2;


import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.func.Reducer;
import org.joda.time.DateMidnight;

public class LeasingCalculator {

    public static final String INITIAL_AMOUNT = "Initial Amount";
    public static final String START_DATE = "Start Date";
    public static final String PAY_FREQUENCY = "Pay Frequency";
    public static final String INTEREST_START = "Interest Start";
    public static final String INTEREST_END = "Interest End";
    public static final String PAYMENT_DATE = "Payment Date";
    public static final String INTEREST_PERIOD = "Interest Period";
    public static final String OUTSTANDING = "Outstanding";
    public static final String CRD = "CRD";
    public static final String RATE = "Rate";
    public static final String INTEREST = "Interest";
    public static final String CAPITAL = "Capital";
    public static final String RENT = "Rent";
    public static final String PAYMENT = "Payment";

    public static void main(String[] args) {
        new LeasingCalculator().findRateTest();
    }

    public void findRateTest() {

        Mapper<Double, Double> leasingCalcul = new Mapper<Double, Double>() {
            @Override
            public Double map(Double rate) {
                return calc(
                        PaymentType.IN_ARREARS,
                        20,
                        new DateMidnight(2013, 1, 1),
                        4,
                        new Base30360(),
                        25000d,
                        1250d,
                        rate)
                    .run()
                    .reduce(new ZeroReducer(2000d), 25000d);
            }
        };

        double rate = dichotomy(0.0d, 1.0d, 30, leasingCalcul);
        System.out.println("RATE " + rate);
        System.out.println("XLS  0,027892831712961");

    };

    Sheet calc(final PaymentType paymentType,
                           final int numberOfPeriods,
                           final DateMidnight startDate,
                           final int payFrequency,
                           final Base base,
                           final double initialAmount,
                           final double rent,
                           final double rate) {

        Sheet sheet = new Sheet()
                .withStatic(START_DATE, startDate)
                .withStatic(PAY_FREQUENCY, payFrequency)
                .withStatic(INITIAL_AMOUNT, initialAmount)
                .withStatic(RATE, rate)
                .withStatic(RENT, rent)
                .addIndexColum(numberOfPeriods)
                .addDateColumn(INTEREST_START, new Formula<DateMidnight>() {
                    @Override
                    DateMidnight funcFirst() {
                        return sheet.dateOfStatic(START_DATE);
                    }

                    @Override
                    DateMidnight func() {
                        return lastDateOfColumn(INTEREST_END);
                    }
                })
                .addDateColumn(INTEREST_END, new Formula<DateMidnight>() {
                    @Override
                    DateMidnight func() {
                        return dateOfColumn(INTEREST_START).plusMonths(12 / intOfStatic(PAY_FREQUENCY));
                    }
                })
                .addDateColumn(PAYMENT_DATE, new Formula<DateMidnight>() {
                    @Override
                    DateMidnight func() {
                        return paymentType.paymentDate(dateOfColumn(INTEREST_START), dateOfColumn(INTEREST_END));
                    }
                })
                .addDoubleColumn(INTEREST_PERIOD, new Formula<Double>() {
                    @Override
                    Double func() {
                        return base.calculateDaycountFraction(dateOfColumn(INTEREST_START), dateOfColumn(INTEREST_END));
                    }
                })
                .addDoubleColumn(OUTSTANDING, new Formula<Double>() {
                    @Override
                    Double funcFirst() {
                        return doubleOfstatic(INITIAL_AMOUNT);
                    }

                    @Override
                    Double func() {
                        return lastDoubleOfColumn(CRD);
                    }
                })
                .addDoubleColumn(RATE, new Formula<Double>() {
                    @Override
                    Double func() {
                        return doubleOfstatic(RATE);
                    }
                })
                .addDoubleColumn(PAYMENT, new Formula<Double>() {
                    @Override
                    Double func() {
                        return doubleOfstatic(RENT);
                    }
                })
                .addDoubleColumn(INTEREST, new Formula<Double>() {
                    @Override
                    Double func() {
                        return doubleOfColumn(RATE) * doubleOfColumn(INTEREST_PERIOD) * doubleOfColumn(OUTSTANDING);
                    }
                })
                .addDoubleColumn(CAPITAL, new Formula<Double>() {
                    @Override
                    Double func() {
                        return doubleOfColumn(PAYMENT) - doubleOfColumn(INTEREST);
                    }
                })
                .addDoubleColumn(CRD, new Formula<Double>() {
                    @Override
                    Double func() {
                        return doubleOfColumn(OUTSTANDING) - doubleOfColumn(CAPITAL);
                    }
                });
       return sheet;
    }

    Double dichotomy(double up, double down, int nbIter, Mapper<Double, Double> function) {
        Double mid = null;
        for (int i=0; i<nbIter; i++) {
            mid = (up+down)/2;
            double zeroTarget = function.map(mid);
            if (zeroTarget > 0)
                up = mid;
            else
                down = mid;
            System.out.println(mid);
        }
        return mid;
    }

    class ZeroReducer implements Reducer<Double, Sheet> {

        double residualValue;

        ZeroReducer(double residualValue) {
            this.residualValue = residualValue;
        }

        @Override
        public Double reduce(Double initialAmount, Sheet sheet) {
            return (sheet.getColDouble(CAPITAL).sum() + residualValue) - initialAmount;
        }

    };
}