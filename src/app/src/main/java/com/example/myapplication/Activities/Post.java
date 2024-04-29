package com.example.myapplication.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.ProductAdapter;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.HashMap;
import java.util.List;

public class Post extends Page {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();
    private ActivityResultLauncher<String> mGetContent;
    private String imageUri;

    private void uploadImageToFirebase(Uri uri) {
        // If you prefer to use a fixed file name, replace with your own logic
        StorageReference fileRef = storageReference.child("uploads/" + System.currentTimeMillis() + ".jpg");
        fileRef.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(Post.this, "Image Upload Successful", Toast.LENGTH_SHORT).show();
                    fileRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                         imageUri = downloadUri.toString();
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Post.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // UI setup
        Button back = findViewById(R.id.button_back);

        // Product input
        EditText productName = findViewById(R.id.productName);
        EditText productPrice = findViewById(R.id.productPrice);
        EditText productCategory = findViewById(R.id.productCategory);
        EditText productDescription = findViewById(R.id.productDescription);
        Button submitButton = findViewById(R.id.submitButton);
        Button imageButton = findViewById(R.id.image_button);
        ImageView imageView = findViewById(R.id.imageView);


        // Navigation listeners
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Post.this, HomePage.class));
                finish();
            }
        });



        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            // Handle the returned Uri
            if (uri != null) {
                imageView.setImageURI(uri); // Set the imageView to the picked image

                // Now upload it to Firebase Storage
                uploadImageToFirebase(uri);
            }
            else {
                Toast.makeText(this, "Select this image!", Toast.LENGTH_SHORT).show();
            }
        });

        imageButton.setOnClickListener(v -> mGetContent.launch("image/*"));

        // Submit button event to upload product data to Firebase
        submitButton.setOnClickListener(v -> {

            String name = productName.getText().toString().trim();
            String price = productPrice.getText().toString().trim();
            String category = productCategory.getText().toString().trim();
            String description = productDescription.getText().toString().trim();
            String productId = mDatabase.push().getKey();  // Generate unique ID for the product

            Product product = new Product(productId, category, description, price, "New", "", "Available", imageUri);  // Assume defaults for unspecified fields

            mDatabase.child("userProduct").child(productId).setValue(product)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Post.this, "Product added successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Post.this, HomePage.class));
                    })
                    .addOnFailureListener(e -> Toast.makeText(Post.this, "Failed to add product", Toast.LENGTH_SHORT).show());
        });
    }
}
