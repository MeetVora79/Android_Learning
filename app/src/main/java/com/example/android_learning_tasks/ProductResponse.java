package com.example.android_learning_tasks;

import java.util.List;

public class ProductResponse {
    private List<ProductModel_w6_t1> products;
    private int total;
    private int skip;
    private int limit;

    public List<ProductModel_w6_t1> getProducts() {
        return products;
    }

    public int getTotal() {
        return total;
    }

    public int getSkip() {
        return skip;
    }

    public int getLimit() {
        return limit;
    }
}
