package com.example.myapplication.basicClass;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String headImage;
    private String location;

    public User() {

    }

    User(String id, String name, String email, String password, String headImage, String location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.headImage = headImage;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getHeadImage() {
        return headImage;
    }

    public String getLocation() {
        return location;
    }

}
