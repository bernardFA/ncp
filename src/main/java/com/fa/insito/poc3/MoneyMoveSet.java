package com.fa.insito.poc3;

import java.util.HashSet;

public class MoneyMoveSet extends HashSet<MoneyMove> {

    public MoneyMoveSet(MoneyMove... moneyMoves) {
        for (MoneyMove moneyMove : moneyMoves)
            add(moneyMove);
    }

    public MoneyMoveSet(MoneyMoveSet flows) {
        for (MoneyMove moneyMove : flows)
            add(moneyMove);
    }

    public MoneyMoveSet add(MoneyMoveSet moneyMoveSet) {
        for (MoneyMove moneyMove : moneyMoveSet)
            add(moneyMove);
        return this;
    }


}
