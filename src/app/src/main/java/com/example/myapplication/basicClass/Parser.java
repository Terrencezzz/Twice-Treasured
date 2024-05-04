package com.example.myapplication.basicClass;

import androidx.annotation.NonNull;

import com.example.myapplication.common.AVLTree;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Parser {
    private Tokenizer tokenizerLocation;
    private Tokenizer tokenizerCategory;
    private Tokenizer tokenizerName;
    private AVLTree<Product> productAVLTree = new AVLTree<>();
    boolean location = false;
    boolean category = false;
    boolean name = false;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product");


    public Parser(Tokenizer tokenizer) {
        this.tokenizerLocation = tokenizer;
    }

    // Initialize tokenizers for location, category, and name with the input text
    public Parser(String text) {
        this.tokenizerLocation = new Tokenizer(text);
        this.tokenizerCategory = new Tokenizer(text);
        this.tokenizerName = new Tokenizer(text);
    }

    // Parse based on location
    public AVLTree<Product> parseEXP(AVLTree<Product> avlTree) {
        productAVLTree = avlTree;
        AVLTree<Product> container = new AVLTree<>();
        while (tokenizerLocation.hasNext()) {
            Token.Type type = tokenizerLocation.current().getType();
            if (type == Token.Type.LOCATION) {
                location = true;
                ArrayList<Product> products = productAVLTree.convertToArrayList();
                for (Product product : products) {
                    if (product.getLocation().toLowerCase().equals(tokenizerLocation.current().getToken())) {
                        container.insert(product);
                    }
                }
            }
            tokenizerLocation.next();
        }
        if (!location) {
            return parseCategory(productAVLTree);
        }
        productAVLTree = container;
        return parseCategory(productAVLTree);
    }

    // Parse based on Category
    public AVLTree<Product> parseCategory(AVLTree<Product> avlTree) {
        AVLTree<Product> container = new AVLTree<>();
        while (tokenizerCategory.hasNext()) {
            Token.Type type = tokenizerCategory.current().getType();
            if (type == Token.Type.Category) {
                category = true;
                ArrayList<Product> products = productAVLTree.convertToArrayList();
                for (Product product : products) {
                    if (product.getCategory().toLowerCase().contains(tokenizerCategory.current().getToken())) {
                        container.insert(product);
                    }
                }
            }
            tokenizerCategory.next();
        }
        if (!category) {
            return parseName(productAVLTree);
        }
        productAVLTree = container;
        return parseName(productAVLTree);
    }

    // Parse based on Name
    public AVLTree<Product> parseName(AVLTree<Product> avlTree) {
        AVLTree<Product> container = new AVLTree<>();
        while(tokenizerName.hasNext()) {
            Token.Type type = tokenizerName.current().getType();
            if (type == Token.Type.NAME) {
                name = true;
                ArrayList<Product> products = productAVLTree.convertToArrayList();
                for (Product product : products) {
                    if (product.getCategory().toLowerCase().contains(tokenizerName.current().getToken())) {
                        container.insert(product);
                    }
                }
            }
            tokenizerName.next();
        }
        if (!location && !category && !name) {
            return new AVLTree<>();
        }
        else if (!name) {
            return productAVLTree;
        }
        productAVLTree = container;
        return productAVLTree;
    }
}