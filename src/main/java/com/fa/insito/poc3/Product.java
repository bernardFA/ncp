package com.fa.insito.poc3;

import com.fa.insito.poc3.calcengine.Calculator;
import com.fa.insito.poc3.calcengine.CalculatorFactory;
import com.fa.insito.poc3.framework.Function;
import com.fa.insito.poc3.framework.Specification;
import com.fa.insito.poc3.sandbox.LiveCycleState;
import com.fa.insito.poc3.sandbox.ValidationState;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

// TODO : change to a composite structure to add behevior
public class Product {

    private ProductSpec productSpec;

    private MoneyMoveSet moneyMoves;

    // sandbox 4now
    private LiveCycleState liveCycleState;
    private ValidationState validationState;

    public Product(ProductSpec productSpec) {
        this.productSpec = productSpec;
        this.liveCycleState = new LiveCycleState();
    }

    // management

    // TODO : action initialise product (a sortir et mettre dans action)
    public void initializeProduct() {
        moneyMoves = getCalculator().calculate(productSpec);
    }

    public Calculator getCalculator() {
        return CalculatorFactory.newCalculator((String) productSpec.getCalculator());
    }

    public <T> Set<T> extract(final Specification<MoneyMove> spec, final Function<MoneyMove, T> function) {
        final Set<T> res = Sets.newHashSet(); // new HashSet<T>();
        for (MoneyMove moneyMove : moneyMoves)
            if (spec.isSatisfiedBy(moneyMove))
                res.add(function.apply(moneyMove));
        return res;
    }

    public Set<MoneyMove> extract(final Specification<MoneyMove> spec) {
        return extract(spec, new Function<MoneyMove, MoneyMove>() {
            @Override
            public MoneyMove apply(MoneyMove moneyMove) {
                return moneyMove;
            }
        });
    }


    public MoneyMoveSet getMoneyMoves() {
        return moneyMoves;
    }
}

