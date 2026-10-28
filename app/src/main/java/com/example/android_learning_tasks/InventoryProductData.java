package com.example.android_learning_tasks;

public class InventoryProductData {
    private double price;
    private int stock;

    public InventoryProductData(double price, int stock) {
        this.price = price;
        this.stock = stock;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}