package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.graphics.Color;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);

        btnBack = findViewById(R.id.btnBack); // Initialize back button
        btnBack.setOnClickListener(view -> finish()); // Set click listener to finish activity when back button is clicked

        btnSortBy = findViewById(R.id.btnSortBy); // Initialize sort by button
        setupSortOptionsPopup(); // Setup sort options popup
        setupButtonWithToggle(btnSortBy, R.drawable.search_result_page_button_background, R.drawable.search_result_page_selected_button_background, sortOptionsPopup); // Setup sort by button with toggle behavior
    }

    // Setup sort options popup window
    private void setupSortOptionsPopup() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.WHITE);
        final String[] options = {"Suggested", "Lowest Price", "Highest Price", "Nearest", "Newest"};

        for (String option : options) {
            sortOptionView = getLayoutInflater().inflate(R.layout.research_result_sort_item, layout, false);
            tvOption = sortOptionView.findViewById(R.id.tvSortOption); // Initialize text view for sort option
            ivCheckmark = sortOptionView.findViewById(R.id.ivCheckmark); // Initialize checkmark image view

            tvOption.setText(option); // Set text for sort option
            updateOptionDisplay(tvOption, ivCheckmark, option.equals(selectedOption)); // Update display based on whether the option is selected

            sortOptionView.setOnClickListener(v -> {
                selectedOption = option; // Update currently selected option
                btnSortBy.setText(option); // Update button text to the selected option
                updateSortOptions(layout); // Update sort options display
                sortOptionsPopup.dismiss(); // Close the popup
            });

            layout.addView(sortOptionView); // Add sort option view to layout
        }

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
            }
        });

        // Listener for popup dismissal
        popupWindow.setOnDismissListener(() -> {
            button.setBackgroundResource(normalBg); // Reset button background when the popup is dismissed
        });
    }
}