package com.example.myapplication.basicClass;

import java.io.Serializable;

/**
 * This page is for the product constructor
 * @author Wanzhong Wu, Qin, u7769944
 */
public class Product implements Comparable<Product>, Serializable{
    private String name;
    private String productID;
    private String category;
    private String description;
    private String price;
    private String condition;
    private String uploadDate;
    private String status;
    private String imgLink;
    private String ownerID;

    private String categoryID;
    private String location;



    // Constructor to initialize the Product object with all attributes
    public Product(String name, String productID, String category, String description, String price,
                   String condition, String uploadDate, String status, String imgLink, String ownerID,
                   String categoryID, String location) {
        this.name = name;
        this.productID = productID;
        this.category = category;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.uploadDate = uploadDate;
        this.status = status;
        this.imgLink = imgLink;
        this.ownerID = ownerID;
        this.categoryID = categoryID;
        this.location = location;
    }

    public Product() {
        // Default constructor required for calls to DataSnapshot.getValue(Product.class)
    }


    // Getter and setter methods for all properties
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategoryID() {
        return categoryID;
    }
    public String getLocation() {return location;}

    @Override
    public int compareTo(Product other) {
        return Integer.compare(Integer.parseInt(this.price), Integer.parseInt(other.price));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + productID.hashCode();
        return result;
    }
}

