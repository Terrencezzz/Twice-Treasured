package com.example.myapplication.basicClass;

import java.io.Serializable;

public class Favorite implements Serializable {
    private String favoriteID;
    private String userID;
    private String productID;
    private boolean favoriteStatus;

    // Default constructor
    public Favorite() {}

    // Constructor with all parameters
    public Favorite(String favoriteID, String userID, String productID, boolean favoriteStatus) {
        this.favoriteID = favoriteID;
        this.userID = userID;
        this.productID = productID;
        this.favoriteStatus = favoriteStatus;
    }

    // Getter and Setter methods
    public String getFavoriteID() {
        return favoriteID;
    }

    public void setFavoriteID(String favoriteID) {
        this.favoriteID = favoriteID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public boolean isFavoriteStatus() {
        return favoriteStatus;
    }

    public void setFavoriteStatus(boolean favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }
}
