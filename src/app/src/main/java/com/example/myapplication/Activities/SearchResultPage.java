package com.example.myapplication.Activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapters.SearchItemAdapter;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;

import java.util.ArrayList;


public class SearchResultPage extends AppCompatActivity {
    private ImageView btnBack; // Back button
    private TextView btnPrice, btnCondition, btnUsed;
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

    private enum FilterState {
        ALL, NEW, USED
    }

    private SortState priceSortState = SortState.NONE;
    private FilterState currentFilter = FilterState.ALL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);
        searchService = new SearchService();
        initViews();
        initSearchString();
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
        } else if (HomeSearchString != null && !HomeSearchString.isEmpty()) {
            SearchString = HomeSearchString;
        } else {
            SearchString = "";  // Set default to empty string
        }

        // Update the search field text and perform the search
        searchField.setText(SearchString);
        ResultProductOfSearch(SearchString);
    }
    // Initialize Views
    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnPrice = findViewById(R.id.btnPrice);
        btnCondition = findViewById(R.id.btnCondition);
        btnUsed = findViewById(R.id.btnUsed);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        searchField = findViewById(R.id.searchField);
        btnSearch = findViewById(R.id.btnSearch);

        // Ensure no icon is displayed initially for the price button
        btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_blank_24, 0);;

        // Setup search button to update SearchString and perform search
        btnSearch.setOnClickListener(view -> {
            SearchString = searchField.getText().toString();
            applyFilterAndSort();  // Search using current filter and sort state
        });

        btnBack.setOnClickListener(view -> finish());
    }


    /**
     * This method applies the current filter and sort state to fetch and display products.
     * It checks the currentFilter state to determine which subset of products to fetch (ALL, NEW, or USED).
     * It then applies the priceSortState to determine if the products should be sorted in ascending, descending, or no specific order.
     * This ensures that any interaction with the product list (whether filtering or sorting) will consistently reflect the user's choices.
     *
     * - If the filter is set to NEW, the method fetches new products.
     * - If the filter is set to USED, the method fetches used products.
     * - If the filter is set to ALL or any other state, it fetches all products.
     *
     * Depending on the priceSortState (ASCENDING, DESCENDING, NONE), it will either sort these products,
     * or simply fetch them as per the inherent order in the database.
     *
     * This approach maintains the state of user selections across different actions,
     * ensuring that the interface responds predictably to user inputs.
     */
    private void applyFilterAndSort() {
        // Switch based on the current filter state (ALL, NEW, USED)
        switch (currentFilter) {
            case NEW:
                // Apply ascending or descending price sort for new products or default to unsorted
                if (priceSortState == SortState.ASCENDING) {
                    searchService.FindNewProductsAscendingOrder(SearchString, this::updateRecyclerView);
                } else if (priceSortState == SortState.DESCENDING) {
                    searchService.FindNewProductsDescendingOrder(SearchString, this::updateRecyclerView);
                } else {
                    // If no sorting is specified, fetch new products without sorting
                    searchService.FindNewProduct(SearchString, this::updateRecyclerView);
                }
                break;
            case USED:
                // Apply ascending or descending price sort for used products or default to unsorted
                if (priceSortState == SortState.ASCENDING) {
                    searchService.FindUsedProductsAscendingOrder(SearchString, this::updateRecyclerView);
                } else if (priceSortState == SortState.DESCENDING) {
                    searchService.FindUsedProductsDescendingOrder(SearchString, this::updateRecyclerView);
                } else {
                    // If no sorting is specified, fetch used products without sorting
                    searchService.FindUsedProduct(SearchString, this::updateRecyclerView);
                }
                break;
            case ALL:
            default:
                // Apply ascending or descending price sort for all products or default to unsorted
                if (priceSortState == SortState.ASCENDING) {
                    searchService.FindProductsAscendingOrder(SearchString, this::updateRecyclerView);
                } else if (priceSortState == SortState.DESCENDING) {
                    searchService.FindProductsDescendingOrder(SearchString, this::updateRecyclerView);
                } else {
                    // If no sorting is specified, fetch all products without sorting
                    searchService.FindProduct(SearchString, this::updateRecyclerView);
                }
                break;
        }
    }

    private void setupToggleIcons() {
        // Toggle button for price
        btnPrice.setOnClickListener(v -> {
            // Cycle through the sort states
            switch (priceSortState) {
                case NONE:
                    priceSortState = SortState.ASCENDING;
                    btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_arrow_drop_up_24, 0);
                    applyFilterAndSort();
                    break;
                case ASCENDING:
                    priceSortState = SortState.DESCENDING;
                    btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_arrow_drop_down_24, 0);
                    applyFilterAndSort();
                    break;
                case DESCENDING:
                    priceSortState = SortState.NONE;
                    btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_blank_24, 0);
                    applyFilterAndSort();
                    break;
            }
        });

        // Filter out Products whose condition is New
        btnCondition.setOnClickListener(v -> {
            isConditionDropdown = !isConditionDropdown;
            currentFilter = isConditionDropdown ? FilterState.NEW : FilterState.ALL;
            btnCondition.setTextColor(getResources().getColor(isConditionDropdown ? R.color.orange_button : R.color.black));
            applyFilterAndSort();
        });

        // Filter out Products whose condition is Used
        btnUsed.setOnClickListener(v -> {
            isConditionDropdown = !isConditionDropdown;
            currentFilter = isConditionDropdown ? FilterState.USED : FilterState.ALL;
            btnUsed.setTextColor(getResources().getColor(isConditionDropdown ? R.color.orange_button : R.color.black));
            applyFilterAndSort();
        });
    }

    // Update filtered product UI
    private void updateRecyclerView(ArrayList<Product> products) {
        recyclerViewProducts.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        searchItemAdapter = new SearchItemAdapter(getApplicationContext(), products);
        recyclerViewProducts.setAdapter(searchItemAdapter);
    }

    // Use SearchService to fetch products and update UI
    private void ResultProductOfSearch(String searchString) {
        searchService.FindProduct(searchString, this::updateRecyclerView);
    }

    // Display new products in the RecyclerView
    private void ResultNewProducts(String searchString) {
        searchService.FindNewProduct(searchString, this::updateRecyclerView);
    }

    // Display used products in the RecyclerView
    private void ResultUsedProducts(String searchString) {
        searchService.FindUsedProduct(searchString, this::updateRecyclerView);
    }


    // Sort and display products in ascending order based on the current filter state
    private void ResultProductsAscendingOrder(String searchString) {
        switch (currentFilter) {
            case ALL:
                // If no specific filter is applied, sort all products in ascending order
                searchService.FindProductsAscendingOrder(searchString, this::updateRecyclerView);
                break;
            case NEW:
                // If the filter for new products is active, sort only new products in ascending order
                searchService.FindNewProductsAscendingOrder(searchString, this::updateRecyclerView);
                break;
            case USED:
                // If the filter for used products is active, sort only used products in ascending order
                searchService.FindUsedProductsAscendingOrder(searchString, this::updateRecyclerView);
                break;
        }
    }

    // Sort and display products in descending order based on the current filter state
    private void ResultProductsDescendingOrder(String searchString) {
        switch (currentFilter) {
            case ALL:
                // If no specific filter is applied, sort all products in descending order
                searchService.FindProductsDescendingOrder(searchString, this::updateRecyclerView);
                break;
            case NEW:
                // If the filter for new products is active, sort only new products in descending order
                searchService.FindNewProductsDescendingOrder(searchString, this::updateRecyclerView);
                break;
            case USED:
                // If the filter for used products is active, sort only used products in descending order
                searchService.FindUsedProductsDescendingOrder(searchString, this::updateRecyclerView);
                break;
        }
    }

}
