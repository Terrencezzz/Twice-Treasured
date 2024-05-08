package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.graphics.Color;

import com.example.myapplication.Adapters.SearchItemAdapter;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Parser;
import com.example.myapplication.basicClass.Product;
import com.example.myapplication.basicClass.Tokenizer;
import com.example.myapplication.common.AVLTree;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchResultPage extends AppCompatActivity {
    private ImageView btnBack; // Back button
    private TextView btnPrice, btnCondition, btnDateListed;
    private RecyclerView recyclerViewProducts;
    private SearchItemAdapter searchItemAdapter;
    private EditText searchField; // User input field to search
    private ImageView btnSearch; // Search button image

    private boolean isPriceDropdown = false;
    private boolean isConditionDropdown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);
        initViews();
        setupToggleIcons();
    }

    // Initialize Views
    private void initViews() {
        // Find and assign views
        btnBack = findViewById(R.id.btnBack);
        btnPrice = findViewById(R.id.btnPrice);
        btnCondition = findViewById(R.id.btnCondition);
        btnDateListed = findViewById(R.id.btnDateListed);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        searchField = findViewById(R.id.searchField);
        btnSearch = findViewById(R.id.btnSearch);

        // Get the product category clicked by the user
        String categoryName = getIntent().getStringExtra("CategoryName");

        // Get the user input string passed from HomePage
        String HomeSearchString = getIntent().getStringExtra("HomeSearchString");

        // If the user clicks on the category picture, search for the corresponding category
        if (categoryName != null && !categoryName.isEmpty()) {
            // call the search method
            resultProductOfSearch(categoryName);
        }
        // If the user enters search content on the homepage, call the search method
        else if (HomeSearchString != null && !HomeSearchString.isEmpty()) {
            searchField.setText(HomeSearchString);
            resultProductOfSearch(HomeSearchString);
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInput = searchField.getText().toString(); // Get user input and convert to string
                resultProductOfSearch(userInput); // Call the search method, passing in user input
            }
        });

        // Back button click listener
        btnBack.setOnClickListener(view -> finish());
    }

    private void setupToggleIcons() {
        // Toggle button for price
        btnPrice.setOnClickListener(v -> {
            isPriceDropdown = !isPriceDropdown; // Toggles the state of price dropdown
            // Set the icon based on dropdown state
            btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    isPriceDropdown ? R.drawable.baseline_arrow_drop_up_24 : R.drawable.baseline_arrow_drop_down_24, 0);
            // Implement functionality to show or hide the dropdown here
        });

        // Toggle button for condition
        btnCondition.setOnClickListener(v -> {
            isConditionDropdown = !isConditionDropdown; // Toggles the state of condition dropdown
            // Set the icon based on dropdown state
            btnCondition.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    isConditionDropdown ? R.drawable.baseline_arrow_drop_up_24 : R.drawable.baseline_arrow_drop_down_24, 0);
            // Implement functionality to show or hide the dropdown here
        });
    }


    // Search Firebase for products matching user input string
    private void resultProductOfSearch(String searchString) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AVLTree<Product> avlTree = new AVLTree<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    avlTree.insert(product);
                }
                Parser parser = new Parser(searchString);
                avlTree = parser.parseEXP(avlTree);

                recyclerViewProducts.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                searchItemAdapter = new SearchItemAdapter(getApplicationContext(), avlTree.convertToArrayList());
                recyclerViewProducts.setAdapter(searchItemAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}