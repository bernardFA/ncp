package com.fa.insito.stateengine;


public class ProductValidationStatus extends StateEngine {

    public static final String PENDING = "Pending";
    public static final String BODONE = "BO done";
    public static final String INTEGRATED = "Integrated";
    public static final String CLIENTAPPROVED = "Client Approved";

    public static final String MODIFICATIONBO = "Modification BO";
    public static final String INVALIDATION = "Invalidation";
    public static final String VALIDATION = "Validation";
    public static final String INTEGRATION = "Integration";
    public static final String MODIFICATION = "Modification";
    public static final String DISAPPROVE = "disapprove";

    ProductValidationStatus() {
        super(PENDING,
                new Transition(PENDING, MODIFICATIONBO, BODONE),
                new Transition(PENDING, INTEGRATION, INTEGRATED),
                new Transition(BODONE, INVALIDATION, PENDING),
                new Transition(BODONE, VALIDATION, INTEGRATED),
                new Transition(INTEGRATED, INVALIDATION, PENDING),
                new Transition(INTEGRATED, VALIDATION, CLIENTAPPROVED),
                new Transition(CLIENTAPPROVED, MODIFICATION, INTEGRATED),
                new Transition(CLIENTAPPROVED, DISAPPROVE, PENDING));
    }

    private void pending() {

    }

}
