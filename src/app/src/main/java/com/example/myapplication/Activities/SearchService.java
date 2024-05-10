package com.example.myapplication.Activities;

import androidx.annotation.NonNull;

import com.example.myapplication.basicClass.Product;
import com.example.myapplication.basicClass.Parser;
import com.example.myapplication.common.AVLTree;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.function.Consumer;


public class SearchService {

    private DatabaseReference reference;

    public SearchService() {
        reference = FirebaseDatabase.getInstance().getReference("Product");
    }

    // Search for products by a search string and use a callback to return results
    public void FindProduct(String searchString, Consumer<ArrayList<Product>> callback) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AVLTree<Product> avlTree = new AVLTree<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product != null) {
                        avlTree.insert(product);
                    }
                }
                Parser parser = new Parser(searchString);
                avlTree = parser.parseEXP(avlTree);
                callback.accept(avlTree.convertToArrayList());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors more robustly
            }
        });
    }

}
