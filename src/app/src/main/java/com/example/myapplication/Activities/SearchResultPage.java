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
    String SearchString = "";

    private enum SortState {
        NONE, ASCENDING, DESCENDING
    }
    private SortState priceSortState = SortState.NONE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);
        searchService = new SearchService();
        initSearchString();
        initViews();
        setupToggleIcons();
    }

    // Initialize search string based on input from other activities or default
    private void initSearchString() {
        // Get the product category clicked by the user
        String categoryName = getIntent().getStringExtra("CategoryName");
        // Get the user input string passed from HomePage
        String HomeSearchString = getIntent().getStringExtra("HomeSearchString");

        if (categoryName != null && !categoryName.isEmpty()) {
            SearchString = categoryName;
            ResultProductOfSearch(SearchString);
        } else if (HomeSearchString != null && !HomeSearchString.isEmpty()) {
            SearchString = HomeSearchString;
            ResultProductOfSearch(SearchString);
        }
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

        // Ensure no icon is displayed initially for the price button
        btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        // Setup search button to update SearchString and perform search
        btnSearch.setOnClickListener(view -> {
            SearchString = searchField.getText().toString();
            ResultProductOfSearch(SearchString);
        });

        btnBack.setOnClickListener(view -> finish());
    }

    private void setupToggleIcons() {
        // Toggle button for price
        btnPrice.setOnClickListener(v -> {
            // Cycle through the sort states
            switch (priceSortState) {
                case NONE:
                    priceSortState = SortState.ASCENDING;
                    btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_arrow_drop_up_24, 0);
                    ResultProductsAscendingOrder(SearchString);
                    break;
                case ASCENDING:
                    priceSortState = SortState.DESCENDING;
                    btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_arrow_drop_down_24, 0);
                    ResultProductsDescendingOrder(SearchString);
                    break;
                case DESCENDING:
                    priceSortState = SortState.NONE;
                    btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    ResultProductOfSearch(SearchString);
                    break;
            }
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

    // Get and display products in ascending order
    private void ResultProductsAscendingOrder(String searchString) {
        searchService.FindProductsAscendingOrder(searchString, products -> {
            recyclerViewProducts.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            searchItemAdapter = new SearchItemAdapter(getApplicationContext(), products);
            recyclerViewProducts.setAdapter(searchItemAdapter);
        });
    }

    // Get and display products in descending order
    private void ResultProductsDescendingOrder(String searchString) {
        searchService.FindProductsDescendingOrder(searchString, products -> {
            recyclerViewProducts.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            searchItemAdapter = new SearchItemAdapter(getApplicationContext(), products);
            recyclerViewProducts.setAdapter(searchItemAdapter);
        });
    }

}
