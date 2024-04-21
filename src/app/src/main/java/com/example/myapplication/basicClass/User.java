package com.example.myapplication.basicClass;

import java.util.ArrayList;
import java.util.List;

public class User {
    public final String UID;
    public String name;
    public String email;
    public String password;
    public String location;

    User(String UID, String name,String email, String password, String location) {
        this.UID = UID;
        this.name = name;
        this.password = password;
        this.location = location;
    }

}
