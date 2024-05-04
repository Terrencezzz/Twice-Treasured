package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.myapplication.basicClass.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchResultPage extends AppCompatActivity {
    private ImageView btnBack; // Back button
    private TextView btnSortBy, btnPrice, btnCondition, btnDateListed;
    private PopupWindow sortOptionsPopup, priceOptionsPopup, conditionPopup;
    private RecyclerView recyclerViewProducts;
    private SearchItemAdapter searchItemAdapter;
    private View sortOptionView; // View for each sort option
    private TextView tvOption; // Text view for sort option
    private ImageView ivCheckmark; // Checkmark image view for selected option indicator
    private boolean isSortByExpanded = false; // Flag to track if sort options are expanded
    private String selectedOption = "Suggested"; // Initially selected sorting option
    private EditText etMinimum, etMaximum;
    private Button btnReset;
    private Button btnSeeItems;
    private Button btnConditonReset;
    private Button btnConditionSeeItems;

    private LinearLayout priceOptionsContainer; // Container for dynamically loaded price options
    private String savedMinimum = "";
    private String savedMaximum = "";
    private boolean[] conditionStates;
    private String[] conditions = {"Any", "New", "Used-like new", "Used-good", "Used-fair"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);
        initViews();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnSortBy = findViewById(R.id.btnSortBy);
        btnPrice = findViewById(R.id.btnPrice);
        btnCondition = findViewById(R.id.btnCondition);
        btnDateListed = findViewById(R.id.btnDateListed);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);

        btnBack.setOnClickListener(view -> finish());

        conditionStates = new boolean[5];
        conditionStates[0] = true; // 'Any' is selected by default
        for (int i = 1; i < conditionStates.length; i++) {
            conditionStates[i] = false; // Other options are not selected initially
        }

        // Initialize PopupWindow
        setupSortOptionsPopup(); // Setup sort options popup
        inflatePriceOptions();  // Make sure to call this to prepare the price options
        setupConditionOptionsPopup();

        // Set button toggle behavior
        setupButtonWithToggle(btnSortBy, R.drawable.search_result_page_button_background,
                R.drawable.search_result_page_selected_button_background, sortOptionsPopup);

        // Set Price button toggle behavior
        setupButtonWithToggle(btnPrice, R.drawable.search_result_page_button_background,
                R.drawable.search_result_page_selected_button_background, priceOptionsPopup);

        setupButtonWithToggle(btnCondition, R.drawable.search_result_page_button_background,
                R.drawable.search_result_page_selected_button_background, conditionPopup);

        List<Product> products = loadFavoriteProducts();
        setupRecyclerView(products);
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
            sortOptionView = getLayoutInflater().inflate(R.layout.search_result_sort_item, layout, false);
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



    //和price 按钮相关的交互
    // Interactions related to the "price" button
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


    // Interactions related to the "condition" button
    private void setupConditionOptionsPopup() {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout conditionOptionsContainer = (LinearLayout) inflater.inflate(R.layout.search_result_condition_dropdown_layout, null, false);
        conditionPopup = new PopupWindow(conditionOptionsContainer, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        conditionPopup.setOutsideTouchable(true);
        conditionPopup.setFocusable(true); // Changed to false to not auto-dismiss

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




        // Interaction related to the "condition" button
        btnConditionSeeItems = conditionOptionsContainer.findViewById(R.id.btnConditionSeeItems);
        btnConditionSeeItems.setOnClickListener(v -> {
            // Dismiss the condition popup and update the condition button text
            if (conditionPopup != null && conditionPopup.isShowing()) {
                conditionPopup.dismiss();
                updateConditionButtonText();
            }
        });

        btnConditonReset = conditionOptionsContainer.findViewById(R.id.btnConditonReset);
        btnConditonReset.setOnClickListener(view -> resetConditionOptions());

        btnCondition.setOnClickListener(v -> {
            // Show or dismiss the condition popup when the condition button is clicked
            if (conditionPopup.isShowing()) {
                conditionPopup.dismiss();
            } else {
                conditionPopup.showAsDropDown(btnCondition);
            }
        });

    }

    // Toggle the state of the checkbox
    private void toggleCheckbox(ImageView ivCheckbox, int index, String[] conditions) {
        // Get the current selected state of the checkbox
        boolean isSelected = conditionStates[index];

        if (index == 0) { // If 'Any' is clicked
            if (!isSelected) {
                // If 'Any' was previously unselected, select 'Any' and deselect all other options
                conditionStates[index] = true;
                ivCheckbox.setImageResource(R.drawable.check_box_selected_24);
                for (int i = 1; i < conditionStates.length; i++) {
                    conditionStates[i] = false;
                    updateCheckboxUI(i, false);
                }
            } // If 'Any' was already selected and no other options were selected, no change is needed
        } else { // If a non-'Any' option is clicked
            conditionStates[index] = !isSelected; // Toggle the selection state
            ivCheckbox.setImageResource(conditionStates[index] ? R.drawable.check_box_selected_24 : R.drawable.check_box_empty_24);

            if (conditionStates[index]) {
                // If any non-'Any' option is selected, ensure 'Any' is deselected
                conditionStates[0] = false;
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
                    // If the deselected option was the only selected non-'Any' option, automatically select 'Any'
                    conditionStates[0] = true;
                    updateCheckboxUI(0, true);
                }
            }
        }
    }

    /**
     * Update the UI display of the specified checkbox
     *
     * @param index      Index of the checkbox
     * @param isSelected Whether the checkbox is selected
     */
    private void updateCheckboxUI(int index, boolean isSelected) {
        LinearLayout llConditionOptions = (LinearLayout) conditionPopup.getContentView().findViewById(R.id.llConditionOptions);
        ImageView checkboxView = (ImageView) llConditionOptions.getChildAt(index).findViewById(R.id.ivCheckbox);
        checkboxView.setImageResource(isSelected ? R.drawable.check_box_selected_24 : R.drawable.check_box_empty_24);
    }

    // Update the text of the condition button based on the selected conditions
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
            // If 'Any' is selected or no conditions are selected
            btnCondition.setText("Condition"); // Use your default text for 'Any'
        } else if (count > 2) {
            // Ensure the string truncation doesn't remove too much text
            int lastCommaIndex = selectedConditions.lastIndexOf(",");
            if (lastCommaIndex > 0) {
                int secondLastCommaIndex = selectedConditions.substring(0, lastCommaIndex).lastIndexOf(",");
                if (secondLastCommaIndex > 0) {
                    btnCondition.setText(selectedConditions.substring(0, secondLastCommaIndex) + "…");
                } else {
                    btnCondition.setText(selectedConditions.substring(0, lastCommaIndex) + "…");
                }
            } else {
                btnCondition.setText(selectedConditions.toString());  // For the case where only one option is selected
            }
        } else {
            // If one or two conditions are selected, display them
            btnCondition.setText(selectedConditions.toString());
        }
    }


    // Reset all condition options
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
            // If any non-'Any' option is selected, deselect all non-'Any' options and select 'Any'
            for (int i = 1; i < conditionStates.length; i++) {
                conditionStates[i] = false;
                updateCheckboxUI(i, false);
            }
            conditionStates[0] = true;
            updateCheckboxUI(0, true);
        }
        // If only 'Any' is selected, no action is needed
    }

    // Interaction related to the displayed products
    private void setupRecyclerView(List<Product> products) {
        recyclerViewProducts.setLayoutManager(new GridLayoutManager(this, 2));
        searchItemAdapter = new SearchItemAdapter(this, products);
        recyclerViewProducts.setAdapter(searchItemAdapter);
    }

    private List<Product> loadFavoriteProducts() {
        // Dummy data for demonstration
        List<Product> products = new ArrayList<>();
        products.add(new Product("test", "1", "Category", "Nice product Nice productNice productNice productNice productNice productNice productNice productNice product", "100", "New", "Today", "Available", "https://img01.yzcdn.cn/upload_files/2019/12/27/Fk1Z1GGZ-42PJVGrSSHFMrgSO4R8.jpg%21middle.jpg", "tom", "001", "Canberra"));
        products.add(new Product("test", "2", "Category", "Another product Another productAnother productAnother productAnother productAnother productAnother productAnother productAnother product", "200", "Used", "Yesterday", "Available", "https://img01.yzcdn.cn/upload_files/2017/11/01/da3c0908669a5d3c43dec36642415254.jpg%21middle.jpg", "tom", "001", "Sydney"));
        products.add(new Product("test", "3", "Category", "Awesome product Awesome productAwesome productAwesome productAwesome productAwesome productAwesome productAwesome productAwesome product", "150", "New", "Today", "Available", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fg-search1.alicdn.com%2Fimg%2Fbao%2Fuploaded%2Fi4%2FO1CN01UL3Mg01PROLUi7MLa_%21%210-fleamarket.jpg_300x300.jpg&refer=http%3A%2F%2Fg-search1.alicdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1717053644&t=1c1527ee4c93bfde6992445a253901a9", "tom", "001", "Perth"));
        products.add(new Product("test", "4", "Category", "Fantastic product Fantastic productFantastic productFantastic productFantastic productFantastic productFantastic productFantastic productFantastic product", "180", "Used", "Yesterday", "Available", "https://img1.baidu.com/it/u=4185713029,1649043310&fm=253&fmt=auto&app=138&f=JPEG?w=450&h=600", "tom", "001", "ACT"));
        products.add(new Product("test", "5", "Category", "Superb product Superb productSuperb productSuperb productSuperb productSuperb productSuperb productSuperb productSuperb product", "250", "New", "Today", "Available", "https://img01.yzcdn.cn/upload_files/2020/03/29/FsKNyYUq6i2JbYVSMa-PAyaj_pya.jpg%21middle.jpg", "tom", "001", "Melbourne"));
        products.add(new Product("test", "6", "Category", "Excellent product Excellent productExcellent productExcellent productExcellent productExcellent productExcellent productExcellent productExcellent product", "300", "Used", "Yesterday", "Available", "https://pic.rmb.bdstatic.com/bjh/down/dc41f9f74d70e08b5877df601c381c42.jpeg@wm_2,t_55m+5a625Y+3L+enn+S4gOermeS6jOaJi+WKnuWFrOWutuWFtw==,fc_ffffff,ff_U2ltSGVp,sz_20,x_13,y_13", "tom", "001", "Sydney"));
        products.add(new Product("test", "7", "Category", "Amazing product Amazing productAmazing productAmazing productAmazing productAmazing productAmazing productAmazing productAmazing product", "170", "New", "Today", "Available", "https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2022%2F0628%2F06131eb4j00re6keu001dd200m800aag00g6007h.jpg&thumbnail=660x2147483647&quality=80&type=jpg", "tom", "001", "Brisbane"));
        products.add(new Product("test", "8", "Category", "Incredible product Incredible productIncredible productIncredible productIncredible productIncredible productIncredible productIncredible productIncredible product", "220", "Used", "Yesterday", "Available", "https://img2.baidu.com/it/u=2984813231,3723666658&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500", "tom", "001", "Melbourne"));
        products.add(new Product("test", "9", "Category", "Outstanding product Outstanding productOutstanding productOutstanding productOutstanding productOutstanding productOutstanding productOutstanding productOutstanding product", "280", "New", "Today", "Available", "https://img0.baidu.com/it/u=1257588435,2863691321&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500", "tom", "001", "Sydney"));
        products.add(new Product("test", "10", "Category", "Spectacular product Spectacular productSpectacular productSpectacular productSpectacular productSpectacular productSpectacular productSpectacular productSpectacular product", "320", "Used", "Yesterday", "Available", "https://img2.baidu.com/it/u=271958556,2894190561&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500", "tom", "001", "Melbourne"));

        return products;
    }


}