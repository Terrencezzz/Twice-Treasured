package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myapplication.Activities.ProductEditorPage;
import com.example.myapplication.Activities.ProductPage;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;
import java.util.List;
/**
 * This class serves as an adapter for managing products in a RecyclerView.
 * It handles displaying product information and enabling editing.
 * Author: Xiaojie Zhou (u7769944)
 */
public class ProductsManageAdapter extends RecyclerView.Adapter<ProductsManageAdapter.ProductViewHolder> {

    private Context mContext;
    private List<Product> productList;
    private LayoutInflater inflater;

    public ProductsManageAdapter(Context context, List<Product> products) {
        this.mContext = context;
        this.productList = products;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout and create a new ViewHolder
        View itemView = inflater.inflate(R.layout.products_manage_list, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Bind data to the ViewHolder
        Product product = productList.get(position);
        holder.tvDescription.setText(product.getDescription()); // Set description text
        String priceWithCurrency="$ "+product.getPrice();
        holder.tvPrice.setText(priceWithCurrency);

        // Load product image using Glide library
        Glide.with(mContext)
                .load(product.getImgLink()) // Load image from provided link
                .placeholder(R.drawable.product_page_default_img) // Placeholder for loading image
                .error(R.drawable.product_page_default_img) // Default image to show if loading fails
                .into(holder.ivProductImage); // Set image into ImageView

        // Set click event listener
        View.OnClickListener listener = v -> {
            Intent intent = new Intent(mContext, ProductPage.class);
            intent.putExtra("product", product); // Pass product object to ProductPage
            mContext.startActivity(intent); // Start ProductPage activity
        };

        // Add click event to image and text views
        holder.ivProductImage.setOnClickListener(listener);
        holder.tvDescription.setOnClickListener(listener);
        holder.tvPrice.setOnClickListener(listener);

        // Specific edit button click event
        holder.btnEdit.setOnClickListener(v -> {
            Intent editIntent = new Intent(mContext, ProductEditorPage.class);
            editIntent.putExtra("product", product); // Pass product object to ProductEditorPage
            mContext.startActivity(editIntent); // Start ProductEditorPage activity
        });
    }


    @Override
    public int getItemCount() {
        return productList.size(); // Return the size of the data list
    }

    // Update the data list and notify the adapter
    public void setProducts(List<Product> newProducts) {
        if (newProducts != null) {
            Log.d("ProductsManageAdapter", "setProducts: New products list size: " + newProducts.size());
            productList = newProducts;
            notifyDataSetChanged();
        } else {
            Log.d("ProductsManageAdapter", "setProducts: Received null products list");
        }
    }

    // ViewHolder for holding item views
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvDescription, tvPrice;
        Button btnEdit;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views from the item layout
            ivProductImage = itemView.findViewById(R.id.my_product_image);
            tvDescription = itemView.findViewById(R.id.my_product_description);
            tvPrice = itemView.findViewById(R.id.my_product_price);
            btnEdit = itemView.findViewById(R.id.edit_button);
        }

    }
}

