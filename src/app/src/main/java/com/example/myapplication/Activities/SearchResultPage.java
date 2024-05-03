package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;

import com.example.myapplication.R;

public class SearchResultPage extends AppCompatActivity {
    private ImageView btnBack; // Back button
    private TextView btnSortBy; // Sort by button
    private PopupWindow sortOptionsPopup; // Popup window for sort options

    private View sortOptionView; // View for each sort option
    private TextView tvOption; // Text view for sort option
    private ImageView ivCheckmark; // Checkmark image view for selected option indicator
    private boolean isSortByExpanded = false; // Flag to track if sort options are expanded
    private String selectedOption = "Suggested"; // Initially selected sorting option

    private TextView btnPrice;
    private EditText etMinimum, etMaximum;
    private TextView btnCondition;
    private TextView btnDateListed;
    private Button btnReset;
    private Button btnSeeItems;

    private LinearLayout priceOptionsContainer; // Container for dynamically loaded price options
    private PopupWindow priceOptionsPopup;

    private String savedMinimum = "";
    private String savedMaximum = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);
//        initButtons();  // Initially commented out
        initViews(); // Initialize views and setup sort options popup
//        setupSortOptionsPopup(); // Setup sort options popup - Initially commented out

    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnSortBy = findViewById(R.id.btnSortBy);
        btnPrice = findViewById(R.id.btnPrice);
        btnCondition = findViewById(R.id.btnCondition);
        btnDateListed = findViewById(R.id.btnDateListed);

        btnBack.setOnClickListener(view -> finish());

        // Initialize PopupWindow
        setupSortOptionsPopup(); // Setup sort options popup
        inflatePriceOptions();  // Make sure to call this to prepare the price options

        // Set button toggle behavior
        setupButtonWithToggle(btnSortBy, R.drawable.search_result_page_button_background,
                R.drawable.search_result_page_selected_button_background, sortOptionsPopup);

        // Set Price button toggle behavior
        setupButtonWithToggle(btnPrice, R.drawable.search_result_page_button_background,
                R.drawable.search_result_page_selected_button_background, priceOptionsPopup);

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

    // Setup sort options popup window
    private void setupSortOptionsPopup() {
        // Create a linear layout and set its orientation to vertical with white background
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.WHITE);
        // Define an array containing sorting options
        final String[] options = {"Suggested", "Lowest Price", "Highest Price", "Nearest", "Newest"};
        // For each sorting option:
        // Inflate the sorting option view from layout file and find text view and checkmark image view within it.
        for (String option : options) {
            sortOptionView = getLayoutInflater().inflate(R.layout.research_result_sort_item, layout, false);
            tvOption = sortOptionView.findViewById(R.id.tvSortOption); // Initialize text view for sort option
            ivCheckmark = sortOptionView.findViewById(R.id.ivCheckmark); // Initialize checkmark image view
            // Set text for sorting option
            tvOption.setText(option);

            // Update display based on whether the option is selected
            updateOptionDisplay(tvOption, ivCheckmark, option.equals(selectedOption));

            // Set click listener to update currently selected option, update button text to selected option,
            // update sorting options display, and close the popup when an option is selected.
            sortOptionView.setOnClickListener(v -> {
                selectedOption = option;
                btnSortBy.setText(option);
                updateSortOptions(layout);
                sortOptionsPopup.dismiss();
            });

            layout.addView(sortOptionView); // Add sorting option view to layout
        }
        // Add sorting option views to layout
        sortOptionsPopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        sortOptionsPopup.setOutsideTouchable(true); // Set popup to be outside touchable
        sortOptionsPopup.setFocusable(true); // Set popup to be focusable
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
}