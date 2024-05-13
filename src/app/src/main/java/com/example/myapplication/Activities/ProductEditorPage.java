package com.example.myapplication.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;

public class ProductEditorPage extends AppCompatActivity {

    private Product currentProduct;  // 产品对象实例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_editor);  // 加载布局

        // 从 Intent 获取传递的 Product 对象
        currentProduct = (Product) getIntent().getSerializableExtra("product");
        if (currentProduct == null) {
            finish();  // 如果没有接收到 Product 对象，关闭此 Activity
            return;
        }


        // 设置EditText
        setupEditTextListeners();
    }

    private void setupEditTextListeners() {

        EditText editProductName = findViewById(R.id.EditProductName);
        EditText editProductPrice = findViewById(R.id.EditProductPrice);
        EditText editProductDescription = findViewById(R.id.EditProductDescription);
        ImageView curImageView = findViewById(R.id.curImageView);
        ImageButton backButton = findViewById(R.id.button_back);

        backButton.setOnClickListener(v -> finish());
        editProductName.setText(currentProduct.getName());
        editProductPrice.setText(currentProduct.getPrice());
        editProductDescription.setText(currentProduct.getDescription());

        if (currentProduct.getImgLink() != null && !currentProduct.getImgLink().isEmpty()) {
            Glide.with(this)
                    .load(currentProduct.getImgLink())
                    .apply(new RequestOptions().placeholder(R.drawable.product_page_default_img).error(R.drawable.product_page_default_img))
                    .into(curImageView);
        }
    }
}
