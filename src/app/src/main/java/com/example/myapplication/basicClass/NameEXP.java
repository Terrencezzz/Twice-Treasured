package com.example.myapplication.basicClass;

import java.util.ArrayList;

public class NameEXP extends EXP {
    @Override
    public ArrayList<Product> search(String keyword) {
        // Filtering logic for names
        return productAVLTree.searchByPredicate(product -> product.getName().equalsIgnoreCase(keyword));
    }
}
