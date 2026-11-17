package com.example.android_learning_tasks;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "products")
public class ProductModel_w6_t1 {
    @PrimaryKey
    private int id;
    private String title;
    private double price;
    private String thumbnail;
    private String category;
    private String description;
    @Ignore
    private List<String> images;

    public ProductModel_w6_t1(){}

    public ProductModel_w6_t1(String title, Double price, String category) {
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
