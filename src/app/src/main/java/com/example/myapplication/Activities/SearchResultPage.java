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
//        initButtons();
        initViews();
//        setupSortOptionsPopup(); // Setup sort options popup

    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnSortBy = findViewById(R.id.btnSortBy);
        btnPrice = findViewById(R.id.btnPrice);
        btnCondition = findViewById(R.id.btnCondition);
        btnDateListed = findViewById(R.id.btnDateListed);

        btnBack.setOnClickListener(view -> finish());

        // 初始化 PopupWindow
        setupSortOptionsPopup();
        inflatePriceOptions();  // Make sure to call this to prepare the price options

        // 设置按钮切换行为
        setupButtonWithToggle(btnSortBy, R.drawable.search_result_page_button_background,
                R.drawable.search_result_page_selected_button_background, sortOptionsPopup);

        // 设置 Price 按钮切换行为
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

        // 设置 "See Items" 按钮的点击监听器
        btnSeeItems.setOnClickListener(v -> {
            // 保存输入值
            String min = etMinimum.getText().toString();
            String max = etMaximum.getText().toString();

            // 尝试转换字符串为整数
            int minValue = min.isEmpty() ? Integer.MIN_VALUE : Integer.parseInt(min);
            int maxValue = max.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(max);

            // 如果最小值大于最大值，则交换它们
            if (minValue > maxValue) {
                int temp = minValue;
                minValue = maxValue;
                maxValue = temp;

                // 更新文本框值
                etMinimum.setText(String.valueOf(minValue));
                etMaximum.setText(String.valueOf(maxValue));

            }

            // 更新文本框值
            savedMinimum = String.valueOf(minValue);
            savedMaximum = String.valueOf(maxValue);

            // 更新 Price 按钮文本
            if (!min.isEmpty() && max.isEmpty()) {
                btnPrice.setText("$ " + minValue + " or more");
            } else if (min.isEmpty() && !max.isEmpty()) {
                btnPrice.setText("$ " + maxValue + " or less");
            } else if (!min.isEmpty() && !max.isEmpty()) {
                btnPrice.setText("$ " + minValue + " to $ " + maxValue);
            }

            // 关闭下拉框
            if (priceOptionsPopup.isShowing()) {
                priceOptionsPopup.dismiss();
            }
        });

        // 设置 "Reset" 按钮的点击监听器
        btnReset.setOnClickListener(v -> {
            // 检查并清空 etMinimum 和 etMaximum
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
        //创建一个线性布局，并设置它的方向为垂直，背景颜色为白色。
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.WHITE);
        //定义一个包含排序选项的字符串数组
        final String[] options = {"Suggested", "Lowest Price", "Highest Price", "Nearest", "Newest"};
        //对于每个排序选项：
        //从布局文件中填充排序选项视图，并找到其中的文本视图和勾选图像视图。
        for (String option : options) {
            sortOptionView = getLayoutInflater().inflate(R.layout.research_result_sort_item, layout, false);
            tvOption = sortOptionView.findViewById(R.id.tvSortOption); // Initialize text view for sort option
            ivCheckmark = sortOptionView.findViewById(R.id.ivCheckmark); // Initialize checkmark image view
            //设置排序选项的文本。
            tvOption.setText(option); // Set text for sort option

            //根据选项是否被选中来更新显示。
            updateOptionDisplay(tvOption, ivCheckmark, option.equals(selectedOption)); // Update display based on whether the option is selected

            //设置点击监听器，当用户选择了一个选项时，更新当前选中的选项，更新按钮文本为所选选项，更新排序选项的显示，并关闭弹出框。
            sortOptionView.setOnClickListener(v -> {
                selectedOption = option; // Update currently selected option
                btnSortBy.setText(option); // Update button text to the selected option
                updateSortOptions(layout); // Update sort options display
                sortOptionsPopup.dismiss(); // Close the popup
            });

            layout.addView(sortOptionView); // Add sort option view to layout
        }
//将排序选项视图添加到布局中。
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
        //检查弹出窗口当前是否正在显示。如果显示，则关闭弹出窗口并将按钮背景设置为正常状态；
        // 如果未显示，则将按钮背景设置为选中状态，并在按钮下方显示弹出窗口。
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
        //设置弹出窗口关闭时的监听器，用于在弹出窗口关闭时将按钮背景重置为正常状态
        popupWindow.setOnDismissListener(() -> {
            button.setBackgroundResource(normalBg); // Reset button background when the popup is dismissed

        });
    }
}