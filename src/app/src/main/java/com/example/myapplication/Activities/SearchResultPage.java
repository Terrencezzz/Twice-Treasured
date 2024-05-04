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

import java.util.ArrayList;
import java.util.List;

public class SearchResultPage extends AppCompatActivity {
    private ImageView btnBack; // Back button
    private TextView btnSortBy, btnPrice, btnCondition, btnDateListed;
    private PopupWindow sortOptionsPopup, priceOptionsPopup, conditionPopup, dateListPopup;
    private RecyclerView recyclerViewProducts;
    private SearchItemAdapter searchItemAdapter;
    private View sortOptionView; // View for each sort option
    private TextView tvOption; // Text view for sort option
    private ImageView ivCheckmark; // Checkmark image view for selected option indicator
    private String selectedOption = "Suggested"; // Initially selected sorting option
    private EditText etMinimum, etMaximum;
    private Button btnReset;
    private Button btnSeeItems;
    private Button btnConditonReset;
    private Button btnConditionSeeItems;
    private Button btnDateListReset;
    private Button btnDateListSeeItems;
    private LinearLayout priceOptionsContainer; // Container for dynamically loaded price options
    private String savedMinimum = "";
    private String savedMaximum = "";
    private boolean[] conditionStates;
    private String[] conditions = {"Any", "New", "Used-like new", "Used-good", "Used-fair"};
    private String[] dateListOptions = {"Any", "Last 24 hours", "Last 7 days", "Last 30 days"};
    private boolean[] dateListStates;

    private EditText searchField; // User input field to search

    private ImageView btnSearch; // Search button image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);
        initViews();
    }

    // Initialize Views
    private void initViews() {
        // Find and assign views
        btnBack = findViewById(R.id.btnBack);
        btnSortBy = findViewById(R.id.btnSortBy);
        btnPrice = findViewById(R.id.btnPrice);
        btnCondition = findViewById(R.id.btnCondition);
        btnDateListed = findViewById(R.id.btnDateListed);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        searchField = findViewById(R.id.searchField);
        btnSearch = findViewById(R.id.btnSearch);

        // Get the user input string passed from HomePage
        String HomeSearchString = getIntent().getStringExtra("HomeSearchString");

        // If the user enters search content on the homepage, call the search method
        if (HomeSearchString != null && !HomeSearchString.isEmpty()) {
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

        // Initialize condition states
        conditionStates = new boolean[5];
        conditionStates[0] = true; // 'Any' selected by default
        for (int i = 1; i < conditionStates.length; i++) {
            conditionStates[i] = false; // All other options not selected
        }

        // Initialize date list states
        dateListStates = new boolean[4];
        dateListStates[0] = true; // 'Any' selected by default
        for (int i = 1; i < dateListStates.length; i++) {
            dateListStates[i] = false; // All other options not selected
        }

        // Initialize PopupWindows
        setupSortOptionsPopup(); // Setup sort options popup
        inflatePriceOptions();  // Prepare price options
        setupConditionOptionsPopup();
        setupDateListOptionsPopup();

        // Set toggle behavior for buttons
        setupButtonWithToggle(btnSortBy, R.drawable.search_result_page_button_background,
                R.drawable.search_result_page_selected_button_background, sortOptionsPopup);

        setupButtonWithToggle(btnPrice, R.drawable.search_result_page_button_background,
                R.drawable.search_result_page_selected_button_background, priceOptionsPopup);

        setupButtonWithToggle(btnCondition, R.drawable.search_result_page_button_background,
                R.drawable.search_result_page_selected_button_background, conditionPopup);

        setupButtonWithToggle(btnDateListed, R.drawable.search_result_page_button_background,
                R.drawable.search_result_page_selected_button_background, dateListPopup);

        // Load favorite products and set up RecyclerView
        // resultProductOfSearch();
    }

    // Setup sort options popup window
    private void setupSortOptionsPopup() {
        // Create a vertical linear layout with white background
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.WHITE);

        // Sorting options
        final String[] options = {"Suggested", "Lowest Price", "Highest Price", "Nearest", "Newest"};

        // For each option:
        for (String option : options) {
            // Inflate the sorting option view
            sortOptionView = getLayoutInflater().inflate(R.layout.search_result_sort_item, layout, false);
            tvOption = sortOptionView.findViewById(R.id.tvSortOption); // Text view for sort option
            ivCheckmark = sortOptionView.findViewById(R.id.ivCheckmark); // Checkmark image view

            // Set text for sorting option
            tvOption.setText(option);

            // Update display based on selection
            updateOptionDisplay(tvOption, ivCheckmark, option.equals(selectedOption));

            // Click listener to update selection, button text, options display, and close popup
            sortOptionView.setOnClickListener(v -> {
                selectedOption = option;
                btnSortBy.setText(option);
                updateSortOptions(layout);
                sortOptionsPopup.dismiss();
            });

            layout.addView(sortOptionView); // Add option view to layout
        }

        // Create and configure popup
        sortOptionsPopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        sortOptionsPopup.setOutsideTouchable(true); // Outside touchable
        sortOptionsPopup.setFocusable(true); // Focusable
    }


    // Update the display of sort options
    private void updateSortOptions(LinearLayout layout) {
        int childCount = layout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = layout.getChildAt(i);
            tvOption = child.findViewById(R.id.tvSortOption); // Find text view for sort option
            ivCheckmark = child.findViewById(R.id.ivCheckmark); // Find checkmark image view
            updateOptionDisplay(tvOption, ivCheckmark, tvOption.getText().toString().equals(selectedOption)); // Update display based on whether the option is selected
        }
    }

    // Update the display of each option (text color and checkmark visibility)
    private void updateOptionDisplay(TextView tvOption, ImageView ivCheckmark, boolean isSelected) {
        tvOption.setTextColor(isSelected ? Color.BLACK : Color.GRAY); // Set text color based on whether the option is selected
        ivCheckmark.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE); // Set checkmark visibility based on whether the option is selected
    }

    // Setup button with toggle behavior
    private void setupButtonWithToggle(final TextView button, final int normalBg, final int selectedBg, final PopupWindow popupWindow) {
        // Set initial background for the button
        button.setBackgroundResource(normalBg);

        // Click listener for the button
        button.setOnClickListener(v -> {
            // Check if the popup is already showing
            if (popupWindow.isShowing()) {
                popupWindow.dismiss(); // Dismiss the popup
                button.setBackgroundResource(normalBg); // Set button background to normal
            } else {
                button.setBackgroundResource(selectedBg); // Set button background to selected
                popupWindow.showAsDropDown(button); // Show the popup below the button

                // Ensure the price options are correctly displayed when the popup is shown
                etMinimum.setText(savedMinimum);
                etMaximum.setText(savedMaximum);
            }
        });

        // Listener for popup dismissal
        popupWindow.setOnDismissListener(() -> {
            button.setBackgroundResource(normalBg); // Reset button background when the popup is dismissed
        });
    }


    private void inflatePriceOptions() {
        LayoutInflater inflater = LayoutInflater.from(this);
        priceOptionsContainer = (LinearLayout) inflater.inflate(R.layout.search_result_price_options, null, false);
        priceOptionsPopup = new PopupWindow(priceOptionsContainer, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        priceOptionsPopup.setOutsideTouchable(true);
        priceOptionsPopup.setFocusable(true);

        etMinimum = priceOptionsContainer.findViewById(R.id.etMinimum);
        etMaximum = priceOptionsContainer.findViewById(R.id.etMaximum);
        btnReset = priceOptionsContainer.findViewById(R.id.btnReset);
        btnSeeItems = priceOptionsContainer.findViewById(R.id.btnSeeItems);

        // Set click listener for "See Items" button
        btnSeeItems.setOnClickListener(v -> {
            // Get text from EditText fields
            String min = etMinimum.getText().toString();
            String max = etMaximum.getText().toString();

            // Try parsing strings into integers, set to null if empty
            Integer minValue = min.isEmpty() ? null : Integer.parseInt(min);
            Integer maxValue = max.isEmpty() ? null : Integer.parseInt(max);

            // If both are not null and minimum is greater than maximum, swap them
            if (minValue != null && maxValue != null && minValue > maxValue) {
                Integer temp = minValue;
                minValue = maxValue;
                maxValue = temp;

                // Update text fields
                etMinimum.setText(String.valueOf(minValue));
                etMaximum.setText(String.valueOf(maxValue));
            }

            // Update saved values
            savedMinimum = minValue != null ? String.valueOf(minValue) : "";
            savedMaximum = maxValue != null ? String.valueOf(maxValue) : "";

            // Build and update Price button text
            if (minValue != null && maxValue == null) {
                btnPrice.setText("$ " + minValue + " or more");
            } else if (minValue == null && maxValue != null) {
                btnPrice.setText("$ " + maxValue + " or less");
            } else if (minValue != null && maxValue != null) {
                btnPrice.setText("$ " + minValue + " to $ " + maxValue);
            } else {
                btnPrice.setText("Price");
            }

            // Close dropdown
            if (priceOptionsPopup.isShowing()) {
                priceOptionsPopup.dismiss();
            }
        });


        // Set click listener for "Reset" button
        btnReset.setOnClickListener(v -> {
            // Check and clear etMinimum and etMaximum
            if (!etMinimum.getText().toString().isEmpty()) {
                etMinimum.setText("");
            }
            if (!etMaximum.getText().toString().isEmpty()) {
                etMaximum.setText("");
            }
        });
    }


    // Setup condition options popup window
    private void setupConditionOptionsPopup() {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout conditionOptionsContainer = (LinearLayout) inflater.inflate(R.layout.search_result_condition_dropdown_layout, null, false);
        conditionPopup = new PopupWindow(conditionOptionsContainer, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        conditionPopup.setOutsideTouchable(true);
        conditionPopup.setFocusable(false); // Changed to false to not auto-dismiss

        LinearLayout llConditionOptions = conditionOptionsContainer.findViewById(R.id.llConditionOptions);

        for (int i = 0; i < conditions.length; i++) {
            View conditionView = inflater.inflate(R.layout.search_result_condition_item, llConditionOptions, false);
            TextView tvConditionOption = conditionView.findViewById(R.id.tvConditionOption);
            ImageView ivCheckbox = conditionView.findViewById(R.id.ivCheckbox);
            tvConditionOption.setText(conditions[i]);
            ivCheckbox.setImageResource(conditionStates[i] ? R.drawable.check_box_selected_24 : R.drawable.check_box_empty_24);
            final int index = i;
            conditionView.setOnClickListener(v -> {
                toggleCheckbox(ivCheckbox, index, conditions);
            });
            llConditionOptions.addView(conditionView);
        }

        // Button to apply selected conditions
        btnConditionSeeItems = conditionOptionsContainer.findViewById(R.id.btnConditionSeeItems);
        btnConditionSeeItems.setOnClickListener(v -> {
            if (conditionPopup != null && conditionPopup.isShowing()) {
                conditionPopup.dismiss();
                updateConditionButtonText();
            }
        });

        // Button to reset conditions
        btnConditonReset = conditionOptionsContainer.findViewById(R.id.btnConditonReset);
        btnConditonReset.setOnClickListener(view -> resetConditionOptions());

        // Condition button click listener
        btnCondition.setOnClickListener(v -> {
            if (conditionPopup.isShowing()) {
                conditionPopup.dismiss();
            } else {
                conditionPopup.showAsDropDown(btnCondition);
            }
        });
    }

    // Toggle checkbox selection
    private void toggleCheckbox(ImageView ivCheckbox, int index, String[] conditions) {
        boolean isSelected = conditionStates[index];

        if (index == 0) { // Clicked 'Any'
            if (!isSelected) {
                conditionStates[index] = true; // Select 'Any' and deselect other options
                ivCheckbox.setImageResource(R.drawable.check_box_selected_24);
                for (int i = 1; i < conditionStates.length; i++) {
                    conditionStates[i] = false;
                    updateCheckboxUI(i, false);
                }
            } // If 'Any' was already selected and no other option was selected, no change needed
        } else { // Clicked a non-'Any' option
            conditionStates[index] = !isSelected; // Toggle selection
            ivCheckbox.setImageResource(conditionStates[index] ? R.drawable.check_box_selected_24 : R.drawable.check_box_empty_24);

            if (conditionStates[index]) {
                conditionStates[0] = false; // Deselect 'Any' if a non-'Any' option is selected
                updateCheckboxUI(0, false);
            } else {
                // Check if any other non-'Any' option is still selected
                boolean anyNonAnySelected = false;
                for (int i = 1; i < conditionStates.length; i++) {
                    if (conditionStates[i]) {
                        anyNonAnySelected = true;
                        break;
                    }
                }
                if (!anyNonAnySelected) {
                    conditionStates[0] = true; // If the deselected option was the only one selected, select 'Any'
                    updateCheckboxUI(0, true);
                }
            }
        }
    }

    // Update UI of specified checkbox
    private void updateCheckboxUI(int index, boolean isSelected) {
        LinearLayout llConditionOptions = (LinearLayout) conditionPopup.getContentView().findViewById(R.id.llConditionOptions);
        ImageView checkboxView = (ImageView) llConditionOptions.getChildAt(index).findViewById(R.id.ivCheckbox);
        checkboxView.setImageResource(isSelected ? R.drawable.check_box_selected_24 : R.drawable.check_box_empty_24);
    }


    // Update condition button text based on selected conditions
    private void updateConditionButtonText() {
        StringBuilder selectedConditions = new StringBuilder();
        int count = 0;
        for (int i = 1; i < conditionStates.length; i++) {
            if (conditionStates[i]) {
                if (selectedConditions.length() > 0) {
                    selectedConditions.append(", ");
                }
                selectedConditions.append(conditions[i]);
                count++;
            }
        }

        if (count == 0) {
            btnCondition.setText("Condition"); // If 'Any' is selected or no conditions are selected
        } else if (count > 2) {
            // Ensure truncation of the string doesn't remove too much text
            int lastCommaIndex = selectedConditions.lastIndexOf(",");
            if (lastCommaIndex > 0) {
                int secondLastCommaIndex = selectedConditions.substring(0, lastCommaIndex).lastIndexOf(",");
                if (secondLastCommaIndex > 0) {
                    btnCondition.setText(selectedConditions.substring(0, secondLastCommaIndex) + "…");
                } else {
                    btnCondition.setText(selectedConditions.substring(0, lastCommaIndex) + "…");
                }
            } else {
                btnCondition.setText(selectedConditions.toString());  // If only one option is selected
            }
        } else {
            btnCondition.setText(selectedConditions.toString()); // If one or two conditions are selected
        }
    }

    // Reset condition options
    private void resetConditionOptions() {
        boolean anyNonAnySelected = false;
        // Check if any non-'Any' option is selected
        for (int i = 1; i < conditionStates.length; i++) {
            if (conditionStates[i]) {
                anyNonAnySelected = true;
                break;
            }
        }

        if (anyNonAnySelected) {
            // Deselect all non-'Any' options and select 'Any'
            for (int i = 1; i < conditionStates.length; i++) {
                conditionStates[i] = false;
                updateCheckboxUI(i, false);
            }
            conditionStates[0] = true;
            updateCheckboxUI(0, true);
        }
        // If only 'Any' is selected, do nothing
    }

    // Setup date listed options popup window
    private void setupDateListOptionsPopup() {
        LayoutInflater inflater = LayoutInflater.from(this);

        LinearLayout DateListOptionsContainer = (LinearLayout) inflater.inflate(R.layout.search_result_datelist_dropdown_layout, null, false);
        dateListPopup = new PopupWindow(DateListOptionsContainer, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dateListPopup.setOutsideTouchable(true);
        dateListPopup.setFocusable(false); // Changed to false to not auto-dismiss

        LinearLayout llDateListOptions = DateListOptionsContainer.findViewById(R.id.llDateListOptions);

        for (int i = 0; i < dateListOptions.length; i++) {
            View dateListView = inflater.inflate(R.layout.search_result_datelist_each_option, llDateListOptions, false);
            TextView tvDateListOption = dateListView.findViewById(R.id.tvDateListOption);
            ImageView ivDateListCheckbox = dateListView.findViewById(R.id.ivDateListCheckbox);
            tvDateListOption.setText(dateListOptions[i]);

            // Set default selection state
            ivDateListCheckbox.setVisibility(dateListStates[i] ? View.VISIBLE : View.INVISIBLE);

            final int index = i;
            dateListView.setOnClickListener(v -> {
                // Update all checkboxes to be invisible
                for (int j = 0; j < dateListStates.length; j++) {
                    dateListStates[j] = false;
                    ((ImageView) llDateListOptions.getChildAt(j).findViewById(R.id.ivDateListCheckbox)).setVisibility(View.INVISIBLE);
                }
                // Set selected checkbox to be visible
                dateListStates[index] = true;
                ivDateListCheckbox.setVisibility(View.VISIBLE);
            });

            llDateListOptions.addView(dateListView);
        }

        // Button to apply selected date list option
        btnDateListSeeItems = DateListOptionsContainer.findViewById(R.id.btnDateListSeeItems);
        btnDateListSeeItems.setOnClickListener(v -> {
            if (dateListPopup != null && dateListPopup.isShowing()) {
                dateListPopup.dismiss();
                updateDateListButtonText();
            }
        });

        // Button to reset date list options
        btnDateListReset = DateListOptionsContainer.findViewById(R.id.btnDateListReset);
        btnDateListReset.setOnClickListener(view -> resetDateListOptions());

        // Date listed button click listener
        btnDateListed.setOnClickListener(v -> {
            if (dateListPopup.isShowing()) {
                dateListPopup.dismiss();
            } else {
                dateListPopup.showAsDropDown(btnDateListed);
            }
        });
    }


    // Update date listed button text based on selected date list conditions
    private void updateDateListButtonText() {
        StringBuilder selectedDateListConditions = new StringBuilder();
        int count = 0;
        for (int i = 1; i < dateListStates.length; i++) {
            if (dateListStates[i]) {
                if (selectedDateListConditions.length() > 0) {
                    selectedDateListConditions.append(", ");
                }
                selectedDateListConditions.append(dateListOptions[i]);
                count++;
            }
        }

        if (count == 0) {
            btnDateListed.setText("Date Listed");
        } else {
            btnDateListed.setText(selectedDateListConditions.toString());
        }
    }

    // Reset date list options
    private void resetDateListOptions() {
        // Reset all options to unselected and ensure 'Any' is default selected
        dateListStates[0] = true;
        for (int i = 1; i < dateListStates.length; i++) {
            dateListStates[i] = false;
        }
        updateDateListCheckboxes();
    }

    // Update visibility of date list checkboxes
    private void updateDateListCheckboxes() {
        LinearLayout llDateListOptions = dateListPopup.getContentView().findViewById(R.id.llDateListOptions);
        for (int i = 0; i < dateListOptions.length; i++) {
            ImageView checkboxView = llDateListOptions.getChildAt(i).findViewById(R.id.ivDateListCheckbox);
            checkboxView.setVisibility(dateListStates[i] ? View.VISIBLE : View.INVISIBLE);
        }
    }

    // Search Firebase for products matching user input string
    private void resultProductOfSearch(String searchString) {
        // Dummy data for demonstration
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AVLTree<Product> avlTree = new AVLTree<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    avlTree.insert(product);
                }
                Tokenizer tokenizer = new Tokenizer(searchString);
                Parser parser = new Parser(tokenizer);
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