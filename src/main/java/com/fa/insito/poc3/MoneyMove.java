package com.fa.insito.poc3;


import com.google.common.collect.Sets;
import org.joda.time.DateMidnight;

import java.util.Set;

/**
 * real money flow (treso) //
 */
public class MoneyMove {

    public enum Type {
        PAYMENT,         // "remboursement classiques"
        TIRAGE,          // name ?
        EARLYREPAYMENT,  // payoff ? ("remboursement anticipé")
        SUBVENTION,      // name ?
        PRIME            //
    }

    private Type type;
    private DateMidnight dateValue;
    private boolean forcedDateValue = false;

    private Set<MoveItem> moveItems = Sets.newHashSet();

    public MoneyMove(Type type, DateMidnight dateValue, Set<MoveItem> moveItems) {
        this.type = type;
        this.dateValue = dateValue;
        this.moveItems = moveItems;
    }

    public DateMidnight getDateValue() {
        return dateValue;
    }

    public boolean isForcedDateValue() {
        return forcedDateValue;
    }

    public void forceDateValue(DateMidnight dateValue) {
        this.dateValue = dateValue;
        forcedDateValue = true;
    }

    public boolean isBefore(DateMidnight date) {
        return this.dateValue.isBefore(date);
    }

    public boolean isAfter(DateMidnight date) {
        return this.dateValue.isAfter(date);
    }

       /*
    public Comparator<Flow> getDateComparator() {
        return new Comparator<Flow>() {
            @Override
            public int compare(Flow f1, Flow f2) {
                return f1.getDateValue().compareTo(f2.getDateValue());
            }
        }; // TODO OUTCH !
    }
    */
}
