package com.fa.insito.poc3;


public class Product {

    private Specification specification;

    private FlowSet flows;

    public Product(Specification specification) {
        this.specification = specification;
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

