package com.fa.insito.poc3;


import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LeasingTest {

    @Test
    public void testSimpleLeasing() {

        String spec = isToString(this.getClass().getClassLoader().getResourceAsStream("com/fa/insito/poc3/Sample CB Loyer Constant Type1.json"));
        JSONParser parser = new JSONParser();
        Map specifications = null;
        try {
            specifications = (Map)parser.parse(spec,  new ContainerFactory(){
                public List creatArrayContainer() {
                    return new LinkedList();
                }
                public Map createObjectContainer() {
                    return new LinkedHashMap();
                }
            });
        } catch (ParseException e) {
            throw new IllegalArgumentException("error parsing specs json " + specifications, e);
        }
        Product p = new Product(specifications);
        p.initializeProduct();

        for (MoneyMove moneyMove : p.getMoneyMoves())
            System.out.println(moneyMove);

    }


    private static String isToString(InputStream is) {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        try {
            for (int n; (n = is.read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

}
