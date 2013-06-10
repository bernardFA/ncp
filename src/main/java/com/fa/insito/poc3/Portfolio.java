package com.fa.insito.poc3;

import com.fa.insito.poc3.framework.CumulativeFunction;
import com.fa.insito.poc3.framework.Function;
import com.fa.insito.poc3.framework.Specification;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class Portfolio {

    Set<Product> products = new HashSet<Product>();

    public <T> Set<T> extract(final Specification<Product> spec, final CumulativeFunction<Product, T> function) {
        final Set<T> res = Sets.newHashSet(); //new HashSet<T>();
        for (Product product : products)
            if (spec.isSatisfiedBy(product))
                function.apply(product, new CumulativeFunction.ResultSet<T>() {
                    @Override
                    public void add(T t) {
                        res.add(t);
                    }
                }
        );
        return res;
    }

    public <T> Set<T> extract(final Specification<Product> spec, final Function<Product, T> function) {
        final Set<T> res = Sets.newHashSet(); // new HashSet<T>();
        for (Product product : products)
            if (spec.isSatisfiedBy(product))
                res.add(function.apply(product));
        return res;
    }
    
    public Set<Product> extract(final Specification<Product> spec) {
        return extract(spec, new Function<Product, Product>() {
            @Override
            public Product apply(Product Product) {
                return Product;
            }
        });
    }

}
