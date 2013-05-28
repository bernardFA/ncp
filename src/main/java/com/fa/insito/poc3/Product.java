package com.fa.insito.poc3;

// TODO : change to a composite structure to add behevior
public class Product {

    private String specification;

    private FlowSet flows;

    private LiveCycleState liveCycleState;
    private ValidationState validationState;

    public Product(String specification) {
        this.specification = specification;
        this.liveCycleState = new LiveCycleState();
    }

    // management

    // action initialise product (a sortir et mettre dans action)
    public void initializeProduct() {
        flows = new Calculator(specification).calculate();
    }


    public FlowSet getFlows() {
        return flows;
    }
}

