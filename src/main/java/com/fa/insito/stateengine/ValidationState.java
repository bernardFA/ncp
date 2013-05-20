package com.fa.insito.stateengine;


public class ValidationState extends StateEngine {

    // states
    public static final String PENDING = "Pending";
    public static final String INTEGRATED = "Integrated";
    public static final String CLIENTAPPROVED = "Client Approved";
    // transitions
    public static final String INVALIDATION = "Invalidation";
    public static final String VALIDATION = "Validation";
    public static final String INTEGRATION = "Integration";
    public static final String MODIFICATION = "Modification";
    public static final String DISAPPROVE = "disapprove";

    ValidationState() {
        super(new State(PENDING),
                new Transition(new State(PENDING), INTEGRATION, new State(INTEGRATED)),
                new Transition(new State(INTEGRATED), INVALIDATION, new State(PENDING)),
                new Transition(new State(INTEGRATED), VALIDATION, new State(CLIENTAPPROVED)),
                new Transition(new State(CLIENTAPPROVED), MODIFICATION, new State(INTEGRATED)),
                new Transition(new State(CLIENTAPPROVED), DISAPPROVE, new State(PENDING)));
    }

}
