package com.example.myapplication.basicClass;

import com.example.myapplication.common.AVLTree;

import java.util.ArrayList;



/**
 * @author Wanzhong Wu
 * This is a parser for the search functionality
 */

public class Parser {
    private Tokenizer tokenizerLocation;
    private Tokenizer tokenizerCategory;
    private Tokenizer tokenizerName;
    boolean locationCheck = false;
    boolean categoryCheck = false;
    boolean nameCheck = false;

    // Initialize tokenizers for location, category, and name with the input text
    public Parser(String text) {
        this.tokenizerLocation = new Tokenizer(text);
        this.tokenizerCategory = new Tokenizer(text);
        this.tokenizerName = new Tokenizer(text);
    }

    /**
     * This function support this search functionality, it will keep checking the key words then
     * it will cut the avlTree to get the result.
     * @param avlTree
     * @return an avlTree sorted by the location
     */
    public AVLTree<Product> parseEXP(AVLTree<Product> avlTree) {

        AVLTree<Product> container = new AVLTree<>();
        while (tokenizerLocation.hasNext()) {
            Token.Type type = tokenizerLocation.current().getType();
            if (type == Token.Type.LOCATION) {
                locationCheck = true;
                String location = tokenizerLocation.current().getToken();
                ArrayList<Product> products = avlTree.convertToArrayList();
                for (Product product : products) {
                    String check = product.getLocation().toLowerCase();
                    if (check.contains(location) || location.contains(check)) {
                        container.insert(product);
                    }
                }
            }
            if (tokenizerLocation.hasNext()) {
                tokenizerLocation.next();
            }
        }
        if (!locationCheck) {
            return parseName(avlTree);
        }
        else {
            return parseName(container);
        }
    }

    /**
     * This function is to track the keyword with "Name" type.
     * @param avlTree
     * @return an avlTree sorted by the name
     */
    private AVLTree<Product> parseName(AVLTree<Product> avlTree) {
        AVLTree<Product> container = new AVLTree<>();
        while (tokenizerName.hasNext()) {
            Token.Type type = tokenizerName.current().getType();
            if (type == Token.Type.NAME) {
                nameCheck = true;
                String name = tokenizerName.current().getToken();
                ArrayList<Product> products = avlTree.convertToArrayList();
                for (Product product : products) {
                    String check = product.getName().toLowerCase();
                    if (name.contains(check) || check.contains(name) || checkNameTypo(name, check)) {
                        ArrayList<Product> productArrayList = container.convertToArrayList();
                        if (!productArrayList.contains(product)) {
                            container.insert(product);
                        }
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

        if (locationCheck && !categoryCheck) {
            return avlTree;
        }

        return container;
    }


    /**
     * This function is to track the keyword with category type.
     * @param avlTree
     * @return an avlTree sorted by the category
     */
    private AVLTree<Product> parseCategory(AVLTree<Product> avlTree) {
        AVLTree<Product> container = new AVLTree<>();
        while (tokenizerCategory.hasNext()) {
            Token.Type type = tokenizerCategory.current().getType();
            if (type == Token.Type.Category) {
                categoryCheck = true;
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

    /**
     * This one is to handle the invalid search in parser level.
     * @param input
     * @param check
     * @return is it a typo or not
     */
    private boolean checkNameTypo(String input, String check) {
        if (input.length() == check.length()) {
            int record = 0;
            for (int i = 0; i < input.length(); i++) {
                if (check.charAt(i) != input.charAt(i)) record++;
            }
            return record <= 2;
        }
        return false;
    }
}