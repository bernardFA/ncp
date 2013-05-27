package com.fa.insito.poc3;

// TODO : change to a composite structure to add behevior
public class Product {

    private Specification specification;

    private FlowSet flows;

    private LiveCycleState liveCycleState;
    private ValidationState validationState;

    public Product(Specification specification) {
        this.specification = specification;
        this.liveCycleState = new LiveCycleState();
    }

    // management

    // action initialise product
    public void initializeProduct() {
        flows = SheetWareHouse.getSheet(specification.getName())
                .prepare(specification.getInput())
                .calculate()
                .extractFlows(specification.getOutput());
    }

    public FlowSet getFlows() {
        return flows;
    }
}

