package com.example.myapplication.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapters.SearchItemAdapter;
import com.example.myapplication.R;


public class SearchResultPage extends AppCompatActivity {
    private ImageView btnBack; // Back button
    private TextView btnPrice, btnCondition, btnDateListed;
    private RecyclerView recyclerViewProducts;
    private SearchItemAdapter searchItemAdapter;
    private EditText searchField; // User input field to search
    private ImageView btnSearch; // Search button image

    private boolean isPriceDropdown = false;
    private boolean isConditionDropdown = false;
    private SearchService searchService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);
        searchService = new SearchService();
        initViews();
        setupToggleIcons();
    }

    // Initialize Views
    private void initViews() {
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
            ResultProductOfSearch(categoryName);
        }
        // If the user enters search content on the homepage, call the search method
        else if (HomeSearchString != null && !HomeSearchString.isEmpty()) {
            searchField.setText(HomeSearchString);
            ResultProductOfSearch(HomeSearchString);
        }
        // When the user clicks on the btnSearch, the search will be based on the string entered by the user.
        btnSearch.setOnClickListener(view -> {
            String userInput = searchField.getText().toString();
            ResultProductOfSearch(userInput);
        });

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

    // Use SearchService to fetch products and update UI
    private void ResultProductOfSearch(String searchString) {
        searchService.FindProduct(searchString, products -> {
            recyclerViewProducts.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            searchItemAdapter = new SearchItemAdapter(getApplicationContext(), products);
            recyclerViewProducts.setAdapter(searchItemAdapter);
        });
    }
}
