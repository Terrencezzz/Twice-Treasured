package com.example.myapplication.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // UI setup
        ImageButton back = findViewById(R.id.button_back);
        Spinner categorySpinner = findViewById(R.id.productCategorySpinner);
        Spinner conditionSpinner = findViewById(R.id.productConditionSpinner);
        Button submitButton = findViewById(R.id.submitButton);
        Button imageButton = findViewById(R.id.image_button);
        ImageView imageView = findViewById(R.id.imageView);

        setupSpinner(categorySpinner, new String[]{"Select Product Category", "Electronics", "Clothing", "Furniture", "Books", "Sports", "Toys", "Beauty", "Others"});
        setupSpinner(conditionSpinner, new String[]{"Select Product Condition", "New", "Used"});

        // Navigation listeners
        back.setOnClickListener(v -> navigateHome());
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> handleImageSelection(uri, imageView));
        imageButton.setOnClickListener(v -> mGetContent.launch("image/*"));
        submitButton.setOnClickListener(v -> submitProduct(categorySpinner, conditionSpinner));
    }

    private void setupSpinner(Spinner spinner, String[] options) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };
        spinner.setAdapter(adapter);
    }

    private void handleImageSelection(Uri uri, ImageView imageView) {
        if (uri != null) {
            imageView.setImageURI(uri);
            uploadImageToFirebase(uri);
        } else {
            showToast("Select this image!");
        }
    }

    private void navigateHome() {
        startActivity(new Intent(Post.this, HomePage.class));
        finish();
    }

    private void submitProduct(Spinner categorySpinner, Spinner conditionSpinner) {
        String name = ((TextView) findViewById(R.id.productName)).getText().toString().trim();
        String price = ((TextView) findViewById(R.id.productPrice)).getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();
        String condition = conditionSpinner.getSelectedItem().toString();
        String description = ((TextView) findViewById(R.id.productDescription)).getText().toString().trim();
        String productId = mDatabase.push().getKey();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String owner = user.getUid();

        Product product = new Product(name, productId, category, description, price, condition, "2024-01-01", "Available", imageUri, owner, "0");
        mDatabase.child("Product").child(productId).setValue(product)
                .addOnSuccessListener(aVoid -> {
                    showToast("Product added successfully!");
                    navigateHome();
                })
                .addOnFailureListener(e -> showToast("Failed to add product"));
    }

    private void showToast(String message) {
        Toast.makeText(Post.this, message, Toast.LENGTH_SHORT).show();
    }

    private void uploadImageToFirebase(Uri uri) {
        StorageReference fileRef = storageReference.child("uploads/" + System.currentTimeMillis() + ".jpg");
        fileRef.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    showToast("Image Upload Successful");
                    fileRef.getDownloadUrl().addOnSuccessListener(downloadUri -> imageUri = downloadUri.toString());
                })
                .addOnFailureListener(e -> showToast("Image Upload Failed"));
    }
}
