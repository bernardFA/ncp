package com.fa.insito.poc3.framework;

import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class WeightedAverage {

    List<Element> elements = Lists.newArrayList();

    public void add(double value, double weight) {
        elements.add(new Element(value, weight));
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public double compute() {
        if (sumOfWeight() == 0.0) {
            throw new IllegalStateException("Weight sum is equal to zero. Cannot compute weighted average");
        }
        return sumOfWeightedValues() / sumOfWeight();
    }

    private double sumOfWeightedValues() {
        double result = 0.0;
        for (Element element : elements) {
            result += element.getWeightedValue();
        }
        return result;
    }

    private double sumOfWeight() {
        double result = 0.0;
        for (Element element : elements) {
            result += element.weight;
        }
        return result;
    }

    private class Element {
        private double value;
        private double weight;

        public double getWeightedValue() {
            return weight * value;
        }

        private Element(double value, double weight) {
            checkArgument(weight > 0, "The weight %s must be strictly superior to zero", weight);
            this.value = value;
            this.weight = weight;
        }
    }

}
