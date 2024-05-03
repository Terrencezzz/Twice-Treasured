package com.example.myapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;

import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ProductViewHolder> {

    private Context context;
    private final List<Product> productList;

    public SearchItemAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_result_item_display_list, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        Glide.with(context).load(product.getImgLink()).into(holder.imageProduct);
        holder.textPrice.setText(product.getPrice());
        holder.textDescription.setText(product.getDescription());
        holder.textLocation.setText(product.getLocation());

        // Set click listeners to return to homepage
        View.OnClickListener returnToHomeListener = v -> {
            if (context instanceof Activity) {
                ((Activity) context).finish(); // End this activity
            }
        };

        holder.imageProduct.setOnClickListener(returnToHomeListener);
        holder.textPrice.setOnClickListener(returnToHomeListener);
        holder.textDescription.setOnClickListener(returnToHomeListener);
        holder.textLocation.setOnClickListener(returnToHomeListener);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView textPrice, textDescription, textLocation;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            textPrice = itemView.findViewById(R.id.textPrice);
            textDescription = itemView.findViewById(R.id.textDescription);
            textLocation = itemView.findViewById(R.id.textLocation);
        }
    }
}
