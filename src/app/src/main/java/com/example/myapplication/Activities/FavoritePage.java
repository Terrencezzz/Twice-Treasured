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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This activity allows users to view their favorite products and manage them, including deleting selected items.
 * @author Xiaojie Zhou u7769944
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
    private boolean isPriceAscending = true; // Flag for sorting price in ascending order
    private String currentCategory = "All Items"; // Currently selected category, initialized to "All Items"
    private List<Product> allFavoriteProducts = new ArrayList<>(); // Global variable to store all favorite products

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        globalVars = GlobalVariables.getInstance();
        currentUser = globalVars.getLoginUser();
        if (currentUser == null) {
            finish();
            return;
        }

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
        setupAllItemsButton(); // Add logic for "All Items" button
        initCategoryButtons();
        setupPriceSortingButton();
    }


    // Initialize favorite products list
    private void initFavoriteProducts() {
        pbLoading.setVisibility(View.VISIBLE);
        adapter = new FavoriteAdapter(new ArrayList<>(),favoriteRef, currentUser, this::handleProductClick);
        rvFavorite.setAdapter(adapter);
        loadFavoriteProducts(favoriteProducts -> {
            allFavoriteProducts = new ArrayList<>(favoriteProducts); // Store all products
            adapter.setFavoriteItemList(favoriteProducts);
            pbLoading.setVisibility(View.GONE);
            updateSelectAllVisibility(!favoriteProducts.isEmpty());
            // Call initCategoryButtons() to ensure category buttons are correctly initialized
            initCategoryButtons();
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
            listener.onProductsLoaded(new ArrayList<>());
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

    private static final String TAG = "FavoritePage"; // Log tag defined at the beginning of the class

    private void initCategoryButtons() {

        LinearLayout categoryLayout = findViewById(R.id.categoryButtons);

        // Clear existing buttons, except for the "All Items" button
        int childCount = categoryLayout.getChildCount();
        for (int i = childCount - 1; i > 0; i--) {
            categoryLayout.removeViewAt(i);
        }

        Set<String> categories = new HashSet<>(); // Used to store unique category names

        for (Product product : allFavoriteProducts) {
            String category = product.getCategory();
            if (category != null) {
                categories.add(category);
            }
        }

        for (String category : categories) {
            Button categoryButton = new Button(this);
            categoryButton.setText(category);
            categoryButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            categoryButton.setOnClickListener(v -> filterByCategory(category));
            categoryLayout.addView(categoryButton);
        }
    }

    private void filterByCategory(String category) {

        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : allFavoriteProducts) {
            if (product.getCategory().equals(category)) {
                filteredProducts.add(product);
            }
        }
        adapter.setFavoriteItemList(filteredProducts);
        adapter.notifyDataSetChanged(); // Ensure RecyclerView is refreshed
    }

    private void setupAllItemsButton() {
        Button btnAllItems = findViewById(R.id.btnAllItems);
        btnAllItems.setOnClickListener(v -> {
            adapter.setFavoriteItemList(new ArrayList<>(allFavoriteProducts));
            adapter.notifyDataSetChanged();
            currentCategory = "All Items"; // Reset current category state
        });
    }

    private void setupPriceSortingButton() {
        Button btnPrice = findViewById(R.id.btnFavoritePagePrice);
        btnPrice.setOnClickListener(v -> {
            if (isPriceAscending) {
                Collections.sort(adapter.getFavoriteItemList(), (p1, p2) -> Integer.compare(Integer.parseInt(p1.getPrice()), Integer.parseInt(p2.getPrice())));
                btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_arrow_drop_up_24, 0);
            } else {
                Collections.sort(adapter.getFavoriteItemList(), (p1, p2) -> Integer.compare(Integer.parseInt(p2.getPrice()), Integer.parseInt(p1.getPrice())));
                btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_arrow_drop_down_24, 0);
            }
            adapter.notifyDataSetChanged();
            isPriceAscending = !isPriceAscending; // Toggle sorting state
        });
    }



}