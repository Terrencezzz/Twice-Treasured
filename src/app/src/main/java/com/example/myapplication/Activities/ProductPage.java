package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Database;
import com.example.myapplication.basicClass.Favorite;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.Notice;
import com.example.myapplication.basicClass.NoticeFactory;
import com.example.myapplication.basicClass.Product;
import com.example.myapplication.basicClass.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProductPage extends AppCompatActivity {
    private DatabaseReference favoriteRef;
    private DatabaseReference userRef;
    private User currentUser;
    private GlobalVariables globalVars;
    private ImageView productFavorite;
    private boolean isFavorited;
    private String favoriteID;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        globalVars = GlobalVariables.getInstance();
        currentUser = globalVars.getLoginUser();
        favoriteRef = Database.getDatabase().getReference().child("Favorite");
        userRef = Database.getDatabase().getReference().child("User");

        // Get the back button
        ImageView btnBack = findViewById(R.id.btnproductDetailPageBack);
        btnBack.setOnClickListener(v -> finish());

        // Get the passed product information
        product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            displayProductDetails(product);
        }

        // Favorite button
        productFavorite = findViewById(R.id.product_detail_favorite);
        checkFavoriteStatus(currentUser, product);
        productFavorite.setOnClickListener(v -> toggleFavoriteStatus(currentUser, product));
    }

    private void displayProductDetails(Product product) {
        ImageView imageProduct = findViewById(R.id.imgProduct);
        TextView textProductTitle = findViewById(R.id.txtName);
        TextView textProductPrice = findViewById(R.id.txtPrice);
        TextView textProductDescription = findViewById(R.id.textProductDescription);
        TextView textProductCondition = findViewById(R.id.textProductCondition);
        ImageView imageSellerProfile = findViewById(R.id.imageSellerProfile);
        TextView textSellerName = findViewById(R.id.textSellerName);
        TextView textCityInfo = findViewById(R.id.textCityInfo);
        TextView textSellerDetails = findViewById(R.id.textSellerDetails);

        // Check if product object and image link are valid
        if (product != null && product.getImgLink() != null) {
            // Load image using Glide library
            Glide.with(this)
                    .load(product.getImgLink())
                    .placeholder(R.drawable.white_background) // Use placeholder image
                    .error(R.drawable.error_img) // Error image
                    .into(imageProduct);

            // Set text view content
            textProductTitle.setText(product.getName());
            textProductPrice.setText("$ " + product.getPrice());
            textProductDescription.setText(product.getDescription());
            textProductCondition.setText("Condition: " + product.getCondition());
            textCityInfo.setText("Location: " + product.getLocation());

            // Check if ownerID is not empty
            if (product.getOwnerID() != null) {
                // Get seller information based on ownerID
                userRef.child(product.getOwnerID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        User seller = snapshot.getValue(User.class);
                        if (seller != null) {
                            textSellerName.setText(seller.getName());
                            String sellerImageUrl = seller.getHeadImage();
                            if (sellerImageUrl != null && !sellerImageUrl.isEmpty()) {
                                Glide.with(ProductPage.this)
                                        .load(sellerImageUrl)
                                        .apply(new RequestOptions().circleCrop()) // Apply circular cropping
                                        .placeholder(R.drawable.white_background)
                                        .error(R.drawable.error_img)
                                        .into(imageSellerProfile);
                            }
                        } else {
                            textSellerName.setText("Unknown Seller");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.e("ProductPage", "Firebase Read Error: " + error.getMessage());
                    }
                });

                textSellerDetails.setOnClickListener(v -> {
                    Intent intent = new Intent(ProductPage.this, UserPage.class);
                    intent.putExtra("ownerID", product.getOwnerID());
                    startActivity(intent);
                });


            } else {
                textSellerName.setText("Unknown Seller");
            }
        } else {
            textProductTitle.setText("No Product Information");
        }
    }



        // Check favorite status
        private void checkFavoriteStatus (User user, Product product){

            if (user == null) {
                updateFavoriteButton(false);
                return;
            }

            favoriteRef.orderByChild("userID").equalTo(user.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Favorite favorite = snapshot.getValue(Favorite.class);
                        if (favorite != null && favorite.getProductID().equals(product.getProductID()) && favorite.isFavoriteStatus()) {
                            favoriteID = snapshot.getKey();
                            isFavorited = true;
                            updateFavoriteButton(true);
                            return;
                        }
                    }
                    isFavorited = false;
                    updateFavoriteButton(false);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("ProductPage", "Firebase Read Error: " + databaseError.getMessage());
                }
            });
        }

        // Update favorite button state
        private void updateFavoriteButton ( boolean isFavorited){
            productFavorite.setImageResource(isFavorited ? R.drawable.icon_favorite_click : R.drawable.icon_favorite_unclick);
        }

        // Toggle favorite status
        private void toggleFavoriteStatus (User user, Product product){
            if (user == null) {
                Toast.makeText(this, "Please log in to add to favorite", Toast.LENGTH_LONG).show();
                return;
            }

            if (isFavorited) {
                favoriteRef.child(favoriteID).child("favoriteStatus").setValue(false)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Favorite removed", Toast.LENGTH_SHORT).show();
                            updateFavoriteButton(false);
                            isFavorited = false;
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, "Failed to remove favorite: " + e.getMessage(), Toast.LENGTH_LONG).show());
            } else {
                favoriteID = favoriteRef.push().getKey();
                Favorite favorite = new Favorite(favoriteID, user.getId(), product.getProductID(), true);
                favoriteRef.child(favoriteID).setValue(favorite)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
                            updateFavoriteButton(true);
                            isFavorited = true;
                            //try to add favorite notice
                            NoticeFactory factory = new NoticeFactory();
                            Notice userNotice = factory.createNotice("Favorite");
                            userNotice.addNotice(product.getProductID());
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, "Failed to add to favorites: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }

    }
