package com.fa.insito.poc3.framework;

import com.financeactive.fxent.domain.produit.Valorisation;
import org.joda.time.DateMidnight;

public class FinancialUnitOfWork<I> implements UnitOfWork<Valorisation> {

    private DateMidnight dateMarche;
    private FinancialFunction<I, Valorisation> function;
    private I input;

    public FinancialUnitOfWork(DateMidnight dateMarche, FinancialFunction<I, Valorisation> function, I input) {
        this.dateMarche = dateMarche;
        this.function = function;
        this.input = input;
    }

    @Override
    public Valorisation execute() {
        return function.compute(dateMarche, input);
    }
}
