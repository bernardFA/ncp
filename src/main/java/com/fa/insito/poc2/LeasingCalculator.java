package com.fa.insito.poc2;


import com.fa.insito.poc2.columns.Formula;
import com.fa.insito.poc2.columns.Sheet;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.func.Reducer;
import org.joda.time.DateMidnight;

public class LeasingCalculator {

    public static final String INITIAL_AMOUNT = "Initial Amount";
    public static final String START_DATE = "Start Date";
    public static final String INTEREST_START = "Interest Start";
    public static final String INTEREST_END = "Interest End";
    public static final String PAYMENT_DATE = "Payment Date";
    public static final String INTEREST_PERIOD = "Interest Period";
    public static final String OUTSTANDING = "Outstanding";
    public static final String INTEREST_BASE_AMOUNT = "Interest Base Amount";
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
                Sheet sheet = calc(
                        PaymentType.IN_ADVANCE,
                        InterestCalculationMode.on_crd_after_payment_minus_rent,
                        20,
                        new DateMidnight(2013, 1, 1),
                        PaymentPeriodicity.QUARTERLY,
                        new BaseExact360(),
                        25000d,
                        1250d,
                        rate)
                    .run();
                System.out.println(sheet.extract(PAYMENT_DATE, PAYMENT, RATE, CAPITAL, INTEREST, CRD).print());
                return sheet.reduce(new ZeroReducer(2000d), 25000d);
            }
        };

        double rate = dichotomy(0.0d, 1.0d, 30, leasingCalcul);
        System.out.println("ICI " + rate);
       // System.out.println("XLS echu = 0,027892831712961");
        System.out.println("PRO échoir = 0.030499");


    };

    Sheet calc(final PaymentType paymentType,
               final InterestCalculationMode interestCalculationMode,
               final int numberOfPeriods,
               final DateMidnight startDate,
               final PaymentPeriodicity paymentPeriodicity,
               final Base base,
               final double initialAmount,
               final double rent,
               final double rate) {

        Sheet sheet = new Sheet(numberOfPeriods)
                .withStatic(START_DATE, startDate)
                .withStatic(INITIAL_AMOUNT, initialAmount)
                .withStatic(RATE, rate)
                .withStatic(RENT, rent)
                .addDateColumn(INTEREST_START, new Formula<DateMidnight>() {
                    @Override
                    public DateMidnight funcFirst() {
                        return dateStatic(START_DATE);
                    }

                    @Override
                    public DateMidnight func() {
                        return dateLastCell(INTEREST_END);
                    }
                })
                .addDateColumn(INTEREST_END, new Formula<DateMidnight>() {
                    @Override
                    public DateMidnight func() {
                        return paymentPeriodicity.nextDate(dateCell(INTEREST_START));
                    }
                })
                .addDateColumn(PAYMENT_DATE, new Formula<DateMidnight>() {
                    @Override
                    public DateMidnight func() {
                        return paymentType.paymentDate(dateCell(INTEREST_START), dateCell(INTEREST_END));
                    }
                })
                .addDoubleColumn(INTEREST_PERIOD, new Formula<Double>() {
                    @Override
                    public Double funcFirst() {
                        return paymentType == PaymentType.IN_ADVANCE ? 0d : func();
                    }
                    @Override
                    public Double func() {
                        return base.calculateDaycountFraction(dateCell(INTEREST_START), dateCell(INTEREST_END));
                    }
                })
                .addDoubleColumn(OUTSTANDING, new Formula<Double>() {
                    @Override
                    public Double funcFirst() {
                        return doubleStatic(INITIAL_AMOUNT);
                    }

                    @Override
                    public Double func() {
                        return doubleLastCell(CRD);
                    }
                })
                .addDoubleColumn(RATE, new Formula<Double>() {
                    @Override
                    public Double func() {
                        return doubleStatic(RATE);
                    }
                })
                .addDoubleColumn(PAYMENT, new Formula<Double>() {
                    @Override
                    public Double func() {
                        return doubleStatic(RENT);
                    }
                })
                .addDoubleColumn(INTEREST_BASE_AMOUNT, new Formula<Double>() {
                    @Override
                    public Double func() {
                        if (paymentType == PaymentType.IN_ADVANCE)
                            return interestCalculationMode.interestBaseAmount(doubleCell(OUTSTANDING), doubleCell(PAYMENT), doubleCell(CAPITAL));
                        else
                            return doubleCell(OUTSTANDING);
                    }
                })
                .addDoubleColumn(INTEREST, new Formula<Double>() {
                    @Override
                    public Double func() {
                        return doubleCell(RATE) * doubleCell(INTEREST_PERIOD) * doubleCell(INTEREST_BASE_AMOUNT);
                    }
                })
                .addDoubleColumn(CAPITAL, new Formula<Double>() {
                    @Override
                    public Double func() {
                        return doubleCell(PAYMENT) - doubleCell(INTEREST);
                    }
                })
                .addDoubleColumn(CRD, new Formula<Double>() {
                    @Override
                    public Double func() {
                        return doubleCell(OUTSTANDING) - doubleCell(CAPITAL);
                    }
                });
       return sheet;
    }

    double dichotomy(double up, double down, int nbIter, Mapper<Double, Double> function) {
        double mid = up+up; /* crazy value */
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
            return (sheet.doubleColumn(CAPITAL).sum() + residualValue) - initialAmount;
        }

    };
}