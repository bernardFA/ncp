package com.fa.insito.poc3;


import com.fa.insito.poc3.sheetengine.Column;
import com.fa.insito.poc3.sheetengine.Sheet;
import com.google.common.collect.Sets;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class Calculator {

    private Map specifications;

    public Calculator(String specification) {
        JSONParser parser = new JSONParser();
        try {
            this.specifications = (Map)parser.parse(specification,  new ContainerFactory(){
                public List creatArrayContainer() {
                    return new LinkedList();
                }
                public Map createObjectContainer() {
                    return new LinkedHashMap();
                }
            });
        } catch (ParseException e) {
            throw new IllegalArgumentException("error parsing specification json " + specification, e);
        }
    }

    public FlowSet calculate() {
        FlowSet flowSet = null;
        List sheetSpecs = (List)specifications.get("calculator");

        Set<Column> chainedColumns = Sets.newHashSet();

        for (Iterator it = sheetSpecs.iterator(); it.hasNext(); ) {
            Map sheetSpec = (Map)it.next();
            Sheet sheet = SheetWareHouse.getInstance(sheetSpec.get("name"));
            Map input = (Map)sheetSpec.get("input");
            Set<String> output = new HashSet((List<String>)sheetSpec.get("output"));

            // chaining sheets calculation
            sheet.prepareColumn(chainedColumns);
            sheet.prepareData(input);
            sheet.calculate();
            if (it.hasNext())
                chainedColumns = sheet.extractColumn(output);
            else
                flowSet = sheet.extractFlows(output);
        }

        return flowSet;
    }

}
