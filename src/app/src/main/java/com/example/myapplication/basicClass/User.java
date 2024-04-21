package com.example.myapplication.basicClass;

import java.util.ArrayList;
import java.util.List;

public class User {
    public final String UID;
    public String name;
    public String email;
    public String password;


    User(String UID, String name,String email, String password) {
        this.UID = UID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
