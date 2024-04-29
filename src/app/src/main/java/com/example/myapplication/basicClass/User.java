package com.example.myapplication.basicClass;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String UID;
    private String name;
    private String email;
    private String password;
    private String headURL;
    private String location;


    User(String UID, String name,String email, String password, String headURL, String location) {
        this.UID = UID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.headURL = headURL;
        this.location = location;
    }

    public String getUID() {
        return UID;
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

    public String getHeadURL() {
        return headURL;
    }

    public String getLocation() {
        return location;
    }

}
