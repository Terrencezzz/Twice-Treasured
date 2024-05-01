package com.example.myapplication.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Post extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();
    private ActivityResultLauncher<String> mGetContent;
    private String imageUri;

    // Method to upload image to Firebase and handles UI response
    private void uploadImageToFirebase(Uri uri) {
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
        ImageButton back = findViewById(R.id.button_back);
        Spinner categorySpinner = findViewById(R.id.productCategorySpinner);
        Button submitButton = findViewById(R.id.submitButton);
        Button imageButton = findViewById(R.id.image_button);
        ImageView imageView = findViewById(R.id.imageView);

        // Populate Spinner with categories for product category selection
        String[] categories = {"Electronics", "Clothing", "Furniture", "Books", "Sports", "Toys", "Beauty", "Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(adapter);

        // Navigation listeners
        back.setOnClickListener(v -> {
            startActivity(new Intent(Post.this, HomePage.class));
            finish();
        });

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                imageView.setImageURI(uri);
                uploadImageToFirebase(uri);
            } else {
                Toast.makeText(this, "Select this image!", Toast.LENGTH_SHORT).show();
            }
        });

        imageButton.setOnClickListener(v -> mGetContent.launch("image/*"));

        // Submit button event to upload product data to Firebase
        submitButton.setOnClickListener(v -> {
            String name = ((TextView) findViewById(R.id.productName)).getText().toString().trim();
            String price = ((TextView) findViewById(R.id.productPrice)).getText().toString().trim();
            String category = categorySpinner.getSelectedItem().toString();
            String description = ((TextView) findViewById(R.id.productDescription)).getText().toString().trim();
            String productId = mDatabase.push().getKey();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String owner = user.getUid();

            Product product = new Product(productId, category, description, price, "New", "2024-01-01", "Available", imageUri, owner, "0");
            mDatabase.child("Product").child(productId).setValue(product)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Post.this, "Product added successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Post.this, HomePage.class));
                    })
                    .addOnFailureListener(e -> Toast.makeText(Post.this, "Failed to add product", Toast.LENGTH_SHORT).show());
        });
    }
}
