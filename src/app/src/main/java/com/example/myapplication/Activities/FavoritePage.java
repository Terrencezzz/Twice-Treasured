package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.FavoriteAdapter;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Database;
import com.example.myapplication.basicClass.Favorite;
import com.example.myapplication.basicClass.GlobalVariables;
import com.example.myapplication.basicClass.Product;
import com.example.myapplication.basicClass.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * In this page people can check their favorite products.
 */
public class FavoritePage extends Page {
    // UI elements
    private TextView tvTitle;
    private RecyclerView rvFavorite;
    private ImageButton btn_return_to_home;
    private FavoriteAdapter adapter;
    private CheckBox selectAllCheckbox;
    private Button btnManage;
    private Button btnDelete;
    private LinearLayout bottomBar;
    private ConstraintLayout clPrivate;
    private ConstraintLayout clHome;
    private ConstraintLayout clMe;
    private ConstraintLayout clFavorite;
    private Button btnTradePlatform;
    private DatabaseReference favoriteRef;
    private DatabaseReference productRef;
    private User currentUser;
    private GlobalVariables globalVars;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        globalVars = GlobalVariables.getInstance();
        currentUser = globalVars.getLoginUser();
        favoriteRef = Database.getDatabase().getReference().child("Favorite");
        productRef = Database.getDatabase().getReference().child("Product");

        // Initializing UI elements
        tvTitle = findViewById(R.id.tvTitle);
        btn_return_to_home = findViewById(R.id.btn_return_to_home);
        btn_return_to_home.setOnClickListener(v -> goHomePage());
        pbLoading = findViewById(R.id.pbLoading);

        rvFavorite = findViewById(R.id.rvFavorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));

        selectAllCheckbox = findViewById(R.id.checkbox_select_all);
        btnManage = findViewById(R.id.btnManage);
        btnDelete = findViewById(R.id.btnDelete);
        bottomBar = findViewById(R.id.bottom_bar);

        clPrivate = findViewById(R.id.clPrivate);
        clHome = findViewById(R.id.clHome);
        clMe = findViewById(R.id.clMe);
        btnTradePlatform = findViewById(R.id.btnTradePlatform);
        clFavorite = findViewById(R.id.clFavorite);

        // Initialize favorite products list and set up UI
        initFavoriteProducts();
        setupManageMode();
        setupSelectAllFeature();
        updateSelectAllVisibility(false);
        initFavoriteProducts();
    }


    // Initialize favorite products list
    private void initFavoriteProducts() {
        pbLoading.setVisibility(View.VISIBLE);
        adapter = new FavoriteAdapter(new ArrayList<>(),favoriteRef, currentUser, this::handleProductClick);
        rvFavorite.setAdapter(adapter);
        loadFavoriteProducts(favoriteProducts -> {
            adapter.setFavoriteItemList(favoriteProducts);
            pbLoading.setVisibility(View.GONE);
            updateSelectAllVisibility(!favoriteProducts.isEmpty());
        });
    }

    // Handle click on a product
    public void handleProductClick(Product product) {
        Intent intent = new Intent(FavoritePage.this, ProductPage.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    // Set up management mode (for deleting items)
    private void setupManageMode() {
        btnManage.setOnClickListener(v -> {
            try {
                if (adapter.isManageMode()) {
                    adapter.toggleManageMode();
                    btnManage.setText("Manage");
                    btnDelete.setVisibility(View.GONE);
                    updateSelectAllVisibility(false);
                } else {
                    adapter.toggleManageMode();
                    btnManage.setText("Finish");
                    btnDelete.setVisibility(View.VISIBLE);
                    updateSelectAllVisibility(true);
                }
            } catch (Exception e) {
                e.printStackTrace(); // Log or handle the error appropriately
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (this.adapter != null) {
                this.adapter.deleteSelectedItems(favoriteRef, currentUser);
                if (adapter.getItemCount() == 0) {
                    btnDelete.setVisibility(View.GONE);
                    updateSelectAllVisibility(false);
                }
            }
        });

        clMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUserPage();
            }
        });

        btnTradePlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTradePage();
            }
        });

        clPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPrivateMenu();
            }
        });

        clHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHomePage();
            }
        });

    }

    // Set up 'Select All' feature
    private void setupSelectAllFeature() {
        selectAllCheckbox.setOnClickListener(v -> {
            boolean isChecked = selectAllCheckbox.isChecked();
            if (isChecked) {
                adapter.selectAll();
            } else {
                adapter.deselectAll();
            }
        });
    }

    // Update visibility of 'Select All' checkbox and bottom bar
    private void updateSelectAllVisibility(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        bottomBar.setVisibility(visibility);
        selectAllCheckbox.setVisibility(visibility);
        btnDelete.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    // Load favorite products list from Firebase database
    private void loadFavoriteProducts(FavoriteProductsListener listener) {
        if (currentUser == null) {
            pbLoading.setVisibility(View.GONE);
            return;
        }
        List<Product> favoriteProducts = new ArrayList<>();
        favoriteRef.orderByChild("userID").equalTo(currentUser.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int totalFavorites = (int) snapshot.getChildrenCount();
                if (totalFavorites == 0) {
                    // If there are no favorite products, return an empty list directly
                    listener.onProductsLoaded(favoriteProducts);
                    return;
                }

                AtomicInteger loadedCount = new AtomicInteger(0);
                for (DataSnapshot favoriteSnapshot : snapshot.getChildren()) {
                    Favorite favorite = favoriteSnapshot.getValue(Favorite.class);
                    if (favorite != null && favorite.isFavoriteStatus()) {
                        productRef.child(favorite.getProductID()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot productSnapshot) {
                                Product product = productSnapshot.getValue(Product.class);
                                if (product != null) {
                                    favoriteProducts.add(product);
                                }

                                if (loadedCount.incrementAndGet() == totalFavorites) {
                                    listener.onProductsLoaded(favoriteProducts);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Handle potential errors
                                if (loadedCount.incrementAndGet() == totalFavorites) {
                                    listener.onProductsLoaded(favoriteProducts);
                                }
                            }
                        });
                    } else {
                        if (loadedCount.incrementAndGet() == totalFavorites) {
                            listener.onProductsLoaded(favoriteProducts);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle potential errors
                listener.onProductsLoaded(favoriteProducts);
            }
        });
    }

    private interface FavoriteProductsListener {
        void onProductsLoaded(List<Product> favoriteProducts);
    }





}