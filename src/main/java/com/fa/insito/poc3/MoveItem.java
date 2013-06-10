package com.fa.insito.poc3;


public class MoveItem {

    private Double capital;
    private Double interest;
    private Double fee;
    private boolean forcedCapital = false;
    private boolean forcedInterest = false;
    private boolean forcedFee = false;

    public MoveItem(Double capital, Double interest, Double fee) {
        this.capital = capital;
        this.interest = interest;
        this.fee = fee;
    }

    public Double getCapital() {
        return capital;
    }

    public Double getInterest() {
        return interest;
    }

    public Double getFee() {
        return fee;
    }

    public boolean isForcedCapital() {
        return forcedCapital;
    }

    public boolean isForcedInterest() {
        return forcedInterest;
    }

    public boolean isForcedFee() {
        return forcedFee;
    }

    public void forceCapital(Double capital) {
        this.capital = capital;
        this.forcedCapital = true;
    }

    public void forceInterest(Double interest) {
        this.interest = interest;
        this.forcedInterest = true;
    }

    public void forceFee(Double fee) {
        this.fee = fee;
        this.forcedFee = true;
    }

}
