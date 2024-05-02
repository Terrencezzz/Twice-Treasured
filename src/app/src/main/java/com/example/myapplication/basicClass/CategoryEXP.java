package com.example.myapplication.basicClass;

import java.util.ArrayList;

public class CategoryEXP extends EXP{
    @Override
    public ArrayList<Product> search(String keyword) {
        return productAVLTree.searchByPredicate(product -> product.getCategory().equalsIgnoreCase(keyword));
    }
}
