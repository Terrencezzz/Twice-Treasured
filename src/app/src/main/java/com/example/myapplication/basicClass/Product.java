package com.example.myapplication.basicClass;


public class Product {
    private String productID;
    private String category;
    private String description;
    private String price;
    private String condition;
    private String uploadDate;
    private String status;
    private String imgLink;
    private String name;
    private String owner;
    private String categoryID;



    // Constructor to initialize the Product object with all attributes
    public Product(String productID, String category, String description, String price,
                   String condition, String uploadDate, String status, String imgLink, String owner,
                   String categoryID) {
        this.productID = productID;
        this.category = category;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.uploadDate = uploadDate;
        this.status = status;
        this.imgLink = imgLink;
        this.owner = owner;
        this.categoryID = categoryID;
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

    public String getOwner() {
        return owner;
    }

    public String getCategoryID() {
        return categoryID;
    }
}

