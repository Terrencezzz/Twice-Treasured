package com.example.myapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activities.ProductPage;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;

import java.util.List;


/**
 * Adapter class for displaying search results in a RecyclerView.
 */
public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ProductViewHolder> {

    private Context context;
    private final List<Product> productList;

    /**
     * Constructor for the adapter.
     * @param context The context of the calling activity or fragment.
     * @param productList The list of products to display.
     */
    public SearchItemAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout for each item in the RecyclerView.
        View view = LayoutInflater.from(context).inflate(R.layout.search_result_item_display_list, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Bind data to the views in each item of the RecyclerView.
        Product product = productList.get(position);
        Glide.with(context)
                .load(product.getImgLink())
                .placeholder(R.drawable.product_page_default_img)
                .into(holder.imageProduct);

        String priceWithCurrency = "$ " + product.getPrice();
        holder.textPrice.setText(priceWithCurrency);
        holder.textDescription.setText(product.getDescription());
        holder.textLocation.setText(product.getLocation());

        // Click listener for launching ProductPage
        View.OnClickListener productPageListener = v -> {
            Intent intent = new Intent(context, ProductPage.class);
            intent.putExtra("product", product);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        };

        // Set click listeners to navigate to product detail page
        holder.imageProduct.setOnClickListener(productPageListener);
        holder.textPrice.setOnClickListener(productPageListener);
        holder.textDescription.setOnClickListener(productPageListener);
        holder.textLocation.setOnClickListener(productPageListener);
    }

    @Override
    public int getItemCount() {
        // Return the number of items in the list.
        return productList.size();
    }

    /**
     * ViewHolder class to hold the views of each item in the RecyclerView.
     */
    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView textPrice, textDescription, textLocation;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views from the layout.
            imageProduct = itemView.findViewById(R.id.imageProduct);
            textPrice = itemView.findViewById(R.id.textPrice);
            textDescription = itemView.findViewById(R.id.textDescription);
            textLocation = itemView.findViewById(R.id.textLocation);
        }
    }
}