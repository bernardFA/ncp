package com.fa.insito.poc3;


import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Specification {

    private Map specs;

    public Specification(String data) {
        JSONParser parser = new JSONParser();
        try {
            this.specs = (Map)parser.parse(data,  new ContainerFactory(){
                public List creatArrayContainer() {
                    return new LinkedList();
                }
                public Map createObjectContainer() {
                    return new LinkedHashMap();
                }
            });
        } catch (ParseException e) {
            throw new IllegalArgumentException("error parsing specification json " + data, e);
        }
    }

    public Map getInput() {
        return (Map)specs.get("input");
    }

    public List<String> getOutput() {
        return (List<String>)specs.get("output");
    }

    public String getName() {
        return (String)specs.get("name");
    }
}
