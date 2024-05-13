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
import com.example.myapplication.Activities.ProductPage;
import com.example.myapplication.R;
import com.example.myapplication.basicClass.Product;
import java.util.List;

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
        holder.tvDescription.setText(product.getDescription());
        holder.tvPrice.setText(mContext.getString(R.string.price_format, product.getPrice()));
        // Load product image using Glide library
        // Load product image using Glide library
        Glide.with(mContext)
                .load(product.getImgLink())
                .placeholder(R.drawable.product_page_default_img) // Placeholder for loading image
                .error(R.drawable.product_page_default_img) // Default image to show if loading fails
                .into(holder.ivProductImage);

        // 设置点击事件监听器
        View.OnClickListener listener = v -> {
            Intent intent = new Intent(mContext, ProductPage.class);
            intent.putExtra("product", product);
            mContext.startActivity(intent);
        };

        // 为图片和文本视图添加点击事件
        holder.ivProductImage.setOnClickListener(listener);
        holder.tvDescription.setOnClickListener(listener);
        holder.tvPrice.setOnClickListener(listener);

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
