package com.example.myapplication.basicClass;

import java.util.ArrayList;

/**
 * Represents a log of a favorite product for a specific observer
 */
public class FavoriteLog {
    final private Observer observer;
    final private Product favoriteProduct;

    // Constructor to initialize FavoriteLog with a favorite product and observer
    public FavoriteLog(Product favoriteProduct,Observer observer) {
        this.favoriteProduct = favoriteProduct;
        this.observer = observer;
    }

    // Method to get the observer associated with this favorite log
    public Observer getObserver() {
        return observer;
    }

    // Method to get the favorite product associated with this favorite log
    public Product getFavoriteProduct() {
        return favoriteProduct;
    }

}