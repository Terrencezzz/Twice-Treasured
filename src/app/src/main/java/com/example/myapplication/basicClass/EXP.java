package com.example.myapplication.basicClass;

import androidx.annotation.NonNull;

import com.example.myapplication.common.AVLTree;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public abstract class EXP {
    public DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    public void  fetchProductsAndBuildTree() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AVLTree<Product> productAVLTree = new AVLTree<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    productAVLTree.insert(product);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

}
