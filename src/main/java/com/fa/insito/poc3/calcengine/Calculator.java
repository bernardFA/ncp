package com.fa.insito.poc3.calcengine;


import com.fa.insito.poc3.MoneyMoveSet;
import com.fa.insito.poc3.ProductSpec;

import java.util.Map;

public interface Calculator {

    public MoneyMoveSet calculate(ProductSpec productSpec);

}
