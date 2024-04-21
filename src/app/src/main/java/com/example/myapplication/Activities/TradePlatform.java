package com.example.myapplication.Activities;

import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapters.ProductAdapter;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This activity is the the trade platform, people can post their products here.
 */
public class TradePlatform extends Page {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_platform);

        TextView homePage = findViewById(R.id.HomePage);
        TextView userPage = findViewById(R.id.UserPage);
        TextView tradePage = findViewById(R.id.TradePage);
        TextView favorite = findViewById(R.id.Favorite);

        // Setting up click listeners for navigation
        homePage.setOnClickListener(v -> goHomePage());
        userPage.setOnClickListener(v -> goUserPage());
        tradePage.setOnClickListener(v -> goTradePage());
        favorite.setOnClickListener(v -> goFavorite());

        // Setting up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load product data and set up adapter
        loadProducts();
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);
    }

    private void loadProducts() {
        productList = new ArrayList<>();
        try {
            InputStream is = getAssets().open("Product_Data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length() && i < 100; i++) { // Only load 100 items to test
                JSONObject obj = jsonArray.getJSONObject(i);
                Product product = new Product(
                        obj.getString("Product_ID"),
                        obj.getString("Category"),
                        obj.getString("Product_Description"),
                        obj.getString("Price"),
                        obj.getString("Conditon"),
                        obj.getString("Upload_Date"),
                        obj.getString("Status"),
                        obj.getString("ImgLink")

                );
                productList.add(product);


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
