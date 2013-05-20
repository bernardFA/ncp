package com.fa.insito.poc2;

public enum InterestCalculationMode {

    on_crd_after_payment_minus_rent {
        @Override
        Double interestBaseAmount(Double outstandingBeforePayment, Double payment, Double capital) {
            return outstandingBeforePayment - payment;
        }
    },
    on_crd_after_payment_minus_capital {
        @Override
        Double interestBaseAmount(Double outstandingBeforePayment, Double payment, Double capital) {
            return outstandingBeforePayment - capital;
        }
    };
//    on_crd_before_payment_and_effective_mobilisation_duration {
//        @Override
//        Double interestBaseAmount(Double outstandingBeforePayment, Double payment, Double capital) {
//            return outstandingBeforePayment;
//        }
//    },
//    on_crd_before_payment_and_next_period_duration {
//        @Override
//        Double interestBaseAmount(Double outstandingBeforePayment, Double payment, Double capital) {
//            return outstanding - capital;
//        }
//    };

    abstract Double interestBaseAmount(Double outstanding, Double payment, Double capital);

}
