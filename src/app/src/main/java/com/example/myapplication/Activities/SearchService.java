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

    private final DatabaseReference reference;

    public SearchService() {
        reference = FirebaseDatabase.getInstance().getReference("Product");
    }

    // Data loading from Firebase Product
    private void loadData(Consumer<DataSnapshot> onDataLoaded) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                onDataLoaded.accept(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database error: " + error.getMessage());
            }
        });
    }

    // Process data after loading
    public void FindProduct(String searchString, Consumer<ArrayList<Product>> callback) {
        loadData(snapshot -> {
            AVLTree<Product> avlTree = processProducts(snapshot, searchString);
            callback.accept(avlTree.convertToArrayList());
        });
    }

    // Method to find new products based on a search string and process them through AVLTree
    public void FindNewProduct(String searchString, Consumer<ArrayList<Product>> callback) {
        loadData(snapshot -> {
            AVLTree<Product> avlTree = processNewProducts(snapshot, searchString);
            callback.accept(avlTree.convertToArrayList());
        });
    }

    // Method to find used products based on a search string and process them through AVLTree
    public void FindUsedProduct(String searchString, Consumer<ArrayList<Product>> callback) {
        loadData(snapshot -> {
            AVLTree<Product> avlTree = processUsedProducts(snapshot, searchString);
            callback.accept(avlTree.convertToArrayList());
        });
    }


    // Process products using AVL tree and parser
    private AVLTree<Product> processProducts(DataSnapshot snapshot, String searchString) {
        AVLTree<Product> avlTree = new AVLTree<>();
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            Product product = dataSnapshot.getValue(Product.class);
            if (product != null) {
                avlTree.insert(product);
            }
        }
        Parser parser = new Parser(searchString);
        return parser.parseEXP(avlTree);
    }

    // Process products using AVL tree and filter by condition "New"
    private AVLTree<Product> processNewProducts(DataSnapshot snapshot, String searchString) {
        AVLTree<Product> avlTree = new AVLTree<>();
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            Product product = dataSnapshot.getValue(Product.class);
            if (product != null && "New".equals(product.getCondition())) {
                avlTree.insert(product);
            }
        }
        Parser parser = new Parser(searchString);
        return parser.parseEXP(avlTree);
    }


    // Process products using AVL tree and filter by condition not "New" (considered as "Used")
    private AVLTree<Product> processUsedProducts(DataSnapshot snapshot, String searchString) {
        AVLTree<Product> avlTree = new AVLTree<>();
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            Product product = dataSnapshot.getValue(Product.class);
            if (product != null && !"New".equals(product.getCondition())) {
                avlTree.insert(product);
            }
        }
        Parser parser = new Parser(searchString);
        return parser.parseEXP(avlTree);
    }


    public void FindProductsAscendingOrder(String searchString, Consumer<ArrayList<Product>> callback) {
        loadData(snapshot -> {
            AVLTree<Product> avlTree = processProducts(snapshot, searchString);
            ArrayList<Product> sortedList = avlTree.convertToAscendingArrayList();
            callback.accept(sortedList);
        });
    }

    public void FindProductsDescendingOrder(String searchString, Consumer<ArrayList<Product>> callback) {
        loadData(snapshot -> {
            AVLTree<Product> avlTree = processProducts(snapshot, searchString);
            ArrayList<Product> sortedList = avlTree.convertToDescendingArrayList();
            callback.accept(sortedList);
        });
    }
}
