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
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


    public Parser(Tokenizer tokenizer) {
        this.tokenizerLocation = tokenizer;
    }

    public Parser(String text) {
        this.tokenizerLocation = new Tokenizer(text);
        this.tokenizerCategory = new Tokenizer(text);
        this.tokenizerName = new Tokenizer(text);
    }

    public AVLTree<Product> parseEXP() {
        while(tokenizerLocation.hasNext()) {
            Token.Type type = tokenizerLocation.current().getType();
            if (type == Token.Type.LOCATION) {
                String key = tokenizerLocation.current().getToken();
                Query query = reference.orderByChild("location").equalTo(key);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Product product = dataSnapshot.getValue(Product.class);
                            if (product != null) {
                                productAVLTree.insert(product);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        return parseCategory();
    }

    public AVLTree<Product> parseCategory() {
        AVLTree<Product> container = new AVLTree<>();
        ArrayList<Product> products = productAVLTree.convertToArrayList();
        while(tokenizerCategory.hasNext()) {
            Token.Type type = tokenizerCategory.current().getType();
            if (type == Token.Type.Category) {
                String key = tokenizerCategory.current().getToken();
                for (Product product : products) {
                    String category = product.getCategory();
                    if (category.toLowerCase().equals(key)) {
                        container.insert(product);
                    }
                }
            }
        }
        productAVLTree = container;
        return parseName();
    }

    public AVLTree<Product> parseName() {
        AVLTree<Product> container = new AVLTree<>();
        ArrayList<Product> products = productAVLTree.convertToArrayList();
        while(tokenizerName.hasNext()) {
            Token.Type type = tokenizerName.current().getType();
            if (type == Token.Type.NAME) {
                String key = tokenizerName.current().getToken();
                for (Product product : products) {
                    String name = product.getName();
                    if (name.toLowerCase().equals(key)) {
                        container.insert(product);
                    }
                }
            }
        }
        productAVLTree = container;
        return productAVLTree;
    }

    public AVLTree<Product> getProductAVLTree() {
        return productAVLTree;
    }
}
