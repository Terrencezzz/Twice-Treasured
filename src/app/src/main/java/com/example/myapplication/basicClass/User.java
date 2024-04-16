package com.example.myapplication.basicClass;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final int UID;
    private String name;
    private List<String> products;

    User(int UID, String name) {
        this.UID = UID;
        this.name = name;
        products = new ArrayList<>();
    }

}
