package com.example.myapplication.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.ProductAdapter;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

public class TradePlatform extends Page {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_platform);

        // UI setup
        TextView homePage = findViewById(R.id.HomePage);
        TextView userPage = findViewById(R.id.UserPage);
        TextView tradePage = findViewById(R.id.TradePage);
        TextView favorite = findViewById(R.id.Favorite);

        // Product input
        EditText productName = findViewById(R.id.productName);
        EditText productPrice = findViewById(R.id.productPrice);
        EditText productCategory = findViewById(R.id.productCategory);
        EditText productDescription = findViewById(R.id.productDescription);
        Button submitButton = findViewById(R.id.submitButton);

        // Navigation listeners
        homePage.setOnClickListener(v -> goHomePage());
        userPage.setOnClickListener(v -> goUserPage());
        tradePage.setOnClickListener(v -> goTradePage());
        favorite.setOnClickListener(v -> goFavorite());

        // Initialize Firebase database reference

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://second-hand-market-affd5-default-rtdb.firebaseio.com/");
        mDatabase = database.getReference("Trade");

        // Submit button event to upload product data to Firebase
        submitButton.setOnClickListener(v -> {
            String name = productName.getText().toString().trim();
            String price = productPrice.getText().toString().trim();
            String category = productCategory.getText().toString().trim();
            String description = productDescription.getText().toString().trim();
            String productId = mDatabase.push().getKey();  // Generate unique ID for the product

            Product product = new Product(productId, category, description, price, "New", "", "Available", "");  // Assume defaults for unspecified fields

            mDatabase.child(productId).setValue(product)
                    .addOnSuccessListener(aVoid -> Toast.makeText(TradePlatform.this, "Product added successfully!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(TradePlatform.this, "Failed to add product", Toast.LENGTH_SHORT).show());
        });


    }
}
