package com.example.myapplication.Activities;

import static com.example.myapplication.common.CommonHelper.showToast;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * This activity allows users to edit product details, including name, price, description, category, condition,
 * and image. Users can update the information and save it to the database.
 * @author Xiaojie Zhou (u7769944)
 */
public class ProductEditorPage extends AppCompatActivity {

    private Product currentProduct;
    private EditText editProductName, editProductPrice, editProductDescription;
    private ImageView curImageView;
    private ImageButton backButton;
    private Button imageButton,submitButton;
    private Spinner categorySpinner, conditionSpinner;
    private ActivityResultLauncher<String> mGetContent;
    private String imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_editor);  // Load the layout

        // Get the Product object passed from Intent
        currentProduct = (Product) getIntent().getSerializableExtra("product");
        if (currentProduct == null) {
            finish();  // If no Product object received, close this Activity
            return;
        }

        // Setup EditText
        setupEditTextListeners();
    }

    private void setupSpinner(Spinner spinner, String[] options) {
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);  // Set the color of first item (hint) as gray
                } else {
                    tv.setTextColor(Color.BLACK);  // Set the color of other items as black
                }
                return view;
            }
        };
        spinner.setAdapter(adapter);
    }

    private void setupEditTextListeners() {
        editProductName = findViewById(R.id.EditProductName);
        editProductPrice = findViewById(R.id.EditProductPrice);
        editProductDescription = findViewById(R.id.EditProductDescription);
        curImageView = findViewById(R.id.curImageView);
        backButton = findViewById(R.id.button_back);
        imageButton = findViewById(R.id.image_button);
        categorySpinner = findViewById(R.id.CategorySpinner);
        conditionSpinner = findViewById(R.id.ConditionSpinner);

        backButton.setOnClickListener(v -> finish());

        editProductName.setText(currentProduct.getName());
        editProductPrice.setText(currentProduct.getPrice());
        editProductDescription.setText(currentProduct.getDescription());

        setupSpinner(categorySpinner, new String[]{"Change Product Category", "Electronics", "Clothing", "Furniture", "Books", "Sports", "Toys", "Beauty", "Others"});
        setupSpinner(conditionSpinner, new String[]{"Change Product Condition", "New", "Used (like new)", "Used (good)", "Used (fair)"});

        if (currentProduct.getImgLink() != null && !currentProduct.getImgLink().isEmpty()) {
            Glide.with(this)
                    .load(currentProduct.getImgLink())
                    .apply(new RequestOptions().placeholder(R.drawable.product_page_default_img).error(R.drawable.product_page_default_img))
                    .into(curImageView);
        }

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), this::handleImageSelection);
        imageButton.setOnClickListener(v -> mGetContent.launch("image/*"));

        submitButton = findViewById(R.id.submitButton);  // Get the submit button
        submitButton.setOnClickListener(v -> updateProduct());  // Set click event listener
    }

    private void handleImageSelection(Uri uri) {
        if (uri != null) {
            Glide.with(this)
                    .load(uri)
                    .into(curImageView);
        }
    }


    // Get and format current date and time
    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
        return sdf.format(new Date());
    }

    // Get category ID
    private String getCategoryId(String category) {
        Map<String, String> categoryMap = new HashMap<>();
        categoryMap.put("Electronics", "1");
        categoryMap.put("Clothing", "2");
        categoryMap.put("Furniture", "3");
        categoryMap.put("Books", "4");
        categoryMap.put("Sports", "5");
        categoryMap.put("Toys", "6");
        categoryMap.put("Beauty", "7");
        categoryMap.put("Others", "8");
        return categoryMap.getOrDefault(category, "8"); // If no match, return ID for "Others"
    }

    // Update the product
    private void updateProduct() {
        String name = editProductName.getText().toString().trim();
        String price = editProductPrice.getText().toString().trim();
        String description = editProductDescription.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();
        String condition = conditionSpinner.getSelectedItem().toString();

        // Check if inputs are valid
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(price) || category.equals("Change Product Category") || condition.equals("Change Product Condition")) {
            showToast(this,"Please fill all the fields and make valid selections.");
            return;
        }

        // If user selects an image, update image link; otherwise, use existing image
        if (imageUri != null) {
            currentProduct.setImgLink(imageUri);
        }

        currentProduct.setName(name);
        currentProduct.setPrice(price);
        currentProduct.setDescription(description);
        currentProduct.setCategory(category);
        currentProduct.setCondition(condition);
        currentProduct.setUploadDate(getCurrentTimestamp());
        currentProduct.setCategoryID(getCategoryId(category));

        // Update product information in the database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Product").child(currentProduct.getProductID());
        ref.setValue(currentProduct)
                .addOnSuccessListener(aVoid -> showToast(this,"Product updated successfully!"))
                .addOnFailureListener(e -> showToast(this,"Failed to update product."));
    }
}