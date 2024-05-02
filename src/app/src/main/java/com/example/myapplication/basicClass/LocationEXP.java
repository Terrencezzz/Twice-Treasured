package com.example.myapplication.basicClass;

import java.util.ArrayList;

public class LocationEXP extends EXP {
    @Override
    public ArrayList<Product> search(String keyword) {
        // Filtering logic for locations
        return productAVLTree.searchByPredicate(product -> product.getLocation().equalsIgnoreCase(keyword));
    }
}
