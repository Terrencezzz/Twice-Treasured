package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.ProductsManageAdapter;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity allows users to manage their own products. Users can view a list of products they have posted
 * for sale and perform actions such as editing or deleting these products. It also provides bottom navigation to
 * easily navigate to other sections of the app.
 *
 * @author Xiaojie Zhou (u7769944)
 */
public class ProductsManagePage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductsManageAdapter productsManageAdapter;
    private List<Product> productList;
    private ImageButton returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_manage_page); // Set the layout for this activity

        returnButton = findViewById(R.id.returnButton); // Find the ImageButton with ID returnButton
        recyclerView = findViewById(R.id.product_management_list); // Find the RecyclerView with ID product_management_list

        LinearLayoutManager layoutManager = new LinearLayoutManager(this); // Create a LinearLayoutManager
        recyclerView.setLayoutManager(layoutManager); // Set the RecyclerView's layout manager

        productList = new ArrayList<>(); // Initialize productList as an ArrayList
        productsManageAdapter = new ProductsManageAdapter(this, productList); // Create a new ProductsManageAdapter
        recyclerView.setAdapter(productsManageAdapter); // Set the adapter for the RecyclerView

        loadUserOwnedProducts(); // Call method to load user-owned products

        returnButton.setOnClickListener(v -> finish());
    }

    // Load products owned by the current user
    private void loadUserOwnedProducts() {
        String currentUserId = GlobalVariables.getInstance().getLoginUser().getId(); // Get current user's ID
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference("Product"); // Get reference to "Product" node in Firebase database

        // Query Firebase to get products owned by current user
        productsRef.orderByChild("ownerID").equalTo(currentUserId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) { // If no data exists, return
                            return;
                        }

                        productList.clear(); // Clear productList
                        // Iterate through each snapshot and add product to productList
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);
                            if (product != null) {
                                productList.add(product);
                            }
                        }
                        productsManageAdapter.notifyDataSetChanged(); // Notify adapter that data set has changed
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors
                    }
                });
    }


}
