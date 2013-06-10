package com.fa.insito.poc3.calclib;

import com.fa.insito.poc3.MoneyMove;
import com.fa.insito.poc3.MoneyMoveSet;
import com.fa.insito.poc3.MoveItem;
import com.fa.insito.poc3.calcengine.ColumnDate;
import com.fa.insito.poc3.calcengine.ColumnDouble;
import com.fa.insito.poc3.calcengine.Sheet;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;


public class MoneyMoveExtraction {

    Sheet sheet;

    public MoneyMoveExtraction(Sheet sheet) {
        this.sheet = sheet;
    }

    @Override
    public MoneyMoveSet extractFlows(Map output) {
        // TODO : rajouter les autres type de flux
        List<String> colNames = (List<String>)output.get("PAYMENT");
        return extractPaymentFlows(dateColumn(colNames.get(0)), doubleColumn(colNames.get(1)), doubleColumn(colNames.get(2)), doubleColumn(colNames.get(3)));
    }

    public MoneyMoveSet extractPaymentFlows(ColumnDate paymentDates, ColumnDouble capitals, ColumnDouble interests, ColumnDouble fees) {
        MoneyMoveSet flows = new MoneyMoveSet();
        for (int line=0; line<paymentDates.size(); line++) {
            MoveItem moveItem = new MoveItem(capitals.get(line), interests.get(line), fees.get(line));
            flows.add(new MoneyMove(MoneyMove.Type.PAYMENT, paymentDates.get(line), Sets.newHashSet(moveItem)));
        }
        return flows;
    }

}
