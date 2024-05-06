package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;
import com.example.myapplication.basicClass.User;

public class ProductPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        // Get the back button
        ImageView btnBack = findViewById(R.id.btnproductDetailPageBack);

        // Set click event listener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current Activity and return to the previous one
                finish();
            }
        });

        // Get the product information passed through
        Product product = (Product) getIntent().getSerializableExtra("product");

        // Get layout view components
        ImageView imageProduct = findViewById(R.id.imgProduct);
        TextView textProductTitle = findViewById(R.id.txtName);
        TextView textProductPrice = findViewById(R.id.txtPrice);
        TextView textProductDescription = findViewById(R.id.textProductDescription);
        TextView textProductCondition = findViewById(R.id.textProductCondition);

        ImageView imageSellerProfile = findViewById(R.id.imageSellerProfile);
        TextView textSellerName = findViewById(R.id.textSellerName);

        TextView textCityInfo = findViewById(R.id.textCityInfo);

        // Check if the product object and image link are valid
        if (product != null && product.getImgLink() != null) {
            // Use Glide library to load the image
            Glide.with(this)
                    .load(product.getImgLink())
                    .placeholder(R.drawable.white_background) // Use a placeholder image
                    .error(R.drawable.error_img) // Error image
                    .into(imageProduct);

            // Set text view contents
            textProductTitle.setText(product.getName());
            textProductPrice.setText("$ " +product.getPrice());
            textProductDescription.setText(product.getDescription());
            textProductCondition.setText("Condition: " + product.getCondition());
            textSellerName.setText("tom"); // Here you can set according to actual seller information
            textCityInfo.setText("Location: " +product.getLocation());
        } else {
            textProductTitle.setText("No Product Information");
        }

        // Load image URL
        String sellerImageUrl = "https://k.sinaimg.cn/n/sinakd20114/400/w600h600/20230302/abc3-e7cb9b5c7a8c6533bd650330e92d97ed.gif/w700d1q75cms.jpg";
        Glide.with(this)
                .load(sellerImageUrl) // Load the image from the given URL
                .apply(new RequestOptions().circleCrop()) // Apply a circular crop transformation to the image
                .placeholder(R.drawable.white_background) // Use a placeholder image while the main image is loading
                .error(R.drawable.error_img) // Use an error image if the main image fails to load
                .into(imageSellerProfile); // Set the loaded image into the specified ImageView
    }
}
