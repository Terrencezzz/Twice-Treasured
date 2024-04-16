package com.example.myapplication.basicClass;

public class Product {
    private final int productID;
    private String productName;
    private int price;

    Product(int productID, String productName, int price) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
    }
}
