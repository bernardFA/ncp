package com.fa.insito.poc3;


import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class LeasingTest {

    @Test
    public void testSimpleLeasing() {

        String spec = isToString(this.getClass().getClassLoader().getResourceAsStream("com/fa/insito/poc3/Sample CB Loyer Constant Type1.json"));
        Product p = new Product(new Specification(spec));
        p.initializeProduct();

        for (Flow flow : p.getFlows())
            System.out.println(flow);

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
