package com.example.myapplication.basicClass;

import com.example.myapplication.common.AVLTree;

import java.util.ArrayList;

public class Parser {
    private Tokenizer tokenizerLocation;
    private Tokenizer tokenizerCategory;
    private Tokenizer tokenizerName;
    boolean location = false;
    boolean category = false;
    boolean name = false;

    // Initialize tokenizers for location, category, and name with the input text
    public Parser(String text) {
        this.tokenizerLocation = new Tokenizer(text);
        this.tokenizerCategory = new Tokenizer(text);
        this.tokenizerName = new Tokenizer(text);
    }

    public AVLTree<Product> parseEXP(AVLTree<Product> avlTree) {

        AVLTree<Product> container = new AVLTree<>();
        while (tokenizerLocation.hasNext()) {
            Token.Type type = tokenizerLocation.current().getType();
            if (type == Token.Type.LOCATION) {
                location = true;
                String location = tokenizerLocation.current().getToken();
                ArrayList<Product> products = avlTree.convertToArrayList();
                for (Product product : products) {
                    String check = product.getLocation().toLowerCase();
                    if (check.equals(location)) {
                        container.insert(product);
                    }
                }
            }
            if (tokenizerLocation.hasNext()) {
                tokenizerLocation.next();
            }
        }
        if (!location) {
            return parseName(avlTree);
        }
        else {
            return parseName(container);
        }
    }

    private AVLTree<Product> parseName(AVLTree<Product> avlTree) {
        AVLTree<Product> container = new AVLTree<>();
        while (tokenizerName.hasNext()) {
            Token.Type type = tokenizerName.current().getType();
            if (type == Token.Type.NAME) {
                name = true;
                String name = tokenizerName.current().getToken();
                ArrayList<Product> products = avlTree.convertToArrayList();
                for (Product product : products) {
                    String check = product.getName().toLowerCase();
                    if (name.contains(check) || check.contains(name)) {
                        container.insert(product);
                    }
                }
            }
            if (tokenizerName.hasNext()) {
                tokenizerName.next();
            }
        }
        AVLTree<Product> category = parseCategory(avlTree);
        if (!category.isEmpty()) {
            ArrayList<Product> categoryList = category.convertToArrayList();
            ArrayList<Product> containerList = container.convertToArrayList();
            for (Product product : categoryList) {
                if (!containerList.contains(product)) {
                    container.insert(product);
                }
            }
        }

        if (!name) {
            return avlTree;
        }

        return container;
    }


    private AVLTree<Product> parseCategory(AVLTree<Product> avlTree) {
        AVLTree<Product> container = new AVLTree<>();
        while (tokenizerCategory.hasNext()) {
            Token.Type type = tokenizerCategory.current().getType();
            if (type == Token.Type.Category) {
                category = true;
                String category = tokenizerCategory.current().getToken();
                ArrayList<Product> products = avlTree.convertToArrayList();
                for (Product product : products) {
                    String check = product.getCategory().toLowerCase();
                    if (check.contains(category) || category.contains(check) || category.equals("clothes") && check.equals("clothing")) {
                        container.insert(product);
                    }
                }
            }
            if (tokenizerCategory.hasNext()) {
                tokenizerCategory.next();
            }
        }
        return container;
    }

}