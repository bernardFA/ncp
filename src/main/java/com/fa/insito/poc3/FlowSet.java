package com.fa.insito.poc3;

import java.util.HashSet;

public class FlowSet extends HashSet<Flow> {

    public FlowSet(Flow... flows) {
        for (Flow flow : flows)
            add(flow);
    }

    public FlowSet(FlowSet flows) {
        for (Flow flow : flows)
            add(flow);
    }

    public FlowSet union(FlowSet flowSet) {
        for (Flow flow : flowSet)
            add(flow);
        return this;
    }
}
