package com.example.android_learning_tasks;

import java.util.List;

public class ProductModel_w6_t1 {
    private int id;
    private String title;
    private double price;
    private String thumbnail;
    private String category;
    private String description;
    private List<String> images;

    public ProductModel_w6_t1(){}

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImages() {
        return images;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getId() {
        return id;
    }
}
