package com.example.myapplication.basicClass;

import androidx.annotation.NonNull;

import com.example.myapplication.common.AVLTree;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
        // Initialize all tokenizers with the input text converted to lower case to ensure case-insensitive comparisons
        String lowerText = text.toLowerCase();
        this.tokenizerLocation = new Tokenizer(lowerText);
        this.tokenizerCategory = new Tokenizer(lowerText);
        this.tokenizerName = new Tokenizer(lowerText);
    }

    // Asynchronously parses products based on location and synchronizes with further parsing steps
    public CompletableFuture<AVLTree<Product>> parseEXPAsync() {
        CompletableFuture<AVLTree<Product>> future = new CompletableFuture<>();

        while(tokenizerLocation.hasNext()) {
            Token token = tokenizerLocation.current();
            Token.Type type = token.getType();
            if (type == Token.Type.LOCATION) {
                String key = token.getToken();
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
                        future.complete(productAVLTree);  // Complete the future when data is fully loaded
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        future.completeExceptionally(new RuntimeException("Firebase query cancelled"));
                    }
                });
            }
            tokenizerLocation.next();  // Move to the next token to avoid infinite loops
        }

        return future;
    }

    // Synchronizes asynchronous operations and continues with category and name parsing
    public AVLTree<Product> parse() throws ExecutionException, InterruptedException {
        parseEXPAsync().get();  // Wait for the location-based asynchronous parsing to complete
        parseCategory();
        parseName();
        return productAVLTree;
    }

    // Parses products based on category from the pre-filtered AVL tree
    private void parseCategory() {
        AVLTree<Product> container = new AVLTree<>();
        for (Product product : productAVLTree.convertToArrayList()) {
            String category = product.getCategory().toLowerCase();  // Compare in lower case
            while (tokenizerCategory.hasNext()) {
                String key = tokenizerCategory.current().getToken();
                if (category.equals(key)) {
                    container.insert(product);
                }
                tokenizerCategory.next();
            }
        }
        productAVLTree = container;
    }

    // Parses products based on name from the pre-filtered AVL tree
    private void parseName() {
        AVLTree<Product> container = new AVLTree<>();
        for (Product product : productAVLTree.convertToArrayList()) {
            String name = product.getName().toLowerCase();  // Compare in lower case
            while (tokenizerName.hasNext()) {
                String key = tokenizerName.current().getToken();
                if (name.equals(key)) {
                    container.insert(product);
                }
                tokenizerName.next();
            }
        }
        productAVLTree = container;
    }

    // Provides access to the final AVL tree of products
    public AVLTree<Product> getProductAVLTree() {
        return productAVLTree;
    }
}
